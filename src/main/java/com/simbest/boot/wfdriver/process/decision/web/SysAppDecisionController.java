package com.simbest.boot.wfdriver.process.decision.web;

import com.simbest.boot.base.web.controller.LogicController;
import com.simbest.boot.base.web.response.JsonResponse;
import com.simbest.boot.wfdriver.process.decision.model.SysAppDecision;
import com.simbest.boot.wfdriver.process.decision.service.ISysAppDecisionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <strong>Title : SysAppController</strong><br>
 * <strong>Description : </strong><br>
 * <strong>Create on : 2018/5/26/026</strong><br>
 * <strong>Modify on : 2018/5/26/026</strong><br>
 * <strong>Copyright (C) Ltd.</strong><br>
 *
 * @author LM liumeng@simbest.com.cn
 * @version <strong>V1.0.0</strong><br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 */
@Api(description = "主数据操作应用群组的相关接口", tags = {"决策项 基本增删改查"})
@Slf4j
@RestController
@RequestMapping(value="/action/app/decision")
public class SysAppDecisionController extends LogicController<SysAppDecision, String> {

    private ISysAppDecisionService sysAppDecisionService;

    @Autowired
    public SysAppDecisionController(ISysAppDecisionService sysAppDecisionService) {
        super(sysAppDecisionService);
        this.sysAppDecisionService=sysAppDecisionService;
    }

    /**
     * 给某一应用添加决策
     * @param sysAppDecision
     * @return
     */
    @Override
    //@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
    @ApiOperation(value = "决策项的增删改查之增加", notes = "给某一应用添加决策", tags = {"决策项 基本增删改查"})
    public JsonResponse create(@RequestBody SysAppDecision sysAppDecision){
        return super.create(sysAppDecision);
    }

    /**
     *修改应用决策信息
     * @param sysAppDecision
     * @return
     */
    @Override
    @ApiOperation(value = "决策项的增删改查之修改应用决策信息", notes = "修改应用决策信息", tags = {"决策项 基本增删改查"})
    public JsonResponse update( @RequestBody SysAppDecision sysAppDecision) {
        return super.update(sysAppDecision );
    }

    /**
     * 根据id逻辑删除
     * @param id
     * @return
     */
    @Override
    //@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
    @ApiOperation(value = "决策项的增删改查之删除应用决策信息", notes = "删除应用决策信息", tags = {"决策项 基本增删改查"})
    @ApiImplicitParam(name = "id", value = "应用决策ID",  dataType = "String", paramType = "query")
    public JsonResponse deleteById(@RequestParam String id) {
        return super.deleteById( id );
    }

    /**
     * 先修改再逻辑删除
     * @param sysAppDecision
     * @return
     */
    @Override
    @ApiOperation(value = "决策项的增删改查之删除应用决策信息", notes = "删除应用决策信息", tags = {"决策项 基本增删改查"})
    public JsonResponse delete(@RequestBody SysAppDecision sysAppDecision) {
        return super.delete(sysAppDecision);
    }

    /**
     * 批量逻辑删除应用决策信息
     * @param ids
     * @return JsonResponse
     */
    @Override
    //@PreAuthorize("hasAuthority('ROLE_SUPER')")  // 指定角色权限才能操作方法
    @ApiOperation(value = "决策项的增删改查之批量删除应用决策信息", notes = "批量删除应用决策信息", tags = {"决策项 基本增删改查"})
    public JsonResponse deleteAllByIds(@RequestBody String[] ids) {
        return  super.deleteAllByIds(ids);
    }

    /**
     *修改可见
     * @param id
     * @param enabled
     * @return
     */
    @ApiOperation(value = "决策项的增删改查之修改可见", notes = "修改可见", tags = {"决策项 基本增删改查"})
    @ApiImplicitParams ({@ApiImplicitParam(name = "id", value = "应用决策ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "enabled", value = "是否可用", required = true, dataType = "Boolean", paramType = "query")
    })
    public JsonResponse updateEnable(@RequestParam String id, @RequestParam Boolean enabled) {
        return  super.updateEnable( id,enabled );
    }

    //批量修改可见

    /**
     *根据id查询应用决策信息
     * @param id
     * @return
     */
    @Override
    @ApiOperation(value = "决策项的增删改查之根据id查询应用决策信息", notes = "根据id查询应用决策信息", tags = {"决策项 基本增删改查"})
    @ApiImplicitParam(name = "id", value = "应用决策ID", dataType = "String", paramType = "query")
    @PostMapping({"/findById","/findById/sso"})
    public JsonResponse findById(@RequestParam String id) {
        return super.findById( id );
    }

    /**
     *获取应用决策信息列表并分页
     * @param page
     * @param size
     * @param direction
     * @param properties
     * @param sysAppDecision
     * @return
     */
    @Override
    @ApiOperation(value = "决策项的增删改查之获取应用决策信息列表并分页", notes = "获取应用决策信息列表并分页", tags = {"决策项 基本增删改查"})
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "int", paramType = "query", //
                    required = true, example = "1"), //
            @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", paramType = "query", //
                    required = true, example = "10"), //
            @ApiImplicitParam(name = "direction", value = "排序规则（asc/desc）", dataType = "String", //
                    paramType = "query"), //
            @ApiImplicitParam(name = "properties", value = "排序规则（属性名称）", dataType = "String", //
                    paramType = "query") //
    })
    @PostMapping({"/findAll","/findAll/sso"})
    public JsonResponse findAll( @RequestParam(required = false, defaultValue = "1") int page, //
                                 @RequestParam(required = false, defaultValue = "10") int size, //
                                 @RequestParam(required = false) String direction, //
                                 @RequestParam(required = false) String properties, //
                                 @RequestBody SysAppDecision sysAppDecision //
    ) {

        return super.findAll( page,size,direction, properties,sysAppDecision);
    }

}
