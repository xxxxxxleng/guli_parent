package com.atguigu.demo;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
        // 实现excel写的操作
        // 设置写入文件夹地址和excel文件名
        String filename = "F:\\write.xlsx";
        // 掉方法实现写操作
        EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(data());
        // 读操作
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    //循环设置要添加的数据，最终封装到list集合中
    private static List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("张三" + i);
            list.add(data);
        }
        return list;
    }
}
