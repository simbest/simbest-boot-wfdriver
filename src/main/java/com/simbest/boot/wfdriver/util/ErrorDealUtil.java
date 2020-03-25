package com.simbest.boot.wfdriver.util;

import cn.hutool.core.map.MapUtil;
import com.simbest.boot.wfdriver.http.utils.ConstantsUtils;

import java.util.Map;

/**
 * <strong>Title : ErrorDealUtil</strong><br>
 * <strong>Description : 错误处理工具类</strong><br>
 * <strong>Create on : 2020/3/25</strong><br>
 * <strong>Modify on : 2020/3/25</strong><br>
 * <strong>Copyright (C) Ltd.</strong><br>
 *
 * @author LJW lijianwu@simbest.com.cn
 * @version <strong>V1.0.0</strong><br>
 * <strong>修改历史:</strong><br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 */
public final class ErrorDealUtil {

    public static int getErrorCode( Map<String,Object> resMap ){
        Integer res = -1;
        Integer state = MapUtil.getInt( resMap,"state" );
        if ( state == ConstantsUtils.SUCCESS ){
            res = ConstantsUtils.SUCCESS;
        }
        return res;
    }
}
