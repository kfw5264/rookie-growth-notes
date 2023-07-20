package com.masq;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.masq.model.ExportData;
import com.masq.model.OrderInfo;
import com.masq.model.UserInfo;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExportTest {


    @Test
    private void testExportData2() {




        // exportList为表头总的集合，导出的EXCEL表格的表头完全是按照这个来生成的
        List<ExcelExportEntity> exportList = new ArrayList<>();
        // 创建最底部的一级表头10个
        ExcelExportEntity nameHeader = new ExcelExportEntity("姓名", "name");
        ExcelExportEntity ageHeader = new ExcelExportEntity("年龄", "age");
        ExcelExportEntity localeHeader = new ExcelExportEntity("所在地", "locale");
        ExcelExportEntity priceHeader = new ExcelExportEntity("价格", "proce");
        ExcelExportEntity productionHeader = new ExcelExportEntity("产品", "production");
        ExcelExportEntity payTypeHeader = new ExcelExportEntity("支付方式", "payType");

        // 创建二级表头，并将二级表头对应的下级一级表头放入其中，以此类推...
        ExcelExportEntity userInfoHeader = new ExcelExportEntity("用户信息", "userInfo");
        userInfoHeader.setList(Arrays.asList(nameHeader, ageHeader, localeHeader));
        ExcelExportEntity orderInfoHeader = new ExcelExportEntity("订单信息", "orderInfo");
        orderInfoHeader.setList(Arrays.asList(priceHeader, productionHeader, payTypeHeader));

//        ExcelExportEntity titleHeader = new ExcelExportEntity("导出信息", "exportData");
//        titleHeader.setList(Arrays.asList(userInfoHeader, orderInfoHeader));

        exportList.add(userInfoHeader);
        exportList.add(orderInfoHeader);

        ExportParams params = new ExportParams("导出信息", "测试sheet", ExcelType.XSSF);


    }


    @Test
    public void testExportData() throws IOException {
        List<ExportData> list = new ArrayList<>();
        list.add(new ExportData(new UserInfo("张三", 21, "兰州"), new OrderInfo("33.21", "产品1", "支付宝")));
        list.add(new ExportData(new UserInfo("李四", 29, "杭州"), new OrderInfo("48.65", "产品2", "支付宝")));
        list.add(new ExportData(new UserInfo("王五", 38, "苏州"), new OrderInfo("133.78", "产品3", "微信")));
        list.add(new ExportData(new UserInfo("赵六", 24, "徐州"), new OrderInfo("12.00", "产品4", "线下支付")));

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("导出测试", null, "测试"), ExportData.class, list);
        File exportFile = new File("D:\\excel\\");
        if (!exportFile.exists()) {
            exportFile.mkdir();
        }

        FileOutputStream fos = new FileOutputStream("D:\\excel\\测试导出.xls");
        workbook.write(fos);

        fos.close();
    }
}
