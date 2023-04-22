package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseFrontVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseWebVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-03-19
 */
public interface EduCourseService extends IService<EduCourse> {
    //添加课程基本信息方法
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    // 根据id查课程基本信息
    CourseInfoVo getCourseInfo(String courseId);

    // 修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    // 根据课程id查课程确认信息
    CoursePublishVo publishCourseInfo(String id);

    // 删课程
    void removeCourse(String courseId);

    //1 条件查询带分页查询课程
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    // 根据课程id，写sql查课程信息
    CourseWebVo getBaseCourseInfo(String courseId);


}
