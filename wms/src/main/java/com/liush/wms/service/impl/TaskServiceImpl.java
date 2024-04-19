package com.liush.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liush.wms.entity.Tasks;
import com.liush.wms.mapper.TasksMapper;
import com.liush.wms.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TaskServiceImpl extends ServiceImpl<TasksMapper, Tasks> implements TaskService {

    @Resource
    private TasksMapper tasksMapper;

    @Override
    public IPage pageCC(IPage<Tasks> page, Wrapper wrapper) {
        return tasksMapper.pageCC(page, wrapper);
    }
}
