package com.miaoshaproject.service.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class PromoModel {

    //秒杀活动状态 1：未开始 2：在进行 3:结束
    private Integer status;

    private Integer id;
    //秒杀活动名称
    private String PromoName;

    //秒杀活动的开始时间
    private DateTime startDate;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    //秒杀活动结束时间
    private  DateTime endDate;

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    //秒杀活动适用的商品Id
    private Integer itemId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return PromoName;
    }

    public void setPromoName(String promoName) {
        PromoName = promoName;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    //秒杀活动商品的价格
    private BigDecimal promoItemPrice;

}
