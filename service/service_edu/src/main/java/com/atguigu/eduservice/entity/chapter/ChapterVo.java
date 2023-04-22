package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// 章节
@Data
public class ChapterVo {

    private String id;

    private String title;

    // 章节小节1对多
    private List<VideoVo> children = new ArrayList<>();

}
