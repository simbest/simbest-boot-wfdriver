package com.simbest.boot.wfdriver.enums;

import cn.hutool.core.util.StrUtil;
import com.simbest.boot.base.enums.GenericEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * <strong>Title : BoProcessInstStateEnum</strong><br>
 * <strong>Description : 业务流程状态枚举</strong><br>
 * <strong>Create on : 2020-1-9</strong><br>
 * <strong>Modify on : 2020-1-9</strong><br>
 * <strong>Copyright (C) Ltd.</strong><br>
 *
 * @author LJW lijianwu@simbest.com.cn
 * @version <strong>V1.0.0</strong><br>
 * <strong>修改历史:</strong><br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 */
@Slf4j
public enum BoProcessInstStateEnum{

    PROCESS_INST_STATE_DRAFT(100,"草稿"),
    PROCESS_INST_STATE_RUNNING(200,"运行中"),
    PROCESS_INST_STATE_LOGOUT(300,"注销"),
    PROCESS_INST_STATE_END(400,"归档");

    @Setter
    @Getter
    private int value;

    @Setter
    @Getter
    private String desc;

    BoProcessInstStateEnum ( int value, String desc) {
        this.setValue(value);
        this.setDesc(desc);
    }

    /**
     * 功能描述: 通过value获取枚举
     *
     * @param  value
     * @return
     * @date 2020/2/15 1:06
     * @auther ljw
     */
    public static BoProcessInstStateEnum getTypeByValue( int value){
        for ( BoProcessInstStateEnum decisionFilterTypeEnum : BoProcessInstStateEnum.values() ){
            if ( value == decisionFilterTypeEnum.getValue() ){
                return decisionFilterTypeEnum;
            }
        }
        return null;
    }

    /**
     * 功能描述: 通过value获取描述信息
     *
     * @param  value
     * @return
     * @date 2020/2/15 1:06
     * @auther ljw
     */
    public static String getDescByValue(int value){
        for ( BoProcessInstStateEnum decisionFilterTypeEnum : BoProcessInstStateEnum.values() ){
            if ( value == decisionFilterTypeEnum.getValue() ){
                return decisionFilterTypeEnum.getDesc();
            }
        }
        return "";
    }

    public static void main ( String[] args ) {
        System.out.println( BoProcessInstStateEnum.PROCESS_INST_STATE_DRAFT.getValue() );
    }
}
