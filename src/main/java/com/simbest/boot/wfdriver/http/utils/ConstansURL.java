package com.simbest.boot.wfdriver.http.utils;

public class ConstansURL {

    public static final String DEPLOYMENTS_ADD = "/app/provide/deployment/anonymous/deploymentsAddResource";	//部署
    public static final String INSTANCES_START = "/app/provide/instances/anonymous/instancesStart";	//	启动流程实例
    public static final String tasksQueryNoPage = "/app/provide/tasks/anonymous/tasksQueryNoPage";	//	查询任务
    public static final String TASKS_COMPLETE = "/app/provide/tasks/anonymous/tasksComplete";	//	完成任务
    public static final String TASKS_ADD_COMMENT = "/app/provide/tasks/anonymous/tasksAddComment";	//	添加评论
    public static final String FREE_FLOW = "/app/provide/tasks/anonymous/freeFlow";	//	自由流
    public static final String CREATE_TASK_ENTITYIMPLS = "/app/provide/tasks/anonymous/createTaskEntityImpls";	//	手动创建任务（多人）
    public static final String CREATE_TASK_ENTITYIMPL = "/app/provide/tasks/anonymous/createTaskEntityImpl";	//	手动创建任务（单人）
    public static final String FINSH_TASK = "/app/provide/tasks/anonymous/finshTask";	//	完成当前节点，不再流程下一步
    public static final String ADD_MULTI_INSTANCE_EXECUTION = "/app/provide/tasks/anonymous/addMultiInstanceExecution";	//多实例加签
    public static final String DELETE_MULTI_INSTANCE_EXECUTION = "/app/provide/tasks/anonymous/deleteMultiInstanceExecution";	//	多实例减签
    public static final String GET_DIAGRAM_BY_PROCESS_INSTANCEID = "/app/provide/definitions/anonymous/getDiagramhttp";	//获取流程图
    public static final String GET_DIAGRAM_BY_PROCESS_KEY = "/app/provide/definitions/anonymous/getDiagramByKey";	//获取流程图
    public static final String DEFINITIONS_GETBY_KEY = "/app/provide/definitions/anonymous/definitionsGetByKey";	//根据key获得一个流程定义
    public static final String DELETE_PROCESS_INSTANCE = "/app/provide/instances/anonymous/deleteProcessInstance";	//	删除流程实例
    public static final String UPGRADE_PROCESS_INSTANCE_VERSION = "/app/provide/instances/anonymous/upgradeProcessInstanceVersion";	//升级流程实例
    public static final String CHECK_IS_LAST_VERSION = "/app/provide/instances/anonymous/checkIsLastVersion";	//升级流程实例

}
