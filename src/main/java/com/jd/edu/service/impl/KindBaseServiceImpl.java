package com.jd.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.edu.entity.Commodity;
import com.jd.edu.entity.KindBase;
import com.jd.edu.mapper.CommodityMapper;
import com.jd.edu.mapper.KindBaseMapper;
import com.jd.edu.service.KindBaseService;
import com.jd.edu.utils.exceptionhandler.EsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ysc666
 * @since 2020-10-31
 */
@Service
public class KindBaseServiceImpl extends ServiceImpl<KindBaseMapper, KindBase> implements KindBaseService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private KindBaseMapper kindBaseMapper;

    @Override
    public boolean saveKindBase(KindBase kindBase) {
        List<KindBase> kindBases = null;
        //查询该类别名称是否已经被禁用存在 存在则不能添加 避免重名
        kindBases = kindBaseMapper.showAllDelete(kindBase.getKindName().trim());
        if(kindBases.size() != 0){
            //说明重复
            throw new EsException(20001,"该商品类别暂时禁用，请换一个名称");
        }
        kindBases = kindBaseMapper.showAllUse(kindBase.getKindName().trim());
        if(kindBases.size() != 0){
            //说明重复
            throw new EsException(20001,"该商品类别已被使用，请换一个名称");
        }
        int end = baseMapper.insert(kindBase);
        return end == 1 ? true : false;
    }

    @Override
    public boolean updateKindBaseById(KindBase kindBase) {
        int result = 0;
        //查询该类别名称是否已经存在 存在则不能添加 避免重名
        QueryWrapper<KindBase> wrapper = new QueryWrapper<>();
        wrapper.eq("kind_name",kindBase.getKindName());
        result = baseMapper.selectCount(wrapper);
        if(result == 1){ //不知道是重复还是本数据更新
            wrapper.eq("kind_id",kindBase.getKindId());
            result = baseMapper.selectCount(wrapper);
            if(result == 1){
                return true;
            }else{
                //说明重复
                throw new EsException(20001,"该商品类别已经被使用，请换一个名称");
            }
        }
        //正常更新
        result = baseMapper.updateById(kindBase);
        return result == 1 ? true : false;
    }

    @Override
    public boolean removeByKindBaseId(String id) {
        //先遍历商品表 若没有该类别商品上架 才能逻辑删除该商品类别
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        wrapper.eq("commodity_kind",id);
        wrapper.eq("commodity_status",2);
        Integer count = commodityMapper.selectCount(wrapper);
        if(count != 0){ //商品还有在上架中的
            throw new EsException(20001,"还有商品在上架,该商品类别不能删除");
        }
        int result = baseMapper.deleteById(id);
        return result == 1 ? true : false;
    }

    @Override
    public List<KindBase> showKindDelete(Page page) {
        List<KindBase> list = kindBaseMapper.showKindDelete(page);
        return list;
    }

    @Override
    public Integer updateKindToUse(String id) {
        Integer result = kindBaseMapper.updateKindToUse(id);
        return result;
    }

    @Override
    public KindBase getDeleteKindById(String id) {
        KindBase kindBase = kindBaseMapper.getOneDelete(id);
        return kindBase;
    }

    @Override
    public boolean updateDeleteKind(KindBase kindBase) {
        int result = kindBaseMapper.updateDeleteKind(kindBase);
        return result == 1 ? true : false;
    }
}
