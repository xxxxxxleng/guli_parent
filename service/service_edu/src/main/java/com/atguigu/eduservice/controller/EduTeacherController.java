package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 讲师 前端控制器
 *
 * @author testjava
 * @since 2023-03-14
 */
@RestController
@CrossOrigin    //解决跨域问题
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    // 查所有僵尸
    @GetMapping("/findAll")
    public R list() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    // 讲师逻辑删除
    @DeleteMapping("{id}")
    public R deleteTeacherById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 分页查
    @GetMapping("{page}/{limit}")
    public R pageList(
//            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
//            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        eduTeacherService.page(pageParam, null);
        List<EduTeacher> records = pageParam.getRecords();     // 数据list集合
        long total = pageParam.getTotal();      // 总记录数
        return R.ok().data("total", total).data("rows", records);
    }

    // 分页条件查
    @PostMapping("/pageTeacher/{page}/{limit}")
    public R pageTeacher(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery) {
        // 创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        // 添加条件
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }

        // 调用方法实现
        eduTeacherService.page(pageTeacher, queryWrapper);

        List<EduTeacher> records = pageTeacher.getRecords();    // 数据集合
        long total = pageTeacher.getTotal();        // 总记录数
        return R.ok().data("total", total).data("rows", records);
    }

    // 添加讲师
    @PostMapping("/addTeacher")
    public R save(@RequestBody EduTeacher eduTeacher) {
        eduTeacherService.save(eduTeacher);
        return R.ok();
    }

    // 根据id查讲师
    @GetMapping("/getTeacher/{id}")
    public R getById(@PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    // 根据id改讲师
    @PostMapping("/updateTeacher")
    public R update(@RequestBody EduTeacher teacher) {
        eduTeacherService.updateById(teacher);
        return R.ok();
    }

}
