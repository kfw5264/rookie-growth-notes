package com.masq.mybatisdemo.pojo;

public class Order extends BaseEntity {
    private Long orderId;
    private String orderNo;
    private Integer orderStatus;


    public Order() {
    }

    public Order(Long orderId, String orderNo, Integer orderStatus) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.orderStatus = orderStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }



}
