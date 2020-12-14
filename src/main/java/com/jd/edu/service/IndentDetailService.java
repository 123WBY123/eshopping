package com.jd.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jd.edu.entity.CartDetail;
import com.jd.edu.entity.IndentDetail;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-29
 */
public interface IndentDetailService extends IService<IndentDetail> {

    List<IndentDetail> selectAllIndentDetail(Page<IndentDetail> pageIndentDetail,String id);

    boolean insertOneIndent(CartDetail cartDetail, String indentId);

    int updateIndentToCommodity(IndentDetail indentDetail);
}
