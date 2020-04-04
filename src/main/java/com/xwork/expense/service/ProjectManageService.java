package com.xwork.expense.service;

import com.xwork.expense.entity.dto.JoinProjectDto;
import com.xwork.expense.entity.po.Project;
import com.xwork.expense.entity.po.SpendingDetail;

import java.util.List;

/**
 * 财务立项管理的service
 */
public interface ProjectManageService {

    /**
     * 查询所有项目
     * @return
     */
    List<Project> listAllProject();

    /**
     * 申请项目立项
     * @param project
     */
    void applyProject(Project project);

    /**
     * 审批人查询项目列表
     * @return
     */
    List<Project> listForAudit();

    /**
     * 获取一个项目
     * @return
     */
    Project getOneProject(Long projectId);

    /**
     * 审批项目
     * @param projectId
     */
    void auditProject(Long projectId,String result);

    /**
     * 查询已经三级审批通过的所有项目
     * @return
     */
    List<Project> listAllAudited();

    /**
     * 查询某项目下的开支详情
     * @param projectId
     * @return
     */
    List<SpendingDetail> listSpendingDetail(Long projectId);



}
