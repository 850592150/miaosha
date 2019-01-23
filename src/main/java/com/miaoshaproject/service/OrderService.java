package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.OrderModel;

public interface OrderService {
    //通过前端url传入过来的秒杀活动id，然后下单接口内校验是否该id是否对应相应的商品且秒杀活动已经开始
    OrderModel createOrder(Integer userId,Integer itmeId, Integer promoId, Integer amount) throws BusinessException;
}
