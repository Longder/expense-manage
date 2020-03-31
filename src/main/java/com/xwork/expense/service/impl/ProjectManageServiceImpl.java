package com.xwork.expense.service.impl;

import com.xwork.expense.entity.enums.AuditState;
import com.xwork.expense.entity.po.Project;
import com.xwork.expense.entity.po.SpendingDetail;
import com.xwork.expense.entity.po.SysRole;
import com.xwork.expense.entity.po.SysUser;
import com.xwork.expense.repository.ProjectRepository;
import com.xwork.expense.repository.SpendingDetailRepository;
import com.xwork.expense.security.SecurityUtil;
import com.xwork.expense.service.ProjectManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProjectManageServiceImpl implements ProjectManageService {

    @Resource
    private ProjectRepository projectRepository;
    @Resource
    private SpendingDetailRepository spendingDetailRepository;

    /**
     * 查询所有项目
     *
     * @return
     */
    @Override
    public List<Project> listAllProject() {
        return projectRepository.findAll();
    }

    /**
     * 申请项目立项
     *
     * @param project
     */
    @Override
    @Transactional
    public void applyProject(Project project) {
        //计算出项目的总预算
        AtomicReference<BigDecimal> totalBudget = new AtomicReference<>(BigDecimal.ZERO);
        project.getDetailList().forEach(detail -> totalBudget.set(totalBudget.get().add(detail.getBudget())));
        project.setTotalBudget(totalBudget.get());
        //审核状态处理
        project.setAuditState(AuditState.PRE_AUDIT);
        //存储项目
        projectRepository.save(project);
        //存开支详情
        List<SpendingDetail> spendingDetailList = project.getDetailList();
        spendingDetailList.forEach(detail -> detail.setProject(project));
        spendingDetailRepository.saveAll(spendingDetailList);
    }

    /**
     * 审批人查询项目列表
     *
     * @return
     */
    @Override
    public List<Project> listForAudit() {
        List<Project> projectList = null;
        //当前用户
        SysUser currentUser = SecurityUtil.getCurrentUser();
        assert currentUser != null;
        if (currentUser.getRoles().get(0).getRole() == SysRole.ROLE_AUDIT_LEVEL1) {//一级审批
            projectList = projectRepository.listByAuditState(AuditState.PRE_AUDIT);
        } else if (currentUser.getRoles().get(0).getRole() == SysRole.ROLE_AUDIT_LEVEL2) {//二级审批
            projectList = projectRepository.listByAuditState(AuditState.AUDIT_L1);
        } else if (currentUser.getRoles().get(0).getRole() == SysRole.ROLE_AUDIT_LEVEL3) {//三级审批
            projectList = projectRepository.listByAuditState(AuditState.AUDIT_L2);
        }
        return projectList;
    }

    /**
     * 获取一个项目
     *
     * @param projectId
     * @return
     */
    @Override
    public Project getOneProject(Long projectId) {
        Project project = projectRepository.getOne(projectId);
        //封装开支详情
        List<SpendingDetail> detailList = spendingDetailRepository.listByProjectId(projectId);
        project.setDetailList(detailList);
        return project;
    }

    /**
     * 审批项目
     *
     * @param projectId
     */
    @Override
    @Transactional
    public void auditProject(Long projectId) {
        Project project = projectRepository.getOne(projectId);
        //查看当前用户
        SysUser currentUser = SecurityUtil.getCurrentUser();
        if (currentUser.getRoles().get(0).getRole() == SysRole.ROLE_AUDIT_LEVEL1) {//一级审批
            project.setAuditState(AuditState.AUDIT_L1);
        } else if (currentUser.getRoles().get(0).getRole() == SysRole.ROLE_AUDIT_LEVEL2) {//二级审批
            project.setAuditState(AuditState.AUDIT_L2);
        } else if (currentUser.getRoles().get(0).getRole() == SysRole.ROLE_AUDIT_LEVEL3) {//三级审批
            project.setAuditState(AuditState.AUDIT_L3);
        }
        projectRepository.save(project);
    }

    /**
     * 查询已经三级审批通过的所有项目
     *
     * @return
     */
    @Override
    public List<Project> listAllAudited() {
        return projectRepository.listByAuditState(AuditState.AUDIT_L3);
    }

    /**
     * 查询某项目下的开支详情
     *
     * @param projectId
     * @return
     */
    @Override
    public List<SpendingDetail> listSpendingDetail(Long projectId) {
        return spendingDetailRepository.listByProjectId(projectId);
    }
}
