package com.vkls.wisdom.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vkls.wisdom.model.vod.Teacher;
import com.vkls.wisdom.result.Result;
import com.vkls.wisdom.vo.vod.TeacherQueryVo;
import com.vkls.wisdom.vod.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 讲师 控制器
 *
 * @author vkls
 * @since 2022-12-03
 */
@RestController
@RequestMapping(value="/admin/vod/teacher",method={RequestMethod.POST,RequestMethod.GET,RequestMethod.DELETE})
@Api(tags = "讲师管理接口")
//@CrossOrigin(allowCredentials = "true")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    //查询所有的讲师
    @GetMapping("findAll")
    @ApiOperation("所有讲师列表")
    public Result findAll(){
        List<Teacher> list=teacherService.list();
        return Result.ok(list).message("查询数据成功");
    }

    //逻辑删除讲师
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("remove/{id}")
    public Result<Boolean>  removeById(@ApiParam(name = "id", value = "ID", required = true) @PathVariable String id){
        boolean isSuccess=teacherService.removeById(id);

        if(isSuccess) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    @ApiOperation("条件查询分页")
    @PostMapping("findQueryPage/{current}/{limit}")
    public Result findPage(
            @ApiParam(name = "current",value="当前页面",required = true)
            @PathVariable long current,
            @ApiParam(name = "limit",value="每页记录数",required = true)
            @PathVariable long limit,
            @ApiParam(name = "teacherVo", value = "查询对象", required = false)
            @RequestBody(required = false) TeacherQueryVo teacherQueryVo){

        //创建page对象，传递当前页和每页记录数
        Page<Teacher> pageParam = new Page<>(current, limit);

        //判断传入的条件是否为空(查询条件封装在teacherQueryVo对象中)
        //如果为空，则查询所有的讲师
        if(teacherQueryVo==null){
            System.out.println("条件为空，查询所有讲师");
            Page<Teacher> teacherPage = teacherService.page(pageParam,null);
            return Result.ok(teacherPage);
        }else{
            System.out.println("条件不为空");
            String name=teacherQueryVo.getName();
            Integer level=teacherQueryVo.getLevel();
            String joinDateBegin=teacherQueryVo.getJoinDateBegin();
            String joinDateEnd=teacherQueryVo.getJoinDateEnd();

            //封装查询条件
            QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
            if(!StringUtils.isEmpty(name)){
                wrapper.like("name",name);
            }
            if(!StringUtils.isEmpty(joinDateBegin)){
                wrapper.eq("level",level);
            }
            if(!StringUtils.isEmpty(joinDateBegin)){
                wrapper.ge("join_date",joinDateBegin);
            }
            if(!StringUtils.isEmpty(joinDateEnd)){
                wrapper.le("join_date",joinDateEnd);
            }
            Page<Teacher> teacherPage = teacherService.page(pageParam, wrapper);
            return Result.ok(teacherPage);
        }
    }

    //新增讲师
    @ApiOperation(value="新增")
    @PostMapping("saveTeacher")
    public Result save(@RequestBody Teacher teacher){
        teacherService.save(teacher);
        return Result.ok(null);

    }

    //根据id查询讲师
    @ApiOperation(value="根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public Result get(@PathVariable Long id){
        Teacher teacher=teacherService.getById(id);
        return Result.ok(teacher);
    }

    //修改讲师信息
    @ApiOperation(value="修改讲师信息")
    @PutMapping("updateTeacher")
    public Result updateById(@RequestBody Teacher teacher){
        teacherService.updateById(teacher);
        return Result.ok(null);
    }

    //批量删除讲师
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("removeBatch")
    public Result batchRemove(@RequestBody List<Long> idList) {
        teacherService.removeByIds(idList);
        return Result.ok(null);
    }

}
























