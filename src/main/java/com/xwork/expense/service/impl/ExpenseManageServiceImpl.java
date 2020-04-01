package com.xwork.expense.service.impl;

import com.xwork.expense.entity.dto.ExpensePayDto;
import com.xwork.expense.entity.dto.JoinProjectDto;
import com.xwork.expense.entity.enums.AuditState;
import com.xwork.expense.entity.po.ExpenseApply;
import com.xwork.expense.entity.po.SpendingDetail;
import com.xwork.expense.entity.po.SysRole;
import com.xwork.expense.entity.po.SysUser;
import com.xwork.expense.repository.ExpenseApplyRepository;
import com.xwork.expense.repository.SpendingDetailRepository;
import com.xwork.expense.security.SecurityUtil;
import com.xwork.expense.service.ExpenseManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ExpenseManageServiceImpl implements ExpenseManageService {

    @Resource
    private ExpenseApplyRepository expenseApplyRepository;

    @Resource
    private SpendingDetailRepository spendingDetailRepository;
    /**
     * 默认上传路径
     */
    @Value("${system.upload-path}")
    private String defaultPath;

    /**
     * 查询所有报销申请
     *
     * @return
     */
    @Override
    public List<ExpenseApply> listAllExpenseApply() {
        List<ExpenseApply> expenseApplyList = expenseApplyRepository.findAll();
        //封装关联项目详情
        assert expenseApplyList != null;
        expenseApplyList.forEach(apply-> {
            if(apply.getSpendingDetail()!=null){
                apply.setProjectSpendingDetail(apply.getSpendingDetail().getProject().getName()
                        +"-"
                        +apply.getSpendingDetail().getType().getDisplayName());
            }
        });
        return expenseApplyList;
    }

    /**
     * 添加一个报销申请
     *
     * @param expenseApply
     */
    @Override
    @Transactional
    public void addOneExpenseApply(ExpenseApply expenseApply) {
        //先存储
        expenseApply.setAuditState(AuditState.PRE_AUDIT);
        expenseApplyRepository.save(expenseApply);
        //存储文件
        String path = storeFile(expenseApply.getFile(), expenseApply.getId());
        expenseApply.setApplyFilePath(path);
        expenseApplyRepository.save(expenseApply);
    }

    /**
     * 财务查询待关联的报销申请
     *
     * @return
     */
    @Override
    public List<ExpenseApply> listForFinance() {
        return expenseApplyRepository.listByAuditState(AuditState.PRE_AUDIT);
    }

    /**
     * 获取一个报销申请
     *
     * @param expenseApplyId
     * @return
     */
    @Override
    public ExpenseApply getOneExpenseApply(Long expenseApplyId) {
        return expenseApplyRepository.getOne(expenseApplyId);
    }


    /**
     * 存储文件，返回文件路径
     *
     * @param multipartFile
     * @return
     */
    private String storeFile(MultipartFile multipartFile, Long id) {
        String filePath = defaultPath + File.separator + id + "__" + multipartFile.getOriginalFilename();
        File desFile = new File(filePath);
        if (!desFile.getParentFile().exists()) {
            desFile.mkdirs();
        }
        try {
            multipartFile.transferTo(desFile);
        } catch (IOException e) {
            log.error("上传文件出错");
            e.printStackTrace();
        }
        return filePath;
    }


    /**
     * 报销申请关联项目
     *
     * @param joinProjectDto
     */
    @Override
    @Transactional
    public void joinProject(JoinProjectDto joinProjectDto) {
        ExpenseApply expenseApply = expenseApplyRepository.getOne(joinProjectDto.getExpenseApplyId());
        SpendingDetail spendingDetail =spendingDetailRepository.getOne(joinProjectDto.getSpendingDetailId());
        expenseApply.setSpendingDetail(spendingDetail);
        expenseApply.setAuditState(AuditState.FINANCE_AUDIT);
        expenseApplyRepository.save(expenseApply);
    }

    /**
     * 审核用的报销申请列表
     *
     * @return
     */
    @Override
    public List<ExpenseApply> listForAudit() {
        List<ExpenseApply> expenseApplyList = null;
        //当前用户
        SysUser currentUser = SecurityUtil.getCurrentUser();
        assert currentUser != null;
        if (currentUser.getRoles().get(0).getRole() == SysRole.ROLE_AUDIT_LEVEL1) {//一级审批
            expenseApplyList = expenseApplyRepository.listByAuditState(AuditState.FINANCE_AUDIT);
        } else if (currentUser.getRoles().get(0).getRole() == SysRole.ROLE_AUDIT_LEVEL2) {//二级审批
            expenseApplyList = expenseApplyRepository.listByAuditState(AuditState.AUDIT_L1);
        } else if (currentUser.getRoles().get(0).getRole() == SysRole.ROLE_AUDIT_LEVEL3) {//三级审批
            expenseApplyList = expenseApplyRepository.listByAuditState(AuditState.AUDIT_L2);
        }
        //封装关联项目详情
        assert expenseApplyList != null;
        expenseApplyList.forEach(apply-> apply.setProjectSpendingDetail(apply.getSpendingDetail().getProject().getName()
                +"-"
                +apply.getSpendingDetail().getType().getDisplayName()));
        return expenseApplyList;
    }

    /**
     * 审核报销申请
     *
     * @param expenseApplyId
     */
    @Override
    @Transactional
    public void auditExpenseApply(Long expenseApplyId) {
        ExpenseApply expenseApply = expenseApplyRepository.getOne(expenseApplyId);
        //查看当前用户
        SysUser currentUser = SecurityUtil.getCurrentUser();
        assert currentUser != null;
        if (currentUser.getRoles().get(0).getRole() == SysRole.ROLE_AUDIT_LEVEL1) {//一级审批
            expenseApply.setAuditState(AuditState.AUDIT_L1);
        } else if (currentUser.getRoles().get(0).getRole() == SysRole.ROLE_AUDIT_LEVEL2) {//二级审批
            expenseApply.setAuditState(AuditState.AUDIT_L2);
        } else if (currentUser.getRoles().get(0).getRole() == SysRole.ROLE_AUDIT_LEVEL3) {//三级审批
            expenseApply.setAuditState(AuditState.AUDIT_L3);
        }
        expenseApplyRepository.save(expenseApply);
    }

    /**
     * 出纳查看的待支付的申请列表
     *
     * @return
     */
    @Override
    public List<ExpenseApply> listForCashier() {
        List<ExpenseApply> expenseApplyList = expenseApplyRepository.listByAudiStateAndPayed(AuditState.AUDIT_L3,false);
        //封装关联项目详情
        assert expenseApplyList != null;
        expenseApplyList.forEach(apply-> apply.setProjectSpendingDetail(apply.getSpendingDetail().getProject().getName()
                +"-"
                +apply.getSpendingDetail().getType().getDisplayName()));
        return expenseApplyList;
    }


    /**
     * 支付报销
     *
     * @param expensePayDto
     */
    @Override
    @Transactional
    public void payExpense(ExpensePayDto expensePayDto) {
        ExpenseApply expenseApply = expenseApplyRepository.getOne(expensePayDto.getExpenseApplyId());
        expenseApply.setPayed(true);
        expenseApply.setPayWay(expensePayDto.getPayWay());
        expenseApplyRepository.save(expenseApply);
    }
}
