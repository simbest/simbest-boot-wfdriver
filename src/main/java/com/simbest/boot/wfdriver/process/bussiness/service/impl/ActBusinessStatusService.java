package com.simbest.boot.wfdriver.process.bussiness.service.impl;

import com.simbest.boot.base.service.impl.GenericService;
import com.simbest.boot.wf.process.service.IProcessInstanceService;
import com.simbest.boot.wf.unitfytodo.IProcessTodoDataService;
import com.simbest.boot.wfdriver.exceptions.FlowableDriverBusinessException;
import com.simbest.boot.wfdriver.process.bussiness.mapper.ActBusinessStatusMapper;
import com.simbest.boot.wfdriver.process.bussiness.model.ActBusinessStatus;
import com.simbest.boot.wfdriver.process.bussiness.service.IActBusinessStatusService;
import com.simbest.boot.wfdriver.process.listener.model.ActProcessInstModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 *@ClassName ActBusinessStatusMapper
 *@Description
 *@Author Administrator
 *@Date 2019/12/01 16:03
 *@Version 1.0
 **/
@Slf4j
@Service
public class ActBusinessStatusService extends GenericService<ActBusinessStatus,String> implements IActBusinessStatusService,IProcessTodoDataService {

    private ActBusinessStatusMapper actBusinessStatusMapper;


    @Autowired
    private IProcessInstanceService processInstanceService;

    @Autowired
    public ActBusinessStatusService(ActBusinessStatusMapper mapper ) {
        super(mapper);
        this.actBusinessStatusMapper = mapper;
    }


    /**
     * 流程开始 往act_business_status表中插入信息
     * @param processInstanceId   流程实例对象ID
     * @param startMap   相关参数
     * @return
     */
    @Override
	public int saveActBusinessStatusData(String processInstanceId, Map<String, Object> startMap ) {
        int ret = 0;
        try{
            ActBusinessStatus actBusinessStatus = new ActBusinessStatus();
            String currentUserName = (String)startMap.get( "currentUserName" );
            String currentUserCode = (String)startMap.get( "currentUserCode" );
            String receipTitle = (String)startMap.get( "receipTitle" );
            String code = (String)startMap.get( "code" );
            Boolean iscg = (Boolean)startMap.get( "iscg" );
            String receiptId = (String)startMap.get( "receiptId" );
            String processDefKey = (String)startMap.get( "idValue" );
            actBusinessStatus.setCreateUserId( currentUserCode );
            actBusinessStatus.setCreateUserName( currentUserName );
            actBusinessStatus.setPreviousAssistant(currentUserCode);
            actBusinessStatus.setPreviousAssistantName(currentUserName);
            actBusinessStatus.setEnabled(true);
            actBusinessStatus.setRemoved(false);
            actBusinessStatus.setReceiptTitle(receipTitle);
            actBusinessStatus.setCode(code);
            actBusinessStatus.setIscg(iscg);
            actBusinessStatus.setBusinessKey(receiptId);
            actBusinessStatus.setProcessInstId(processInstanceId);
            actBusinessStatus.setStartTime(LocalDateTime.now());
            actBusinessStatus.setProcessDefKey( processDefKey );
            actBusinessStatus = actBusinessStatusMapper.save(actBusinessStatus);
            if ( actBusinessStatus != null ){
                ret = 1;
            }
        }catch(Exception e){
            FlowableDriverBusinessException.printException( e );
        }
		return ret;
	}

    /**
     * 任务提交下一步更新流程状态信息
     * @param processInstanceId   流程实例ID
     * @param nextUser            审批人
     * @return
     */
	@Override
	public int updateActBusinessStatusData( String processInstanceId, String nextUser ) {
		int ret = 0;
        try{
            ActBusinessStatus actBusinessStatus = getByProcessInst(processInstanceId);
            boolean endFlag = processInstanceService.queryProcessInstaceEndStateByProInsIdApi( processInstanceId );
            actBusinessStatus.setPreviousAssistant(nextUser);
            actBusinessStatus.setPreviousAssistantDate(LocalDateTime.now());
//            actBusinessStatus.setPreviousAssistantName(userObject.get( "truename" ).getAsString());
            actBusinessStatus = actBusinessStatusMapper.save(actBusinessStatus);
            if ( actBusinessStatus != null ){
                ret = 1;
            }
        }catch(Exception e){
            FlowableDriverBusinessException.printException( e );
        }
		return ret;
	}

