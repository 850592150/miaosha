package com.miaoshaproject.service.model;

import java.math.BigDecimal;

//用户下单的交易模型
public class OrderModel {
    //如果promoId非空则是以秒杀的价格来购买该商品
    private Integer promoId;


    //商品下单对应的ID
    private String id;
    //购买的用户id
    private Integer userId;
    //购买的商品id
    private Integer itemId;

    //购买商品的单价，如果promoId非空则是以秒杀的价格来购买该商品
    private BigDecimal itemPrice;


    //购买金额，如果promoId非空则是以秒杀的价格来购买该商品
    private BigDecimal orderPrice;

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    //购买数量
    private Integer amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}
