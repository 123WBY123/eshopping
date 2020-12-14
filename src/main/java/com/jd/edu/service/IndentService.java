package com.jd.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jd.edu.entity.Indent;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-29
 */
public interface IndentService extends IService<Indent> {

    List<Indent> selectAllIndent(Page<Indent> pageIndent, Integer status);

    Indent getByIndentId(String indentId);

    Indent getByUserId(String userId);

    Integer updateIndentById(Indent indent);

    List<Indent> getAllByUserId(Page<Indent> pageIndent,String id);

}
