package com.simbest.boot.wfdriver.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <strong>Title : </strong>ProcessSateEnum<br>
 * <strong>Description : </strong>流程状态<br>
 * <strong>Create on : 2019/01/16</strong><br>
 * <strong>Modify on : 2019/01/16</strong><br>
 * <strong>Copyright (C) Ltd.</strong><br>
 *
 * @author LJW lijianwu@simbest.com.cn
 * @version <strong>V1.0.0</strong><br>
 * <strong>修改历史:</strong><br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 */
public enum ProcessSateEnum {

    RUNNING("运行",2),END("结束",7),HANG("挂起",8);

    @Getter@Setter
    private String name;

    @Getter@Setter
    private int num;

    ProcessSateEnum ( String name, int num ) {
        this.name = name;
        this.num = num;
    }

    public static String getName(int num){
        for ( ProcessSateEnum processSateEnum:ProcessSateEnum.values() ){
            if(processSateEnum.getNum() == num){
                return processSateEnum.name;
            }
        }
        return null;
    }
}
