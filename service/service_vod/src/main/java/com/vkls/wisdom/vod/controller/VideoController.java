package com.vkls.wisdom.vod.controller;


import com.vkls.wisdom.model.vod.Video;
import com.vkls.wisdom.result.Result;
import com.vkls.wisdom.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author vkls
 * @since 2022-12-06
 */
@Api(tags = "课程小结（课时）")
@RestController
@RequestMapping(value="/admin/vod/video")
//@CrossOrigin
public class VideoController {
    @Resource
    private VideoService videoService;

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Video video = videoService.getById(id);
        return Result.ok(video);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody Video video) {
        videoService.save(video);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PostMapping("update")
    public Result updateById(@RequestBody Video video) {
        videoService.updateById(video);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        videoService.removeVideoById(id);
        return Result.ok(null);
    }































































}

