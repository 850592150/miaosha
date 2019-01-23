package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dao.UserPasswordDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import com.miaoshaproject.dataobject.UserPasswordDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import com.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

   @Autowired
   private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        //调用userDomapper获取对应的用户dataobject
      UserDO userDO =  userDOMapper.selectByPrimaryKey(id);
      if(userDO == null){
          return null;
      }
      //通过用户ID获取用户的加密密码信息
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

      return convertFromDataObject(userDO,userPasswordDO);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if(userModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
       /* if(StringUtils.isEmpty(userModel.getName())||userModel.getGender()==null
                ||userModel.getAge()==null||StringUtils.isEmpty(userModel.getTelphone())){

            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);


        }*/
      ValidationResult result = validator.validate(userModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        //实现model转成dataobject的方法
        UserDO userDO = convertFromUserModel(userModel);

        try {
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException ex){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已经注册");
        }

        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);

        return;

    }
    //用户登陆
    @Override
    public UserModel  validateLogin(String telphone, String encrptPassword) throws BusinessException {
        //通过用户手机获取用户信息
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if(userDO==null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FIAL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO,userPasswordDO);
        //比较用户信息的加密密码是否与输入的密码相匹配
        if(!StringUtils.equals(encrptPassword,userModel.getEncrptPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FIAL);

        }
        return userModel;
    }


    private UserPasswordDO convertPasswordFromModel(UserModel userModel){
        if(userModel==null){
            return null;

        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
       userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
       userPasswordDO.setUserId(userModel.getId());
       return  userPasswordDO;

    }
    private UserDO convertFromUserModel(UserModel userModel){
        if(userModel==null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;

    }


    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if(userDO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        //将userDo的属性copy到userModel中
        BeanUtils.copyProperties(userDO,userModel);
        if(userPasswordDO != null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }

        return userModel;


    }
}
