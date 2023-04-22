package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

// excel对应实体类
@Data
public class SubjectData {

    //一级分类
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    //二级分类
    @ExcelProperty(index = 1)
    private String twoSubjectName;

}