    /**
     * 根据流程实例ID查询流程业务状态信息
     * @param processInstID         流程实例ID
     * @return
     */
    @Override
    public ActBusinessStatus getByProcessInst ( String processInstID ) {
        return actBusinessStatusMapper.getByProcessInst( processInstID );
    }

    /**
     * 流程结束更新结束时间
     * @return
     */
	@Override
	public int updateListenerByProcess(ActProcessInstModel actProcessInstModel) {
        int ret = 0;
        try{
            ActBusinessStatus o = getByProcessInst(actProcessInstModel.getProcessInstanceId());
            o.setEndTime( LocalDateTime.now());
            Duration duration = Duration.between( o.getEndTime(),o.getStartTime() );
            o.setDuration(duration.toNanos());
            o = actBusinessStatusMapper.save(o);
            if ( o != null ){
                ret = 1;
            }
        }catch(Exception e){
            FlowableDriverBusinessException.printException( e );
        }
        return ret;
	}

    /**
     * 根据流程实例ID删除业务流程状态数据
     * @param actBusinessStatus
     */
    @Override
    public void deletActBusinessStatusData ( ActBusinessStatus actBusinessStatus ) {
        try {
            actBusinessStatusMapper.delete( actBusinessStatus );
        }catch ( Exception e ){
            FlowableDriverBusinessException.printException( e );
        }
    }

    /**
     * 根据流程实例ID逻辑删除业务状态数据
     * @param processInstID
     * @return
     */
    @Override
    public int updateActBusDataByProInsId ( String processInstID ) {
        int ret  = 0;
        try {
            actBusinessStatusMapper.updateActBusDataByProInsId( processInstID );
            ret = 1;
        }catch ( Exception e ){
            FlowableDriverBusinessException.printException( e );
        }
        return ret;
    }

    /**
     * 分页获取所有待办数据
     * @param todoParam   查询待办参数
     * @return
     */
    @Override
    public Page<Map<String, Object>> getAllTodoPage ( Map<?, ?> todoParam, Pageable pageable ) {
        return null;
    }

