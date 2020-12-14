package com.jd.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jd.edu.entity.KindBase;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-31
 */
public interface KindBaseService extends IService<KindBase> {

    boolean saveKindBase(KindBase kindBase);

    boolean updateKindBaseById(KindBase kindBase);

    boolean removeByKindBaseId(String id);

    List<KindBase> showKindDelete(Page page);

    Integer updateKindToUse(String id);

    KindBase getDeleteKindById(String id);

    boolean updateDeleteKind(KindBase kindBase);
}
