package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.UserModel;

public interface UserService {
    //通过用户ID获取用户对象的方法
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;

  /*  telphone用户手机
    password用户加密后的密码*/
    UserModel validateLogin(String telphone,String encrptPassword) throws BusinessException;
}
