package com.vkls.wisdom.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vkls.wisdom.model.vod.Chapter;
import com.vkls.wisdom.model.vod.Video;
import com.vkls.wisdom.vo.vod.ChapterVo;
import com.vkls.wisdom.vo.vod.VideoVo;
import com.vkls.wisdom.vod.mapper.ChapterMapper;
import com.vkls.wisdom.vod.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vkls.wisdom.vod.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author vkls
 * @since 2022-12-06
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Resource
    private VideoService videoService;

    //根据课程id查询该课程的所有章节，以及章节下的课时(Video)信息
    @Override
    public List<ChapterVo> getNestedTreeList(Long courseId) {
        //List列表存储所有的章节
        List<ChapterVo> chapterVoList=new ArrayList<ChapterVo>();

        //获取章节信息
        LambdaQueryWrapper<Chapter> queryWrapperChapter=new LambdaQueryWrapper<>();
        //获取指定课程id的章节信息
        queryWrapperChapter.eq(Chapter::getCourseId,courseId);
        //按照sort字段将章节升序排序
        queryWrapperChapter.orderByAsc(Chapter::getSort,Chapter::getId);
        List<Chapter> chapterList=baseMapper.selectList(queryWrapperChapter);

        //获取课时信息
        LambdaQueryWrapper<Video> queryWrapperVideo=new LambdaQueryWrapper<>();
        //根据课程id查询所有属于该课程的课时
        queryWrapperVideo.eq(Video::getCourseId,courseId);
        queryWrapperVideo.orderByAsc(Video::getSort,Video::getId);
        List<Video> videoList=videoService.list(queryWrapperVideo);

        //找到每个章节所包含的视频
        for(int i=0;i<chapterList.size();i++){

            Chapter chapter=chapterList.get(i);
            //创建ChapterVo对象
            ChapterVo chapterVo=new ChapterVo();
            //将chapter对象转存为chapterVo对象
            BeanUtils.copyProperties(chapter,chapterVo);
            chapterVoList.add(chapterVo);

            List<VideoVo> videoVoList=new ArrayList<VideoVo>();
            //每处理一个章节，就要遍历一遍所有的视频，判断视频是否属于当前章节
            for(int j=0;j<videoList.size();j++){
                Video video=videoList.get(j);
                //查询属于当前章节的课时信息
                if(chapter.getId().equals(video.getChapterId())){
                    VideoVo videoVo=new VideoVo();
                    //将video对象转存为videoVo对象
                    BeanUtils.copyProperties(video,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            //将章节和属于该章节的课时信息绑定
            chapterVo.setChildren(videoVoList);
        }
        return chapterVoList;
    }

    //根据课程id删除章节
    @Override
    public void removeChapterByCourseId(Long id) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}




















































