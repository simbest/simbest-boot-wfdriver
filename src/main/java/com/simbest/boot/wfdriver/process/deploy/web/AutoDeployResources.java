package com.simbest.boot.wfdriver.process.deploy.web;/**
 * @author Administrator
 * @create 2019/12/7 10:22.
 */

import com.simbest.boot.wfdriver.api.CallFlowableProcessApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *@ClassName AutoDeployResources
 *@Description 自动部署流程定义文件，并完成wfengine部署
 * 1.需配置flowable.autoDeploy = true
 * 2扫描项目classpath:/processes下后缀是.bpmn .bpmn20.xml 和.zip的文件。
 *@Author Administrator
 *@Date 2019/12/7 10:22
 *@Version 1.0
 **/
@Slf4j
@Component
public class AutoDeployResources {

    @Value( "${flowable.autoDeploy}" )
    public Boolean autoDeploy;

    @Autowired
    private CallFlowableProcessApi callFlowableProcessApi;

    protected ResourcePatternResolver resourcePatternResolver;

    public static final String DEPLOYMENTNAMEHINT = "CustomAutoDeployment";

    @PostConstruct
    public void deployResources() throws IOException {
        if(autoDeploy!=null && autoDeploy==true){
            resourcePatternResolver = new PathMatchingResourcePatternResolver();
            List<Resource> result = new ArrayList<>();
            Collections.addAll(result, resourcePatternResolver.getResources("classpath*:/processes/**/**.bpmn"));
            Collections.addAll(result, resourcePatternResolver.getResources("classpath*:/processes/**/**.bpmn20.xml"));
            Collections.addAll(result, resourcePatternResolver.getResources("classpath*:/processes/**/**.zip"));
            if(result!=null && result.size()>0){
                for(Resource r : result){
                    try {
                        callFlowableProcessApi.deployments_Add(r.getFile(), r.getFilename(),DEPLOYMENTNAMEHINT,null);
                    }catch (Exception e){
                        log.error("################################################ERROR#######################################");
                        log.error("##ERROR流程部署异常，请检查环境配置或者流程配置"+e.getMessage());
                        break;
                    }
                }
            }
        }else{
            log.debug("Flowable------------autoDeploy="+autoDeploy+"不启用自动部署!------------------");
        }

    }

}
