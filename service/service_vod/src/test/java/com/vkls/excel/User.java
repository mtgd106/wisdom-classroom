package com.vkls.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class User {
    @ExcelProperty(value="用户编号")
    private int id;

    @ExcelProperty(value="用户名称")
    private String name;
}


















