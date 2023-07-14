package com.masq.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget("orderInfo")
public class OrderInfo {

    @Excel(name = "价格")
    private String price;

    @Excel(name = "产品")
    private String production;

    @Excel(name = "支付方式")
    private String payType;
}
