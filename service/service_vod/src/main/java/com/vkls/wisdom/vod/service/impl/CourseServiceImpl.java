package com.vkls.wisdom.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vkls.wisdom.model.vod.*;
import com.vkls.wisdom.vo.vod.*;
import com.vkls.wisdom.vod.mapper.CourseMapper;
import com.vkls.wisdom.vod.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author vkls
 * @since 2022-12-06
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Resource
    private TeacherService teacherService;

    @Resource
    private SubjectService subjectService;

    @Resource
    private CourseDescriptionService courseDescriptionService;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private VideoService videoService;

    @Resource
    private ChapterService chapterService;

    //查询课程信息
    @Override
    public Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo) {


        Long TeacherId=courseQueryVo.getTeacherId();
        Long subjectId=courseQueryVo.getSubjectId();
        Long subjectParentId=courseQueryVo.getSubjectParentId();
        String title=courseQueryVo.getTitle();

        QueryWrapper<Course> wrapper=new QueryWrapper<>();

        if(!StringUtils.isEmpty(TeacherId)){
            wrapper.eq("teacher_id",TeacherId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }

        //调用page函数，参数为page参数和查询条件
        Page<Course> courseList = page(pageParam, wrapper);

        //获取查询结果的总记录数，总页数和当前页等信息
        long totalCount=courseList.getTotal();  //总记录数
        long totalPage=courseList.getPages();  //总页数
        long currentPage=courseList.getCurrent(); //当前页
        long size=courseList.getSize(); //每页记录数

        List<Course> records=courseList.getRecords();
        //遍历封装讲师和分类名称
        records.forEach(this::getTeacherOrSubjectName);
        //封装返回数据
        Map<String,Object> map=new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",records);

        return map;
    }

    //从前端传入的实体类CourseFormVo中包含了课程的基本信息和课程的详细描述
    //现在将课程的基本信息保存到Course表中，将课程的详细描述保存到CourseDescription表中
    @Override
    public Long addCourse(CourseFormVo courseFormVo) {
        //保存课程基本信息
        Course course=new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        baseMapper.insert(course);

        //保存课程详细信息
        CourseDescription courseDescription=new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        //令CourseDescription表中的id字段和Course表中的id字段保持一致
        //这样，就不再使用CourseDescription表中courseId字段
        courseDescription.setId(course.getId());
        courseDescriptionService.save(courseDescription);

        //返回课程id
        return course.getId();
    }

    //根据id查询课程信息
    @Override
    public CourseFormVo getCourseFormVoById(Long id) {
        //根据id先从Course表中获取数据
        Course course=baseMapper.selectById(id);
        if(course==null)
            return null;

        //再根据课程id从course_description表中获取课程介绍信息
        CourseDescription courseDescription=courseDescriptionService.getById(id);

        //创建CourseFormVo对象
        CourseFormVo courseFormVo=new CourseFormVo();
        BeanUtils.copyProperties(course,courseFormVo);
        if(courseDescription!=null)
          courseFormVo.setDescription(courseDescription.getDescription());

        return courseFormVo;
    }

    //根据id修改信息
    @Override
    public Long updateCourseById(CourseFormVo courseFormVo) {
        //修改课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.updateById(course);

        //修改详细信息
        CourseDescription courseDescription=new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescriptionService.updateById(courseDescription);

        //返回课程id
        return course.getId();
    }

    //查询将要发布的课程的信息
    @Override
    public CoursePublishVo getCoursePublishVo(Long id) {
        return courseMapper.selectCoursePublishVoById(id);
    }

    //发布课程
    @Override
    public void publishCourseById(Long id) {
        Course course=baseMapper.selectById(id);
        course.setId(id);
        course.setPublishTime(new Date());
        course.setStatus(1);
        baseMapper.updateById(course);
    }

    //删除课程
    @Override
    public void removeCourseById(Long id) {
        //根据课程id删除小节
        videoService.removeVideoByCourseId(id);
        //根据课程id删除章节
        chapterService.removeChapterByCourseId(id);
        //根据课程id删除描述
        courseDescriptionService.removeById(id);
        //根据课程id删除课程
        baseMapper.deleteById(id);
    }

    //根据类别查询课程
    @Override
    public Map<String,Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        String title=courseQueryVo.getTitle();  //名称
        Long subjectId=courseQueryVo.getSubjectId();  //二级分类
        Long subjectParentId=courseQueryVo.getSubjectParentId();   //一级分类
        Long teacherId=courseQueryVo.getTeacherId();  //讲师

        //封装条件
        QueryWrapper<Course> wrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id",teacherId);
        }
        Page<Course> pages=baseMapper.selectPage(pageParam,wrapper);
        long totalCount = pages.getTotal();//总记录数
        long totalPage = pages.getPages();//总页数
        long currentPage = pages.getCurrent();//当前页
        long size = pages.getSize();//每页记录数

        //每页数据集合
        List<Course> records = pages.getRecords();
        records.forEach(this::getTeacherOrSubjectName);

        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",records);

        return map;
    }

    @Override
    public Map<String, Object> getInfoById(Long courseId) {
        //将课程的浏览数量加1
        Course course=baseMapper.selectById(courseId);
        course.setViewCount(course.getViewCount()+1);
        baseMapper.updateById(course);

        Map<String,Object> map=new HashMap<>();
        CourseVo courseVo=baseMapper.selectCourseVoById(courseId);
        //查询课程章节数据
        List<ChapterVo> chapterVoList=chapterService.getNestedTreeList(courseId);
        //查询详细信息
        CourseDescription courseDescription=courseDescriptionService.getById(courseId);
        //查询讲师信息
        Teacher teacher = teacherService.getById(course.getTeacherId());

        //TODO 后续完善
        Boolean isBuy = false;

        map.put("courseVo", courseVo);
        map.put("chapterVoList", chapterVoList);
        map.put("description", null != courseDescription ?
                courseDescription.getDescription() : "");
        map.put("teacher", teacher);
        map.put("isBuy", isBuy);//是否购买
        return map;
    }

    //查询这门课程属于哪个分类和讲授这门课程的讲师 将信息封装到course对象中，然后返回
    private void getTeacherOrSubjectName(Course item) {
        //获取课程讲师信息
        Teacher teacher=teacherService.getById(item.getTeacherId());
        if(teacher!=null){
            item.getParam().put("teacherName",teacher.getName());
        }
        //获取课程专业父级信息
        Subject subjectParent=subjectService.getById(item.getSubjectParentId());
        if(subjectParent!=null){
            item.getParam().put("subjectParentTitle",subjectParent.getTitle());
        }
        //获取课程所属类别
        Subject subjectId = subjectService.getById(item.getSubjectId());
        if(subjectId != null) {
            item.getParam().put("subjectTitle",subjectId.getTitle());
        }
    }
}




































