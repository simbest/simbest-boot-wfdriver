package com.simbest.boot.wfdriver.process.bussiness.mapper;

import com.simbest.boot.base.repository.GenericRepository;
import com.simbest.boot.wfdriver.process.bussiness.model.ActBusinessStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 *@ClassName ActBusinessStatusMapper
 *@Description
 *@Author Administrator
 *@Date 2019/12/01 16:03
 *@Version 1.0
 **/
@Repository
public interface ActBusinessStatusMapper extends GenericRepository<ActBusinessStatus,String> {

    /**
     * 根据流程实例ID 查询业务流程操作信息
     * @param processInstID     流程实例ID
     * @return
     */
    String sql1 = "SELECT * FROM act_business_status t WHERE t.PROCESS_INST_ID = ?1";
    @Query(value = sql1,nativeQuery = true)
    ActBusinessStatus getByProcessInst(String processInstID);

    /**
     * 根据父流程实例ID 查询最新的业务流程操作信息
     * @param parentProcessInstId       父流程实例ID
     * @return
     */
    String sql9 = "select * from act_business_status act where act.parent_proc_id=?1 and act.previous_assistant is not null";
    @Query(value = sql9,nativeQuery = true)
    List<ActBusinessStatus> getByProcessInstIdDoneNew(String parentProcessInstId);

    /**
     * 根据父流程实例ID查询业务流程操作信息
     * @param parentProcessInstId  父流程实例ID
     * @return
     */
    List<ActBusinessStatus> getActBusinessStatusByParentProcessInstId(String parentProcessInstId);

    String sql7 = "update act_business_status t set ENABLED=0 WHERE t.PROCESS_INST_ID = ?1";
    @Query(value = sql7,nativeQuery = true)
    int updateActBusDataByProInsId(String processInstID);

    /**
     *  获取指定 userName 下面所有的待办数据
     * @param participant    参与者
     * @return
     */
     String sql4 = "SELECT act .*," +
                            "       task.task_Id       as taskId,"+
                            "       task.name     as taskName,"+
                            "       task.task_Definition_Key     as taskDefKey,"+
                            "       task.assignee as taskAssignee"+
                            "  FROM act_business_status act,"+
                            "       flowable_act_task_inst_model      task"+
                            " WHERE act.process_inst_id = task.process_Inst_Id"+
                            "   AND act.receipt_title LIKE concat(concat('%', :dynamicWhere), '%')"+
                            "   AND task.end_Time is null " +
                            "   AND act.enabled = 1"+
                            "   AND task.assignee = :participant"+
                            " ORDER BY task.created_Time desc";
    String sql4Count = "SELECT count(1)"+
                            "  FROM act_business_status act,"+
                            "       flowable_act_task_inst_model      task"+
                            " WHERE act.process_inst_id = task.process_Inst_Id"+
                            "   AND act.receipt_title LIKE concat(concat('%', :dynamicWhere), '%')"+
                            "   AND task.end_Time is null " +
                            "   AND act.enabled = 1"+
                            "   AND task.assignee = :participant"+
                            " ORDER BY task.created_Time desc";
    @Query(value = sql4,countQuery = sql4Count,nativeQuery = true)
    Page<Map<String,Object>> getTodoByUserPage(@Param(value = "participant") String participant, @Param(value = "dynamicWhere") String dynamicWhere, Pageable pageable);


    /**
     *  获取指定 userName 下面所有的已办数据
     * @param assistant     完成人
     * @return
     */
    String sql5 = "SELECT " +
                    " act.*,task.task_Id as taskId," +
                    "task.name as taskName," +
                    "task.assignee as taskAssignee " +
                    "FROM " +
                    "act_business_status act, " +
                    "flowable_act_task_inst_model task "+
                    "WHERE act.process_inst_id = task.process_Inst_Id " +
                    "AND act.receipt_title LIKE concat(concat('%', :dynamicWhere), '%') " +
                    "AND task.end_Time is not null " +
                    "AND task.assignee = :assistant "+
                    "AND act.enabled = 1 " +
                    "ORDER BY task.created_Time desc";
    String sql5Count = "SELECT count(1)" +
                    "FROM " +
                    "act_business_status act, " +
                    "flowable_act_task_inst_model task "+
                    "WHERE act.process_inst_id = task.process_Inst_Id " +
                    "AND act.receipt_title LIKE concat(concat('%', :dynamicWhere), '%') " +
                    "AND task.end_Time is not null " +
                    "AND task.assignee = :assistant "+
                    "AND act.enabled = 1 " +
                    "ORDER BY task.created_Time desc";
    @Query(value = sql5,countQuery = sql5Count,nativeQuery = true)
    Page<Map<String,Object>> getByAreadyDoneAssistantPage(@Param(value = "assistant") String assistant, @Param(value = "dynamicWhere") String dynamicWhere, Pageable pageable);


    /**
     * 获取指定 userName 下面所有的创建的工单数据
     * @param creator     创建人
     * @return
     */
    String sql6 = " SELECT DISTINCT act_business_status.*"+
            " FROM act_business_status"+
            " where act_business_status.receipt_title like concat(concat('%',:dynamicWhere),'%')"+
            " and act_business_status.enabled = 1"+
            " and act_business_status.create_User_Id = :creator"+
            " order by act_business_status.start_Time desc";
    String sql6Count = " SELECT count(1)"+
            " FROM act_business_status"+
            " where act_business_status.RECEIPT_TITLE like concat(concat('%',:dynamicWhere),'%')"+
            " and act_business_status.enabled = 1"+
            " and act_business_status.create_User_Id = :creator"+
            " order by act_business_status.start_Time desc";
    @Query(value = sql6,countQuery = sql6Count,nativeQuery = true)
    Page<Map<String,Object>> getMyCreateDataPage(@Param(value = "creator") String creator, @Param(value = "dynamicWhere") String dynamicWhere, Pageable pageable);
}
