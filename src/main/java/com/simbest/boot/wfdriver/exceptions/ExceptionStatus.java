package com.simbest.boot.wfdriver.exceptions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <strong>Title : ExceptionStatus</strong><br>
 * <strong>Description : 异常状态对象类</strong><br>
 * <strong>Create on : 2019/02/23</strong><br>
 * <strong>Modify on : 2019/02/23</strong><br>
 * <strong>Copyright (C) Ltd.</strong><br>
 *
 * @author LJW lijianwu@simbest.com.cn
 * @version <strong>V1.0.0</strong><br>
 * <strong>修改历史:</strong><br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 */
public class ExceptionStatus implements Serializable {
    private static final long serialVersionUID = 7222712722288755289L;
    private String _code;
    private String _message;
    public Object[] _params;
    private Throwable _exception;
    private Set<Throwable> _childrenException;
    private static final String ERROR_CODE = "ErrCode: ";
    private static final String ERROR_MESSAGE = "Message: ";

    public ExceptionStatus(String code) {
        this(code, (String)null, (Throwable)null, (Object[])null);
    }

    public ExceptionStatus(String code, String message) {
        this(code, message, (Throwable)null, (Object[])null);
    }

    public ExceptionStatus(String code, Object[] params) {
        this(code, (String)null, (Throwable)null, params);
    }

    public ExceptionStatus(String code, String message, Object[] params) {
        this(code, message, (Throwable)null, params);
    }

    public ExceptionStatus(String code, Throwable exception) {
        this(code, (String)null, exception, (Object[])null);
    }

    public ExceptionStatus(String code, Throwable exception, Object[] params) {
        this(code, (String)null, exception, params);
    }

    public ExceptionStatus(String code, String message, Throwable exception) {
        this(code, message, exception, (Object[])null);
    }

    public ExceptionStatus(String code, String message, Throwable exception, Object[] params) {
        this._exception = null;
        this._childrenException = new LinkedHashSet();
        this.setCode(code);
        this.setMessage(message);
        this.setParams(params);
        this.setException(exception);
    }

    public ExceptionStatus(String code, Object[] params, Throwable cause ) {
        this.setCode( code );
        this.setParams( params );
        this.setException( cause );
    }

    public Object[] getParams() {
        return this._params;
    }

    public void setParams(Object[] params) {
        this._params = params;
    }

    public Throwable getException() {
        return this._exception;
    }

    protected void setException(Throwable exception) {
        this._exception = exception;
    }

    public void setMessage(String message) {
        this._message = message;
    }

    public String getMessage() {
        return this._message;
    }

    public void setCode(String code) {
        this._code = code;
    }

    public String getCode() {
        return this._code;
    }

    public void addChild(Throwable exception) {
        if (exception != null) {
            this._childrenException.add(exception);
        }
    }

    public void addChild(Throwable[] exceptions) {
        if (exceptions != null) {
            this._childrenException.addAll( Arrays.asList(exceptions));
        }
    }

    public int getChildSize() {
        return this._childrenException.size();
    }

    public Throwable[] getChildren() {
        return (Throwable[])this._childrenException.toArray(new Throwable[0]);
    }
}

