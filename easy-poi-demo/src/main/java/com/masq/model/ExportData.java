package com.masq.model;

import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ExcelTarget("exportData")
public class ExportData {

    @ExcelEntity(id =  "userInfo", name = "用户信息")
    private UserInfo userInfo;

    @ExcelEntity(id = "orderInfo", name = "订单信息")
    private OrderInfo orderInfo;
}
