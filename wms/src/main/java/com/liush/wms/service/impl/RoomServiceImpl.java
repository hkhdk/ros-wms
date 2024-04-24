package com.liush.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liush.wms.entity.Room;
import com.liush.wms.mapper.RoomMapper;
import com.liush.wms.service.RoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Resource
    private RoomMapper roomMapper;

    @Override
    public IPage pageCC(IPage<Room> page, Wrapper wrapper, String table) {
        return roomMapper.pageCC(page, wrapper, table);
    }

    @Override
    public Boolean removeByTId(String sequence, String table) {
        return roomMapper.remove(sequence, table);
    }
}
