package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.PromoDOMapper;
import com.miaoshaproject.dataobject.PromoDO;
import com.miaoshaproject.service.PromoService;
import com.miaoshaproject.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoDOMapper promoDOMapper;

    @Override
    public PromoModel getPromoModelByItemId(Integer itemId) {

        //获取商品的秒杀信息
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        //dataobject->model
        PromoModel promoModel = convertProModelFromPromoDO(promoDO);
        if(promoModel==null){
            return null;
        }
        //判断当前时间是否秒杀活动即将开始或者进行
        if(promoModel.getStartDate().isAfterNow()){
            //秒杀活动未开始
            promoModel.setStatus(1);
        }else if(promoModel.getEndDate().isBeforeNow()){
            //秒杀活动结束了
            promoModel.setStatus(3);
        }else{
            //秒杀活动正进行中
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    private PromoModel convertProModelFromPromoDO(PromoDO promoDO){
        if(promoDO==null){
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return  promoModel;


    }
}
