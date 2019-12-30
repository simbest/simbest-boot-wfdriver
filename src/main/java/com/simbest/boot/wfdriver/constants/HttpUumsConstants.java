package com.simbest.boot.wfdriver.constants;

/**
 * <strong>Title : HttpUumsConstants</strong><br>
 * <strong>Description : 请求uums相关常量</strong><br>
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
public final class HttpUumsConstants {

    public static final String UUMS_ADDRESS = "http://10.92.82.44:8001/uums";

    /**
     * 根据username查询用户信息
     */
    public static final String SYS_USER_INTERFACE = "/action/user/user/findByUsername/sso";

    /**
     * 根据username查询组织信息
     */
    public static final String SYS_USER_ORG_INTERFACE = "/action/org/org/findOrgsByUsername/sso";

    public static final String BPS_LISTENER_APP_CODE = "BPSAPP";

    private HttpUumsConstants(){}

}
