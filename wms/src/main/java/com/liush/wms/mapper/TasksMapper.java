package com.liush.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.liush.wms.entity.Tasks;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TasksMapper extends BaseMapper<Tasks> {

    IPage pageCC(IPage<Tasks> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
