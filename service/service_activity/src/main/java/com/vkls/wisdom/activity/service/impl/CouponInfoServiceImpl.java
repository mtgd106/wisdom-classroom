package com.vkls.wisdom.activity.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vkls.wisdom.activity.service.CouponUseService;
import com.vkls.wisdom.client.user.UserInfoFeignClient;
import com.vkls.wisdom.model.activity.CouponInfo;
import com.vkls.wisdom.activity.mapper.CouponInfoMapper;
import com.vkls.wisdom.activity.service.CouponInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vkls.wisdom.model.activity.CouponUse;
import com.vkls.wisdom.model.user.UserInfo;
import com.vkls.wisdom.vo.activity.CouponUseQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 优惠券信息 服务实现类
 * </p>
 *
 * @author vkls
 * @since 2022-12-09
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {

    @Resource
    private CouponUseService couponUseService;

    @Resource
    private UserInfoFeignClient userInfoFeignClient;


    @Override
    public IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo) {
        //获取条件
        Long couponId = couponUseQueryVo.getCouponId();
        String couponStatus = couponUseQueryVo.getCouponStatus();
        String getTimeBegin = couponUseQueryVo.getGetTimeBegin();
        String getTimeEnd = couponUseQueryVo.getGetTimeEnd();
        //封装条件
        QueryWrapper<CouponUse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(couponId)) {
            wrapper.eq("coupon_id",couponId);
        }
        if(!StringUtils.isEmpty(couponStatus)) {
            wrapper.eq("coupon_status",couponStatus);
        }
        if(!StringUtils.isEmpty(getTimeBegin)) {
            wrapper.ge("get_time",getTimeBegin);
        }
        if(!StringUtils.isEmpty(getTimeEnd)) {
            wrapper.le("get_time",getTimeEnd);
        }

        //调用方法查询
        IPage<CouponUse> page=couponUseService.page(pageParam,wrapper);
        //封装用户昵称和手机号
        List<CouponUse> couponUseList=page.getRecords();
        couponUseList.forEach(this::getUserInfoBycouponUse);
        return page;
    }
    //将用户昵称和手机号封装到订单信息中
    private void getUserInfoBycouponUse(CouponUse couponUse){

        Long userId=couponUse.getUserId();
        if(!StringUtils.isEmpty(userId)){
            UserInfo userInfo=userInfoFeignClient.getById(userId);
            if(userInfo!=null){
                couponUse.getParam().put("nickName",userInfo.getNickName());
                couponUse.getParam().put("phone",userInfo.getPhone());
            }
        }

    }
}







































