package com.xwork.expense.service.impl;

import com.xwork.expense.entity.enums.AuditState;
import com.xwork.expense.entity.po.ExpenseApply;
import com.xwork.expense.repository.ExpenseApplyRepository;
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
        return expenseApplyRepository.findAll();
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
}
