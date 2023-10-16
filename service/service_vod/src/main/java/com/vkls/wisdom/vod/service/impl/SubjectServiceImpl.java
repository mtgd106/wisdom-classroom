package com.vkls.wisdom.vod.service.impl;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vkls.wisdom.model.vod.Subject;
import com.vkls.wisdom.vo.vod.SubjectEeVo;
import com.vkls.wisdom.vod.listener.SubjectListener;
import com.vkls.wisdom.vod.mapper.SubjectMapper;
import com.vkls.wisdom.vod.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author vkls
 * @since 2022-12-06
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectListener subjectListener;

    @Override
    public List<Subject> selectSubjectList(Long id) {

        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<Subject> subjectList = baseMapper.selectList(wrapper);

        //判断list集合每个Subject对象是否还有子节点
        for (Subject subject:subjectList) {
            Long subjectId = subject.getId();
            //判断该条目下是否有子节点 如果有其他条目的父id是该条目的id，则该条目有子节点
            boolean isChild = this.isChildren(subjectId);
            //根据返回值将hasChildren属性设置为true或false
            subject.setHasChildren(isChild);
        }
        return subjectList;
    }
    //判断当前条目下面是否有子节点
    private boolean isChildren(Long id) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        //如果selectCount方法的返回结果大于0，说明当前条目有子节点
        return count>0;
    }

    //课程分类导出
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");

            //查询出所有的课程数据
            List<Subject> dictList = baseMapper.selectList(null);

            List<SubjectEeVo> dictVoList = new ArrayList<>(dictList.size());

            //将subject对象包装成subjectEeVo对象再导出
            for(Subject dict : dictList) {
                SubjectEeVo dictVo = new SubjectEeVo();
                //只将两个类中相同的属性进行拷贝赋值
                BeanUtils.copyProperties(dict,dictVo);
                dictVoList.add(dictVo);
            }

            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class).sheet("课程分类").doWrite(dictVoList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //课程信息导入
    @Override
    public void importDictData(MultipartFile file) {

        try {
            EasyExcel.read(file.getInputStream(),
                    SubjectEeVo.class,subjectListener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
