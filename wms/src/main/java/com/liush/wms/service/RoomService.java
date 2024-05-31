package com.liush.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liush.wms.entity.Room;

public interface RoomService extends IService<Room> {
    IPage pageCC(IPage<Room> page, Wrapper wrapper, String room);
    IPage pageCCs(IPage<Room> page, Wrapper wrapper, String room);
    Boolean removeByTId(String sequence, String room);
    Boolean saves(String room, String userBlock, String good);
}
