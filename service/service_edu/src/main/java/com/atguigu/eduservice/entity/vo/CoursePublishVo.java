package com.atguigu.eduservice.entity.vo;

import lombok.Data;

// 根据课程id查课程所有信息 4表联查封装数据
@Data
public class CoursePublishVo {

    private String id;
    private String title;           // 名称
    private String cover;           // 封面
    private Integer lessonNum;      // 课程数
    private String subjectLevelOne; //一级分类
    private String subjectLevelTwo; // 二级分类
    private String teacherName;     // 讲师名称
    private String price;           // 价格

}
