package com.vkls.wisdom.vod.mapper;

import com.vkls.wisdom.model.vod.VideoVisitor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vkls.wisdom.vo.vod.VideoVisitorCountVo;

import java.util.List;

/**
 * <p>
 * 视频来访者记录表 Mapper 接口
 * </p>
 *
 * @author vkls
 * @since 2022-12-08
 */
public interface VideoVisitorMapper extends BaseMapper<VideoVisitor> {

    ////显示统计数据
    List<VideoVisitorCountVo> findCount(Long courseId, String startDate, String endDate);

}
