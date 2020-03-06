package com.simbest.boot.wfdriver.process.listener.mapper;

import com.simbest.boot.base.repository.LogicRepository;
import com.simbest.boot.wfdriver.process.listener.model.ActProcessInstModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <strong>Title : ActProcessInstModelMapper</strong><br>
 * <strong>Description : 流程实例数据库持久层</strong><br>
 * <strong>Create on : 2018/3/29</strong><br>
 * <strong>Modify on : 2018/3/29</strong><br>
 * <strong>Copyright (C) Ltd.</strong><br>
 *
 * @author LJW lijianwu@simbest.com.cn
 * @version <strong>V1.0.0</strong><br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 */
@Repository
public interface ActProcessInstModelMapper extends LogicRepository<ActProcessInstModel, String> {

    @Transactional
    @Modifying
    @Query(value = "update ActProcessInstModel  " +
            "set processInstanceId = :#{#actProcessInstModel.processInstanceId}, " +
            "name=:#{#actProcessInstModel.name},"+
            "description=:#{#actProcessInstModel.description},"+
            "tenantId=:#{#actProcessInstModel.tenantId},"+
            "businessKey=:#{#actProcessInstModel.businessKey},"+
            "callbackId=:#{#actProcessInstModel.callbackId},"+
            "callbackType=:#{#actProcessInstModel.callbackType},"+
            "deleteReason=:#{#actProcessInstModel.deleteReason},"+
            "deploymentId=:#{#actProcessInstModel.deploymentId},"+
            "durationInMillis=:#{#actProcessInstModel.durationInMillis},"+
            "endActivityId=:#{#actProcessInstModel.endActivityId},"+
            "endTime=:#{#actProcessInstModel.endTime},"+
            "processDefinitionId=:#{#actProcessInstModel.processDefinitionId},"+
            "processDefinitionKey=:#{#actProcessInstModel.processDefinitionKey},"+
            "processDefinitionName=:#{#actProcessInstModel.processDefinitionName},"+
            "processDefinitionVersion=:#{#actProcessInstModel.processDefinitionVersion},"+
            "revision=:#{#actProcessInstModel.revision},"+
            "startActivityId=:#{#actProcessInstModel.startActivityId},"+
            "startTime=:#{#actProcessInstModel.startTime},"+
            "startUserId=:#{#actProcessInstModel.startUserId},"+
            "superProcessInstanceId=:#{#actProcessInstModel.superProcessInstanceId},"+
            "modifier = :#{#actProcessInstModel.modifier} ," +
            "modifiedTime = :#{#actProcessInstModel.modifiedTime}, " +
            "currentState = :#{#actProcessInstModel.currentState} " +
            " where processInstanceId = :#{#actProcessInstModel.processInstanceId} " )
    int updateByProcessInstanceId(@Param("actProcessInstModel") ActProcessInstModel actProcessInstModel);

    /**
     * 根据流程实例ID查询流程实例信息
     * @param processInstanceId     流程实例ID
     * @return
     */
    @Query(value = "SELECT * FROM flowable_process_inst_model WHERE PROCESS_INST_ID = ?1",nativeQuery = true)
    ActProcessInstModel getByProcessInstanceId(String processInstanceId);


    /**
     *  根据流程实例ID和数据有效状态查询流程实例数据
     * @param processInstanceId       流程实例ID
     * @param enabled             数据有效状态 1：有效  0:无效
     * @return
     */
    ActProcessInstModel queryByProcessInstanceIdAndEnabled(String processInstanceId, Boolean enabled);

}
