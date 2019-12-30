package com.simbest.boot.wfdriver.constants;

/**
 * <strong>Title : ProcessConstants</strong><br>
 * <strong>Description : 流程相关常量</strong><br>
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
public final class ProcessConstants {

    private ProcessConstants(){}

    /**
     * •基于两个节点之间的时间回退
     目标活动完成时间到当前活动启动时间这个时间段中所有已完成的活动；
     */
    public final static String PROCESS_BACK_TIME = "time";
    /**
     * •基于两个节点之间的路径回退
     所有能从当前活动到达目标活动的路径上的所有活动
     防止中间有路由节点,执行单步回退不成功
     */
    public final static String PROCESS_BACK_PATH = "path";
    /**
     * •回退到最近的人工活动
     以当前活动为参照，到达最近完成的人工活动的路径上的所有活动；
     */
    public final static String PROCESS_BACK_RECENT_MANUAL = "recent_manual";
    /**
     * •单步回退
     以当前活动为参照，所有此活动的上一个活动；
     该回退操作不适用当前节点上一个节点是路由节点
     */
    public final static String PROCESS_BACK_ONE_STEP = "one_step";
    /**
     * •简单回退
     所有能从当前活动到达目标活动的路径上的所有活动；
     */
    public final static String PROCESS_BACK_SIMPLE = "simple";

    /**
     * 单个审批人
     */
    public final static String SINGLE_ASSIGNEE = "singleAssignee";

    /**
     * 多个审批人
     */
    public final static String MULTI_SINGLE_ASSIGNEE_PROCESS = "multiAssigneeProcess";

    /**
     * 带路由提交审批人
     */
    public final static String EXCLUSIVE_GATEWAY = "exclusiveGatewayAndTimerBoundaryEventProcess";

    public final static String PROCESS_FILE_PATH = "processes/";
}
