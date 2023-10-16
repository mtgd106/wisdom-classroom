package com.vkls.wisdom.vod.service.impl;

import com.vkls.wisdom.model.vod.VideoVisitor;
import com.vkls.wisdom.vo.vod.VideoVisitorCountVo;
import com.vkls.wisdom.vod.mapper.VideoVisitorMapper;
import com.vkls.wisdom.vod.service.VideoVisitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 视频来访者记录表 服务实现类
 * </p>
 *
 * @author vkls
 * @since 2022-12-08
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor> implements VideoVisitorService {

    @Override
    public Map<String, Object> findCount(Long courseId, String startDate, String endDate) {
        //调用mapper中的SQL语句
        List<VideoVisitorCountVo> videoVisitorVoList=baseMapper.findCount(courseId,startDate,endDate);
        //
        Map<String, Object> map=new HashMap<String, Object>();
        //创建两个list集合，一个代表所有日期，一个代表日期对应的数量
        List<String> dateList=videoVisitorVoList.stream().map(VideoVisitorCountVo::getJoinTime).collect(Collectors.toList());

        List<Integer> countList=videoVisitorVoList.stream().map(VideoVisitorCountVo::getUserCount).collect(Collectors.toList());

        map.put("xData",dateList);
        map.put("yData",countList);

        return map;
    }
}














