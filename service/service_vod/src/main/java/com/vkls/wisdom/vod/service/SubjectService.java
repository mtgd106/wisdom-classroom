package com.vkls.wisdom.vod.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.vkls.wisdom.model.vod.Subject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author vkls
 * @since 2022-12-06
 */
public interface SubjectService extends IService<Subject> {

    //课程分类列表
    List<Subject> selectSubjectList(Long id);

    /**
     * 导出
     * @param response
     */
    void exportData(HttpServletResponse response);

    void importDictData(MultipartFile file);
}
