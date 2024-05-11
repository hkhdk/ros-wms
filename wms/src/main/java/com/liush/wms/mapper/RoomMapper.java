package com.liush.wms.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.liush.wms.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoomMapper extends BaseMapper<Room> {
    @SqlParser(filter = true)

    IPage pageCC(IPage<Room> page, @Param(Constants.WRAPPER) Wrapper wrapper, @Param("room")String room);
    IPage pageCCs(IPage<Room> page, @Param(Constants.WRAPPER) Wrapper wrapper, @Param("room")String room);

    Boolean remove(String sequence, String room);
    Boolean saves(String room, String userBlock);
}
