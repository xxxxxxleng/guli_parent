package com.atguigu.educms.service.impl;

import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-03-26
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    // 根据方法对其返回结果进行缓存，下次请求时，如果缓存存在，则直接读取缓存数据返回；
    // 如果缓存不存在，则执行方法，并把返回的结果存入缓存中。一般用在查询方法上。
    @Cacheable(key = "'selectIndexList'",value = "banner")      // 'selectIndexList' 要加''
    // 查所有banner
    @Override
    public List<CrmBanner> selectAllBanner() {
        // 根据id进行降序排，显示排序之后的前两条数据
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // last方法，拼接sql语句
        wrapper.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }
}
