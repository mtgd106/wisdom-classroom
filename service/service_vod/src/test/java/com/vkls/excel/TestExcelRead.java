package com.vkls.excel;

import com.alibaba.excel.EasyExcel;

public class TestExcelRead {
    public static void main(String[] args) {
        String fileName = "D:\\DeskTop\\11.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, User.class, new ExcelListener()).sheet().doRead();
    }
}
