package com.simbest.boot.wfdriver.task.todo;


import com.simbest.boot.wfdriver.process.bussiness.model.ActBusinessStatus;

/**
 * <strong>Title : TodoCloseInterface</strong><br>
 * <strong>Description : 核销统一代办</strong><br>
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
public interface TodoCloseInterface {

    /**
     * 核销统一代办
     * @param businessStatus     业务流程操作对象
     * @param userName           审批人
     */
    void execution(ActBusinessStatus businessStatus, String userName);
}
