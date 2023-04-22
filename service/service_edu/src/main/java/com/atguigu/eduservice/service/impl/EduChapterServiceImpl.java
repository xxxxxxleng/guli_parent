package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-03-19
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    // 小节
    @Autowired
    private EduVideoService videoService;

    // 课程大纲列表，根据课程id查
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        // 1 根据课程id查所有章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<EduChapter> cList = baseMapper.selectList(wrapper);

        // 2 根据章节查所有小节
        QueryWrapper<EduVideo> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id", courseId);
        List<EduVideo> vList = videoService.list(wrapper1);

        // 封装最终数据
        List<ChapterVo> finalList = new ArrayList<>();

        // 3 遍历章节集合并封装
        for (int i = 0; i < cList.size(); i++) {
            //得到每个章节
            EduChapter eduChapter = cList.get(i);
            // 将EduChapter转换为ChapterVo，拷贝
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            // 存入最终集合
            finalList.add(chapterVo);

            // 创集合，封装小节
            ArrayList<VideoVo> voArrayList = new ArrayList<>();

            // 4 遍历小节集合并封装  一个章节有很多小节  放入章节循环
            for (int v = 0; v < vList.size(); v++) {
                // 得到每个小节
                EduVideo eduVideo = vList.get(v);
                // 判断小节的chapterid是否与章节的id一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    // 将EduVideo转换为VideoVo，拷贝
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    // 存入小节集合
                    voArrayList.add(videoVo);
                }
            }
            chapterVo.setChildren(voArrayList);
        }
        return finalList;
    }

    // 删除章节
    @Override
    public boolean deleteChapter(String chapterId) {
        // 根据章节id查小节表，如果有小节不能删
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        // 查
        int count = videoService.count(wrapper);
        // 判断是否有小节
        if (count > 0) {
            throw new GuliException(20001, "有小节，不能删");
        } else {
            // 可以删
            int result = baseMapper.deleteById(chapterId);
            // 判断是否删除成功，返回Boolean类型，>0 true成功，否则失败
            return result > 0;
        }
    }

    // 2 根据课程id删章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        // delete() 需要条件对象
        QueryWrapper<EduChapter> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);

    }
}
