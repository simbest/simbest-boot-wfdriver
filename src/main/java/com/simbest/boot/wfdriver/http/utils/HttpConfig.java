package com.simbest.boot.wfdriver.http.utils;/**
 * @author Administrator
 * @create 2019/12/25 10:05.
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *@ClassName HttpConfig
 *@Description TODO
 *@Author Administrator
 *@Date 2019/12/25 10:05
 *@Version 1.0
 **/
@Component
public class HttpConfig {
    @Value( "${flowable.wfengineHost}" )
    public String wfengineHost;

    @Value( "${flowable.sourcesystemid}" )
    public String sourcesystemid;

    @Value( "${flowable.sourcesystemname}" )
    public String sourcesystemname;

    @Value( "${flowable.accesstoken}" )
    public String accesstoken;
}
