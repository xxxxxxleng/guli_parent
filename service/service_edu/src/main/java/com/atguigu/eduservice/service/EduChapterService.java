package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-03-19
 */
public interface EduChapterService extends IService<EduChapter> {
    // 课程大纲列表，根据课程id查
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    // 删除章节
    boolean deleteChapter(String chapterId);

    // 2 根据课程id删章节
    void removeChapterByCourseId(String courseId);
}
