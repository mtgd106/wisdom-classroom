package com.vkls.wisdom.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vkls.wisdom.model.activity.CouponInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vkls.wisdom.model.activity.CouponUse;
import com.vkls.wisdom.vo.activity.CouponUseQueryVo;

/**
 * <p>
 * 优惠券信息 服务类
 * </p>
 *
 * @author vkls
 * @since 2022-12-09
 */
public interface CouponInfoService extends IService<CouponInfo> {

    IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo);
}
