package com.vkls.wisdom.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vkls.wisdom.model.vod.Video;
import com.vkls.wisdom.vod.mapper.VideoMapper;
import com.vkls.wisdom.vod.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vkls.wisdom.vod.service.VodService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author vkls
 * @since 2022-12-06
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {


    @Resource
    private VodService vodService;

    //根据课程id删除所有小节信息(包括表中的信息以及腾讯云中的视频)
    @Override
    public void removeVideoByCourseId(Long id) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        //查出属于该课程的所有视频
        List<Video> videoList=baseMapper.selectList(wrapper);
        for(Video video:videoList){
            //获取视频对应的云端存储路径
            String videoSourceId=video.getVideoSourceId();
            //如果视频id不为空，则删除上传到腾讯云中的视频
            if(!StringUtils.isEmpty(videoSourceId)){
                vodService.removeVideo(videoSourceId);
            }
        }
        baseMapper.delete(wrapper);
    }

    // 根据小节id删除小节信息及视频
    // 删除小节时，要先删除腾讯云中的视频，然后才能删除数据表中的表示小节的数据
    @Override
    public void removeVideoById(Long id) {
        //1 删除视频
        Video video = baseMapper.selectById(id);
        //获取视频id
        String videoSourceId = video.getVideoSourceId();
        //如果视频id不为空，调用方法删除
        if(!StringUtils.isEmpty(videoSourceId)) {
            vodService.removeVideo(videoSourceId);
        }
        //2 删除小节
        baseMapper.deleteById(id);
    }
}
















