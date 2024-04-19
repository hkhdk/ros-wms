package com.liush.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liush.wms.common.QueryPageParam;
import com.liush.wms.common.Result;
import com.liush.wms.entity.Tasks;
import com.liush.wms.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
@RestController
@RequestMapping("/tasks")
public class TasksController {
    @Autowired
    private TaskService taskService;

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Tasks tasks) {
        return taskService.save(tasks) ? Result.suc() : Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Tasks tasks) {
        return taskService.updateById(tasks) ? Result.suc() : Result.fail();
    }

    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return taskService.removeById(id) ? Result.suc() : Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("roomName");
//        Integer result = (Integer) param.get("result");
//        String storage = (String) param.get("createTime");

        Page<Tasks> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Tasks> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Tasks::getRoomName, name);
        }

//        lambdaQueryWrapper.eq(Tasks::getResult, result);
//
//        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)) {
//            lambdaQueryWrapper.eq(Tasks::getCreateTime, storage);
//        }

        IPage result_ = taskService.pageCC(page, lambdaQueryWrapper);
        return Result.suc(result_.getRecords(), result_.getTotal());
    }
}
