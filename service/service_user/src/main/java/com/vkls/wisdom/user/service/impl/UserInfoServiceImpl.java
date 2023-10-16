package com.vkls.wisdom.user.service.impl;

import com.vkls.wisdom.model.user.UserInfo;
import com.vkls.wisdom.user.mapper.UserInfoMapper;
import com.vkls.wisdom.user.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author vkls
 * @since 2022-12-09
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
