package com.vkls.wisdom.vod.service;

import com.vkls.wisdom.model.vod.Video;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author vkls
 * @since 2022-12-06
 */
public interface VideoService extends IService<Video> {
    public void removeVideoByCourseId(Long id);

    void removeVideoById(Long id);
}
