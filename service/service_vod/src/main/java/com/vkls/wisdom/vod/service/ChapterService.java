package com.vkls.wisdom.vod.service;

import com.vkls.wisdom.model.vod.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vkls.wisdom.vo.vod.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author vkls
 * @since 2022-12-06
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getNestedTreeList(Long courseId);

    public void removeChapterByCourseId(Long id);
}
