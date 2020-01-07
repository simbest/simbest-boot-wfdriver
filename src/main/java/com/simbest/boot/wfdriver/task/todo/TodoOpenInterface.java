package com.simbest.boot.wfdriver.task.todo;


import com.simbest.boot.wfdriver.process.bussiness.model.ActBusinessStatus;
import com.simbest.boot.wfdriver.process.listener.model.ActTaskInstModel;

/**
 * <strong>Title : TodoOpenInterface</strong><br>
 * <strong>Description : 推送统一代办</strong><br>
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
public interface TodoOpenInterface {

    /**
     * 推送统一代办
     * @param actTaskInstModel     业务流程操作对象
     * @param userName           审批人
     */
    void execution( ActBusinessStatus actBusinessStatus, ActTaskInstModel actTaskInstModel, String userName);
}
