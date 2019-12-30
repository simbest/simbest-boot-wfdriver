package com.simbest.boot.wfdriver.exceptions;

/**
 * <strong>Title : 程序运行时异常</strong><br>
 * <strong>Description : </strong><br>
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
public class WorkFlowBusinessRuntimeException extends RuntimeException {

    private ExceptionStatus exceptionStatus;

    public WorkFlowBusinessRuntimeException(String message) {
        super(message);
    }

    public WorkFlowBusinessRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkFlowBusinessRuntimeException(Throwable cause) {
        super(cause);
    }

    public WorkFlowBusinessRuntimeException(String code, Object[] params, Throwable cause){
        super(cause);
        this.exceptionStatus = new ExceptionStatus( code,params,cause );
    }
}
