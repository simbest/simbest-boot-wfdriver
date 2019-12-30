package com.simbest.boot.wfdriver.enums;

import com.simbest.boot.base.enums.GenericEnum;

/**
 * <strong>Title : ToDoEnum/strong><br>
 * <strong>Description : 统一代办枚举类</strong><br>
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
public enum  ToDoEnum implements GenericEnum {

    open("推送待办"),close("核销待办"),cancel("删除待办");

    private String value;

    ToDoEnum ( String value ) {
        this.value = value;
    }

    /**
     * @return the value
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    @Override
    public void setValue( String value) {
        this.value = value;
    }
}