    @Override
    public Page<Map<String, Object>> getMyCreateAllTodoPage(Map<?, ?> map, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Map<String, Object>> getMyApplyPage(Map<?, ?> map, Pageable pageable) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getAllTodoNoPage(Map<?, ?> map) {
        return null;
    }

    /**
     * 获取指定 userName 下面所有的待办数据
     * @param todoUserParam        查询待办参数
     * @return
     */
    @Override
    public Page<?> getTodoByUserNamePage ( Map<?, ?> todoUserParam, Pageable pageable ) {
        return null;
    }

    @Override
    public List<?> getTodoByUserNameNoPage(Map<?, ?> map) {
        return null;
    }

    /**
     * 获取指定 userName 下面所有的待办办数据(返回为Map类型)
     * @param doneUserParam         查询已办参数
     * @return
     */
    @Override
    public Page<Map<String, Object>> getTodoByUserIdPageMap ( Map<?, ?> doneUserParam, Pageable pageable ) {
        Page<Map<String, Object>> pages = null;
        String participant = (String) doneUserParam.get( "participant" );
        String dynamicWhere = (String) doneUserParam.get( "title" );
        StringBuilder inWhere = new StringBuilder();
        try {
            if ( StringUtils.isEmpty( dynamicWhere )){
                dynamicWhere = "";
            }
            pages = actBusinessStatusMapper.getTodoByUserPage(participant,dynamicWhere,pageable);
        }catch ( Exception e ){
            FlowableDriverBusinessException.printException( e );
        }
        return pages;
    }

    /**
     * 获取指定 userName 下面所有的已办数据
     * @param doneUserParam         查询已办参数
     * @return
     */
    @Override
    public Page<?> getAreadyDoneByUserIdPage ( Map<?, ?> doneUserParam, Pageable pageable ) {
        return null;
    }

    /**
     * 获取指定 userName 下面所有的已办数据 存在子流程
     * @param doneUserParam         查询已办参数
     * @return
     */
    @Override
    public Page<?> getAreadyDoneByUserIdSubFlowPage ( Map<?, ?> doneUserParam, Pageable pageable ) {
        return null;
    }

    @Override
    public List<?> getAreadyDoneByUserIdSubFlowNoPage(Map<?, ?> map) {
        return null;
    }

    /**
     * 获取指定 userName 下面所有的已办数据
     * @param doneUserParam         查询已办参数
     * @return
     */
    @Override
    public Page<Map<String, Object>> getAreadyDoneByUserIdPageMap ( Map<?, ?> doneUserParam, Pageable pageable ) {
        Page<Map<String, Object>> pages = null;
        String assistant = (String) doneUserParam.get( "assistant" );
        String dynamicWhere = (String) doneUserParam.get( "title" );
        StringBuilder inWhere = new StringBuilder();
        try {
            if ( StringUtils.isEmpty( dynamicWhere )){
                dynamicWhere = "";
            }
            pages = actBusinessStatusMapper.getByAreadyDoneAssistantPage( assistant,dynamicWhere,pageable );
        }catch ( Exception e ){
            FlowableDriverBusinessException.printException( e );
        }
        return pages;
    }

    /**
     * 获取指定 userName 下面所有的创建的工单数据
     * @param userParam         userId
     * @return
     */
    @Override
    public Page<?> getMyCreateDataPage ( Map<?, ?> userParam, Pageable pageable ) {

        Page<Map<String, Object>> pages = null;
        String creator = (String) userParam.get( "creator" );
        String dynamicWhere = (String) userParam.get( "title" );
        StringBuilder inWhere = new StringBuilder();
        try {
            if ( StringUtils.isEmpty( dynamicWhere )){
                dynamicWhere = "";
            }
            pages = actBusinessStatusMapper.getMyCreateDataPage( creator,dynamicWhere,pageable );
        }catch ( Exception e ){
            FlowableDriverBusinessException.printException( e );
        }
        return pages;
    }

    /**
     * 获取指定userName下面待阅数据
     * @param paramMap          查询待阅参数
     * @param pageable          分页参数
     * @return
     */
    @Override
    public Page<?> getMyTodoReadByUserNamePage ( Map<?, ?> paramMap, Pageable pageable ) {
        return null;
    }

    /**
     * 获取指定userName下面待阅数据
     * @param paramMap          查询待阅参数
     * @param pageable          分页参数
     * @return
     */
    @Override
    public Page<Map<String, Object>> getMyTodoReadByUserNamePageMap ( Map<?, ?> paramMap, Pageable pageable ) {
        return null;
    }

    /**
     * 获取指定userName下面已阅数据
     * @param paramMap          查询已阅参数
     * @param pageable          分页参数
     * @return
     */
    @Override
    public Page<?> getMyAreadyReadByUserNamePage ( Map<?, ?> paramMap, Pageable pageable ) {
        return null;
    }

    /**
     * 获取指定userName下面已阅数据
     * @param paramMap          查询已阅参数
     * @param pageable          分页参数
     * @return
     */
    @Override
    public Page<Map<String, Object>> getMyAreadyReadByUserNamePageMap ( Map<?, ?> paramMap, Pageable pageable ) {
        return null;
    }

    /**
     * 查询业务系统中所有正在办理的工单数据   综合流程管理平台调用接口
     * @param paramMap          查询参数
     * @param pageable          分页参数
     * @return
     */
    @Override
    public Page<Map<String, Object>> getAllTodoByManagerPage ( Map<?, ?> paramMap, Pageable pageable ) {
        return null;
    }

    /**
     * 根据主单据中的pmInstId查询流程业务操作数据
     * @param pmInsId       主单据中的pmInstId
     * @return
     */
    @Override
    public Object queryActBusinessStatusByPmInstId ( String pmInsId ) throws Exception {
        return null;
    }

    /**
     * 根据流程实例ID查询流程业务操作数据，可以会出现多条数据挂载一个businessKey（目前网络割接在用）
     * @param processInstId    流程实例ID
     * @return
     */
    @Override
    public List<?> queryActBusinessStatusByProInsId ( Long processInstId ) throws Exception {
        return null;
    }

    @Override
    public List<Map<String, Object>> getAllTodoByManagerNoPage() throws Exception {
        return null;
    }
}