package com.masq.mybatisdemo.pojo;

import com.masq.mybatisdemo.annotation.CreateBy;
import com.masq.mybatisdemo.annotation.CreateTime;
import com.masq.mybatisdemo.annotation.UpdateBy;
import com.masq.mybatisdemo.annotation.UpdateTime;

import java.util.Date;

public class BaseEntity {

    @CreateBy
    private String insertBy;

    @CreateBy
    @UpdateBy
    private String updateBy;

    @CreateTime
    private Date insertTime;

    @CreateTime
    @UpdateTime
    private Date updateTime;

    public String getInsertBy() {
        return insertBy;
    }

    public void setInsertBy(String insertBy) {
        this.insertBy = insertBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


}
