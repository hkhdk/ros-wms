package com.liush.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liush.wms.common.QueryPageParam;
import com.liush.wms.common.Result;
import com.liush.wms.entity.Room;
import com.liush.wms.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/Room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Room room) {
        return roomService.saves(room.getName(), room.getUserBlock()) ? Result.suc() : Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Room room) {
        return roomService.updateById(room) ? Result.suc() : Result.fail();
    }

    //删除
//    @GetMapping("/del")
//    public Result del(@RequestParam String sequence) {
//        return roomService.removeById(sequence) ? Result.suc() : Result.fail();
//    }

    @GetMapping("/del")
    public Result del(@RequestParam String sequence, @RequestParam String room) {
        System.out.println(room);
        return roomService.removeByTId(sequence, room) ? Result.suc() : Result.fail();
    }


    /*
    task(巡检)
     */
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        System.out.println(name);
        Page<Room> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Room> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            //lambdaQueryWrapper.like(Room::getName, name);
        }
        
        IPage result = roomService.pageCC(page, lambdaQueryWrapper, name);
        return Result.suc(result.getRecords(), result.getTotal());
    }

    /*
    task_r(管理员手动操作)
     */
    @PostMapping("/listPages")
    public Result listPages(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        System.out.println(name);
        Page<Room> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Room> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            //lambdaQueryWrapper.like(Room::getName, name);
        }

        IPage result = roomService.pageCCs(page, lambdaQueryWrapper, name);
        return Result.suc(result.getRecords(), result.getTotal());
    }
}

