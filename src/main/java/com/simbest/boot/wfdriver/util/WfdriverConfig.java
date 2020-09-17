package com.simbest.boot.wfdriver.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <strong>Title : </strong><br>
 * <strong>Description : </strong><br>
 * <strong>Create on : 2020/7/22</strong><br>
 * <strong>Modify on : 2020/7/22</strong><br>
 * <strong>Copyright (C) Ltd.</strong><br>
 *
 * @author ZB zhaobo@simbest.com.cn
 * @version <strong>V1.0.0</strong><br>
 * <strong>修改历史:</strong><br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 */
@Component
public class WfdriverConfig {

    @Value("${flowable.sourcesystemid}")
    @Getter
    private String appCode;

}
