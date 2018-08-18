package com.Alice.sso.service;

import com.Alice.common.pojo.TaotaoResult;
import com.Alice.pojo.TbUser;

/**
 * @author Alice
 * @date 2018/8/18/018
 */
public interface UserService {

    TaotaoResult checkData(String data,int type);

    TaotaoResult register(TbUser user);

    TaotaoResult login(String username, String password);

    TaotaoResult getUserByToken(String token);
}
