package com.simbest.boot.wfdriver.task;

import com.simbest.boot.base.exception.Exceptions;
import com.simbest.boot.wfdriver.exceptions.WorkFlowBusinessRuntimeException;
import com.simbest.boot.wfdriver.process.bussiness.model.ActBusinessStatus;
import com.simbest.boot.wfdriver.task.model.TaskCallbackLog;
import com.simbest.boot.wfdriver.task.model.TaskCallbackRetry;
import com.simbest.boot.wfdriver.task.service.ITaskCallBackLogService;
import com.simbest.boot.wfdriver.task.service.ITaskCallBackRetyService;
import com.simbest.boot.wfdriver.task.todo.TodoCloseInterface;
import com.simbest.boot.wfdriver.task.todo.TodoOpenInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * <strong>Title : UserTaskSubmit</strong><br>
 * <strong>Description : 用户待办提交</strong><br>
 * <strong>Create on : $date$</strong><br>
 * <strong>Modify on : $date$</strong><br>
 * <strong>Copyright (C) Ltd.</strong><br>
 *
 * @author LJW lijianwu@simbest.com.cn
 * @version <strong>V1.0.0</strong><br>
 * <strong>修改历史:</strong><br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 */
@Slf4j
@Component
public class UserTaskSubmit {

    @Autowired
    private ITaskCallBackLogService taskCallBackLogService;

    @Autowired
    private ITaskCallBackRetyService taskCallBackRetyService;

    @Autowired
    private TodoOpenInterface todoOpenInterface;

    @Autowired
    private TodoCloseInterface todoCloseInterface;

   /* @Autowired
    private TodoCanceInterface todoCanceInterface;*/

    /**
     * 推送统一代办
     * @param businessStatus     业务流程操作对象
     * @param userName           审批人
     */
    public void submitTodoOpen(ActBusinessStatus businessStatus, String userName){
        LocalDateTime callbackStartDate = LocalDateTime.now();
        Boolean callbackResult = true;
        String callbackError = null;
        try {
            if ( businessStatus == null ){
                throw new WorkFlowBusinessRuntimeException( userName + " Submit Open Todo Is Null!");
            }
            todoOpenInterface.execution( businessStatus,userName );
        }catch ( WorkFlowBusinessRuntimeException e ){
            log.error( "UserTaskSubmit submitTodoOpen Error>>>>" + Exceptions.getStackTraceAsString(e) );
            TaskCallbackRetry taskCallbackRetry = new TaskCallbackRetry();
            taskCallbackRetry.setTaskJobClass(taskCallBackRetyService.getClass().getName());
            taskCallbackRetry.setExecuteTimes(1);
            taskCallbackRetry.setLastExecuteDate( LocalDateTime.now() );
            taskCallbackRetry.setCallbackType("openTodoCallback");
            taskCallbackRetry.setActBusinessStatusId(businessStatus.getId());
            taskCallbackRetry.setUserName(userName);
            log.debug( "UserTaskSubmit submitTodoOpen Error>>>>taskCallBackRetyService>>>>" + taskCallBackRetyService );
            taskCallbackRetry = taskCallBackRetyService.insert(taskCallbackRetry);
            log.debug( "UserTaskSubmit submitTodoOpen Error>>>>" + taskCallbackRetry.toString() );
            callbackResult = false;
            callbackError = StringUtils.substring( Exceptions.getStackTraceAsString(e), 0, 1999 );
        }finally {
            TaskCallbackLog taskCallbackLog = new TaskCallbackLog();
            taskCallbackLog.setActBusinessStatusId(businessStatus.getId());
            taskCallbackLog.setCallbackType("openTodoCallback");
            taskCallbackLog.setCallbackStartDate(callbackStartDate);
            taskCallbackLog.setCallbackEndDate(LocalDateTime.now());
            Duration duration = Duration.between( taskCallbackLog.getCallbackEndDate(),callbackStartDate );
            taskCallbackLog.setCallbackDuration(duration.toMillis());
            taskCallbackLog.setCallbackResult(callbackResult);
            taskCallbackLog.setCallbackError(callbackError);
            log.debug( "UserTaskSubmit submitTodoOpen finally>>>>taskCallBackLogService>>>>" + taskCallBackLogService );
            taskCallbackLog = taskCallBackLogService.insert(taskCallbackLog);
            log.debug( "UserTaskSubmit submitTodoOpen finally>>>>" + taskCallbackLog.toString() );
        }
    }

    /**
     * 核销统一代办
     * @param businessStatus     业务流程操作对象
     * @param userName           审批人
     */
    public void submitTodoClose( ActBusinessStatus businessStatus, String userName){
        LocalDateTime callbackStartDate = LocalDateTime.now();
        Boolean callbackResult = true;
        String callbackError = null;
        try {
            if ( businessStatus == null ){
                throw new WorkFlowBusinessRuntimeException( userName + " Submit Close Todo Is Null!");
            }
            todoCloseInterface.execution( businessStatus,userName );
        }catch ( Exception e ){
            log.error( "UserTaskSubmit submitTodoClose  Error>>>>" + Exceptions.getStackTraceAsString(e) );
            TaskCallbackRetry taskCallbackRetry = new TaskCallbackRetry();
            taskCallbackRetry.setTaskJobClass(taskCallBackRetyService.getClass().getName());
            taskCallbackRetry.setExecuteTimes(1);
            taskCallbackRetry.setLastExecuteDate( LocalDateTime.now() );
            taskCallbackRetry.setCallbackType("closeTodoCallback");
            taskCallbackRetry.setActBusinessStatusId(businessStatus.getId());
            taskCallbackRetry.setUserName(userName);
            log.debug( "UserTaskSubmit submitTodoOpen Error>>>>taskCallBackRetyService>>>>" + taskCallBackRetyService );
            taskCallbackRetry = taskCallBackRetyService.insert(taskCallbackRetry);
            log.debug( "UserTaskSubmit submitTodoClose Error>>>>" + taskCallbackRetry.toString() );
            callbackResult = false;
            callbackError = StringUtils.substring( Exceptions.getStackTraceAsString(e), 0, 1999 );
        }finally {
            TaskCallbackLog taskCallbackLog = new TaskCallbackLog();
            taskCallbackLog.setActBusinessStatusId(businessStatus.getId());
            taskCallbackLog.setCallbackType("closeTodoCallback");
            taskCallbackLog.setCallbackStartDate(callbackStartDate);
            taskCallbackLog.setCallbackEndDate(LocalDateTime.now());
            Duration duration = Duration.between( taskCallbackLog.getCallbackEndDate(),callbackStartDate );
            taskCallbackLog.setCallbackDuration(duration.toMillis());
            taskCallbackLog.setCallbackResult(callbackResult);
            taskCallbackLog.setCallbackError(callbackError);
            log.debug( "UserTaskSubmit submitTodoClose finally>>>>taskCallBackLogService>>>>" + taskCallBackLogService );
            taskCallbackLog = taskCallBackLogService.insert(taskCallbackLog);
            log.debug( "UserTaskSubmit submitTodoClose finally>>>>" + taskCallbackLog.toString() );
        }
    }

    /**
     * 删除统一代办
     * @param businessStatus     业务流程操作对象
     * @param userName           审批人
     */
    public void submitTodoCancel( ActBusinessStatus businessStatus, String userName){ }
}
