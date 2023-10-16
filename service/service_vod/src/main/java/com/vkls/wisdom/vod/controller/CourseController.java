package com.vkls.wisdom.vod.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vkls.wisdom.model.vod.Course;
import com.vkls.wisdom.result.Result;
import com.vkls.wisdom.vo.vod.CourseFormVo;
import com.vkls.wisdom.vo.vod.CoursePublishVo;
import com.vkls.wisdom.vo.vod.CourseQueryVo;
import com.vkls.wisdom.vod.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author vkls
 * @since 2022-12-06
 */
@Api(tags = "课程管理接口")
@RestController
@RequestMapping(value="/admin/vod/course")
//@CrossOrigin
public class CourseController {

    @Resource
    private CourseService courseService;

    //获取所有课程
    @ApiOperation(value = "获取课程分页列表")
    @GetMapping("{page}/{limit}")
    public Result courseList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseVo", value = "查询对象", required = false)
            @RequestBody  CourseQueryVo courseQueryVo) {
        Page<Course> pageParam = new Page<>(page, limit);
        Map<String,Object> map = courseService.findPageCourse(pageParam, courseQueryVo);

        return Result.ok(map);
    }

    //添加新的课程的基本信息
    @ApiOperation(value = "新增课程")
    @PostMapping("addCourse")
    public Result addCourse(@RequestBody CourseFormVo courseFormVo) {
        Long courseId = courseService.addCourse(courseFormVo);
        return Result.ok(courseId);
    }

    //根据id获取课程信息
    @ApiOperation(value="根据ID获取课程信息")
    @GetMapping("getCourseById/{id}")
    public Result getCourseById(@PathVariable Long id){

        CourseFormVo course = courseService.getCourseFormVoById(id);

        return Result.ok(course);
    }

    //修改课程信息
    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseById")
    public Result updateCourseById(@RequestBody CourseFormVo courseFormVo) {
        Long courseId=courseService.updateCourseById(courseFormVo);
        return Result.ok(courseId);
    }

    @ApiOperation("根据id获取将要发布的课程的信息")
    @GetMapping("getCoursePublishVo/{id}")
    public Result getCoursePublishVoById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable Long id){

        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);
        return Result.ok(coursePublishVo);
    }

    @ApiOperation("根据id发布课程")
    @PutMapping("publishCourseById/{id}")
    public Result publishCourseById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable Long id){

        courseService.publishCourseById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除课程")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        courseService.removeCourseById(id);
        return Result.ok();
    }
}

