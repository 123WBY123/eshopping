package com.jd.edu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.edu.entity.Indent;
import com.jd.edu.mapper.IndentMapper;
import com.jd.edu.service.IndentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-29
 */
@Service
public class IndentServiceImpl extends ServiceImpl<IndentMapper, Indent> implements IndentService {

    @Autowired
    private IndentMapper indentMapper;

    @Override
    public List<Indent> selectAllIndent(Page<Indent> pageIndent,Integer status) {
        List<Indent> list = indentMapper.selectAllIndent(pageIndent,status);
        return list;
    }

    @Override
    public Indent getByIndentId(String indentId) {
        Indent indent = indentMapper.getByIndentId(indentId);
        return indent;
    }

    @Override
    public Indent getByUserId(String userId) {
        Indent indent = indentMapper.getByUserId(userId);
        return indent;
    }

    @Override
    public Integer updateIndentById(Indent indent) {
        int result = indentMapper.updateIndentById(indent);
        return result;
    }

    @Override
    public List<Indent> getAllByUserId(Page<Indent> pageIndent,String id) {
        List<Indent> list = indentMapper.getAllByUserId(pageIndent,id);
        return list;
    }

}
