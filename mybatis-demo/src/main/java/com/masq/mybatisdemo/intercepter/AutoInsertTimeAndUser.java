package com.masq.mybatisdemo.intercepter;

import com.masq.mybatisdemo.annotation.CreateBy;
import com.masq.mybatisdemo.annotation.CreateTime;
import com.masq.mybatisdemo.annotation.UpdateBy;
import com.masq.mybatisdemo.annotation.UpdateTime;
import com.masq.mybatisdemo.pojo.BaseEntity;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

@Component
@Intercepts(@Signature(type= Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class AutoInsertTimeAndUser implements Interceptor {

    private static final String[] MAPPER_METHOD_PARAM_MAP = {"record", "collection"};

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];

        // 获取sql命令
        SqlCommandType sqlCommandType = ms.getSqlCommandType();

        if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
            // 获取参数
            Object parameter = invocation.getArgs()[1];
            //批量操作时
            if (parameter instanceof MapperMethod.ParamMap) {
                MapperMethod.ParamMap map = (MapperMethod.ParamMap) parameter;
                Object obj = map.get("list");
                List<?> list = (List<?>) obj;
                if (list != null) {
                    for (Object o : list) {
                        setParameter(o, sqlCommandType);
                    }
                }
            } else {
                setParameter(parameter, sqlCommandType);
            }
        }


        return invocation.proceed();
    }

    public void setParameter(Object parameter, SqlCommandType sqlCommandType) throws Throwable {
        Class<?> aClass = parameter.getClass();
        Field[] declaredFields;
        //如果常用字段提取了公共类 BaseEntity
        //判断BaseEntity是否是父类
        if (BaseEntity.class.isAssignableFrom(aClass)) {
            // 获取父类私有成员变量
            declaredFields = aClass.getSuperclass().getDeclaredFields();
        } else {
            // 获取私有成员变量
            declaredFields = aClass.getDeclaredFields();
        }
        for (Field field : declaredFields) {
            if (SqlCommandType.INSERT.equals(sqlCommandType)) { // insert 语句插入 createBy
                if (field.getAnnotation(CreateBy.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter, "defaultUser");
                }

                if (field.getAnnotation(CreateTime.class) != null) { // insert 语句插入 createTime
                    field.setAccessible(true);
                    field.set(parameter, new Timestamp(System.currentTimeMillis()));
                }
            }

            if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                if (field.getAnnotation(UpdateTime.class) != null) { // update 语句插入 updateTime
                    field.setAccessible(true);
                    field.set(parameter, new Timestamp(System.currentTimeMillis()));
                }
                if (field.getAnnotation(UpdateBy.class) != null) { // update 语句插入 updateBy
                    field.setAccessible(true);
                    field.set(parameter, "updateUser");
                }
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
