package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-03-18
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    // 添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {

        try {
            // 文件输入流
            InputStream in = file.getInputStream();
            // 调方法读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 查所有一级分类 parentid = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectsList = baseMapper.selectList(wrapperOne);

        // 查所有二级分类 parentid != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        // 创list，存最终封装数据
        ArrayList<OneSubject> finalSubjectList = new ArrayList<>();

        //3 封装一级分类
        // 查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值，封装到要求的list集合里面 List<OneSubject> finalSubjectList
        // 遍历一级分类集合
        for (int i = 0; i < oneSubjectsList.size(); i++) {
            // 得到集合的每个EduSubject对象
            EduSubject eduSubject = oneSubjectsList.get(i);
            // 将EduSubject里的值取出存入oneSubject,对象拷贝
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject, oneSubject);
            finalSubjectList.add(oneSubject);

            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            ArrayList<TwoSubject> twoFinalSubjectsList = new ArrayList<>();
            // 遍历二级分类
            for (int v = 0; v < twoSubjectList.size(); v++) {
                // 获取每个二级分类对象
                EduSubject tSubject = twoSubjectList.get(v);
                // 判断二级分类parentid和一级id是否一样
                if (tSubject.getParentId().equals(oneSubject.getId())) {
                    //把tSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    twoFinalSubjectsList.add(twoSubject);
                }
            }
            // 把所有二级分类放到一级分类里
            oneSubject.setChildren(twoFinalSubjectsList);
        }
        return finalSubjectList;
    }
}
