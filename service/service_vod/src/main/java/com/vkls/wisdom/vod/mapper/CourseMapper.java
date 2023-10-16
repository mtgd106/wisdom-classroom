package com.vkls.wisdom.vod.mapper;

import com.vkls.wisdom.model.vod.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vkls.wisdom.vo.vod.CoursePublishVo;
import com.vkls.wisdom.vo.vod.CourseVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author vkls
 * @since 2022-12-06
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo selectCoursePublishVoById(Long id);

    CourseVo selectCourseVoById(Long courseId);
}
