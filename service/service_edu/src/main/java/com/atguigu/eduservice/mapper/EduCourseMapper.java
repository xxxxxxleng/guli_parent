package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2023-03-19
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    // 根据课程id查课程确认信息
    public CoursePublishVo getPublishCourseInfo(String courseId);

    // 根据课程id，写sql查课程信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
