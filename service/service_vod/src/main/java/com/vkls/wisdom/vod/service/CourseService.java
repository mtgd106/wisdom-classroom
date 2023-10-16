package com.vkls.wisdom.vod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vkls.wisdom.model.vod.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vkls.wisdom.vo.vod.CourseFormVo;
import com.vkls.wisdom.vo.vod.CoursePublishVo;
import com.vkls.wisdom.vo.vod.CourseQueryVo;
import com.vkls.wisdom.vo.vod.CourseVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author vkls
 * @since 2022-12-06
 */
public interface CourseService extends IService<Course> {

    //查询所有课程信息
    Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    //添加新的课程
    Long addCourse(CourseFormVo courseFormVo);

    //根据id获取课程信息
    CourseFormVo getCourseFormVoById(Long id);

    //根据id修改课程信息
    Long updateCourseById(CourseFormVo courseFormVo);

    //根据id获取课程发布信息
    CoursePublishVo getCoursePublishVo(Long id);

    //根据id发布课程
    void publishCourseById(Long id);

    void removeCourseById(Long id);

    Map<String,Object>  findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    Map<String, Object> getInfoById(Long courseId);
}
