package com.simbest.boot.wfdriver.process.listener.mapper;

import com.simbest.boot.base.repository.LogicRepository;
import com.simbest.boot.wfdriver.process.listener.model.ActCommentModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *@ClassName ActCommentModelMapper 审批意见Mapper
 *@Description 审批意见Mapper
 *@Author Administrator
 *@Date 2019/12/01 16:03
 *@Version 1.0
 **/
@Repository
public interface ActCommentModelMapper extends LogicRepository<ActCommentModel, String> {

    /**
     * 根据流程实例ID 删除BPS流程信息
     * @param processInstId    流程实例ID
     */
    @Transactional
    @Modifying
    @Query(value = "update flowable_act_comment_model set ENABLED = 0 where processInstId = ?1",nativeQuery = true)
    int deleteByInstID(String processInstId);

    /**
     * 根据流程实例ID和工作项ID 查询审批意见数据
     * @param processInstId   流程实例ID
     * @param taskId    任务ID
     * @return
     */
    String selectSql = "select * from flowable_act_comment_model where processInstId = ?1 and ENABLED = 1 and taskId = ?2";
    @Query(value = selectSql,nativeQuery = true)
    ActCommentModel getOneData(String processInstId, String taskId);

    /**
     * 根据流程实例ID查询意见信息
     * @param processInstId   流程实例ID
     * @return
     */
    List<ActCommentModel> getByProcessInstId(String processInstId);


    /**
     * 根据流程实例ID和有效状态查询所有工作项
     * @param processInstId             流程实例ID
     * @param enabled                   数据状态 0:无效  1:有效
     * @return
     */
    List<ActCommentModel> queryAllByProcessInstIdAndEnabled(String processInstId, Boolean enabled);

}
