package com.vkls.wisdom.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vkls.wisdom.model.order.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vkls.wisdom.vo.order.OrderInfoQueryVo;

import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务类
 * </p>
 *
 * @author vkls
 * @since 2022-12-08
 */
public interface OrderInfoService extends IService<OrderInfo> {

    Map<String, Object> findPageOrderInfo(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo);
}
