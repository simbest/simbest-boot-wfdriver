package com.simbest.boot.wfdriver.process.listener.mapper;

import com.simbest.boot.base.repository.LogicRepository;
import com.simbest.boot.wfdriver.process.listener.model.ActTaskInstModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ActTaskInstModelMapper extends LogicRepository<ActTaskInstModel, String> {

    /**
     * 根据工作项ID更新工作项信息
     * @param actTaskInstModel
     * @return
     */
    @Query(value = "update ActTaskInstModel  " +
            "set taskId = :#{#actTaskInstModel.taskId}, " +
            "parentTaskId = :#{#actTaskInstModel.parentTaskId}, " +
            "taskDefinitionId = :#{#actTaskInstModel.taskDefinitionId}, " +
            "revision = :#{#actTaskInstModel.revision}, " +
            "executionId = :#{#actTaskInstModel.executionId}, " +
            "processInstId = :#{#actTaskInstModel.processInstId}, " +
            "taskDefinitionKey = :#{#actTaskInstModel.taskDefinitionKey}, " +
            "processDefinitionId = :#{#actTaskInstModel.processDefinitionId}, " +
            "scopeId = :#{#actTaskInstModel.scopeId}, " +
            "subScopeId = :#{#actTaskInstModel.subScopeId}, " +
            "scopeType = :#{#actTaskInstModel.scopeType}, " +
            "name = :#{#actTaskInstModel.name}, " +
            "description = :#{#actTaskInstModel.description}, " +
            "owner = :#{#actTaskInstModel.owner}, " +
            "assignee = :#{#actTaskInstModel.assignee}, " +
            "delegationState = :#{#actTaskInstModel.delegationState}, " +
            "priority = :#{#actTaskInstModel.priority}, " +
            "taskCreateTime = :#{#actTaskInstModel.taskCreateTime}, " +
            "dueDate = :#{#actTaskInstModel.dueDate}, " +
            "category = :#{#actTaskInstModel.category}, " +
            "suspensionState = :#{#actTaskInstModel.suspensionState}, " +
            "tenantId = :#{#actTaskInstModel.tenantId}, " +
            "formKey = :#{#actTaskInstModel.formKey}, " +
            "claimTime = :#{#actTaskInstModel.claimTime}, " +
            "endTime = :#{#actTaskInstModel.endTime}, " +
            "modifier = :#{#actTaskInstModel.modifier} ," +
            "modifiedTime = :#{#actTaskInstModel.modifiedTime} " +
            "where taskId = :#{#actTaskInstModel.taskId} " )
    @Transactional
    @Modifying
    int updateByTaskId(@Param("actTaskInstModel") ActTaskInstModel actTaskInstModel);


    /**
     * 根据工作项ID查询工作项信息
     * @param workItemID      工作项ID
     * @return
     */
    String selectSql1 = "SELECT * FROM flowable_act_task_inst_model WHERE processInstId = :processInstId and taskId = :taskId and enabled='1'";
    @Query(value = selectSql1,nativeQuery = true)
    ActTaskInstModel getByProcessInstIdAndTaskId(@Param(value = "processInstId") String processInstId, @Param(value = "taskId") String taskId);

    /**
     * 根据工作项ID查询工作项信息
     * @param workItemID      工作项ID
     * @return
     */
    String selectSql9 = "SELECT * FROM flowable_act_task_inst_model WHERE taskId = :taskId and enabled='1'";
    @Query(value = selectSql9,nativeQuery = true)
    ActTaskInstModel getByTaskId(@Param(value = "taskId") String taskId);

    /**
     * 根据流程实例ID，流程活动定义ID，查询流程工作项信息
     */
    String selectSql2 = "SELECT * FROM (SELECT * FROM flowable_act_task_inst_model WHERE enabled = '1' AND PROCESS_INST_ID =  ?1 AND ACTIVITY_DEF_ID = ?2 order by create_time desc) WHERE rownum=1";
    @Query(value = selectSql2,nativeQuery = true)
    List<ActTaskInstModel> getByProcessInstIdAndTaskDefinitionKey(String processInstId, String taskDefinitionKey);

    /**processInstId
     * 根据流程实例ID 删除工作项信息
     * @param processInstId    流程实例ID
     * @return
     */
    String deleteSql1 = "update flowable_act_task_inst_model set enabled=0 where processInstId = :processInstId";
    @Transactional
    @Modifying
    @Query(value = deleteSql1,nativeQuery = true)
	int deleteByProcessInst(@Param(value = "processInstId") String processInstId);

    /**
     * 根据流程实例ID查询工作项信息
     * @param processInstId  流程实例ID
     * @return
     */
    List<ActTaskInstModel> queryTaskInstModelByProcessInstId(String processInstId);
}