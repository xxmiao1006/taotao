package com.Alice.sso.service.impl;

import com.Alice.common.pojo.TaotaoResult;
import com.Alice.common.utils.JsonUtils;
import com.Alice.mapper.TbUserMapper;
import com.Alice.pojo.TbUser;
import com.Alice.pojo.TbUserExample;
import com.Alice.sso.jedis.JedisClient;
import com.Alice.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Alice
 * @date 2018/8/18/018
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${USER_SESSION}")
    private String USER_SESSION;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;



    /**
     * 校验用户数据是否合格
     * @param data
     * @param type
     * @return
     */
    public TaotaoResult checkData(String data, int type) {

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        if(type == 1){
            //判断用户名是否可用
            criteria.andUsernameEqualTo(data);
        }else if(type == 2){
            //判断手机号是否可用
            criteria.andPhoneEqualTo(data);
        }else if(type == 3){
            //判断邮箱是否可用
            criteria.andEmailEqualTo(data);
        }else{
            return TaotaoResult.build(400,"非法数据");
        }
        List<TbUser> users = userMapper.selectByExample(example);
        if(users!=null&&users.size()>0){
            //有值，不可用
            return TaotaoResult.ok(false);
        }

        return TaotaoResult.ok(true);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    public TaotaoResult register(TbUser user) {
        //检查数据有效性
        if(StringUtils.isBlank(user.getUsername())){
            return TaotaoResult.build(400,"用户名不能为空");
        }
        //判断用户名是否重复
        TaotaoResult taotaoResult = checkData(user.getUsername(), 1);
        if(!(boolean)taotaoResult.getData()){
            return TaotaoResult.build(400,"用户名重复");
        }

        //判断密码是否为空
        if(StringUtils.isBlank(user.getPassword())){
            return TaotaoResult.build(400,"密码不能为空");
        }

        //校验电话号码
        if (StringUtils.isNotBlank(user.getPhone())) {
            //是否重复校验
            taotaoResult = checkData(user.getPhone(), 2);
            if (!(boolean) taotaoResult.getData()) {
                return TaotaoResult.build(400, "电话号码重复");
            }
        }

        //如果email不为空的话进行是否重复校验
        if (StringUtils.isNotBlank(user.getEmail())) {
            //是否重复校验
            taotaoResult = checkData(user.getEmail(), 3);
            if (!(boolean) taotaoResult.getData()) {
                return TaotaoResult.build(400, "email重复");
            }
        }

        //补全属性
        user.setCreated(new Date());
        user.setUpdated(new Date());

        //密码要MD5加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        userMapper.insert(user);

        return TaotaoResult.ok();
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public TaotaoResult login(String username, String password) {
        //判断用户名和密码是否正确(密码要进行加密在检验)
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        if(list==null && list.size()==0){
            //返回登录失败
            return TaotaoResult.build(400,"用户名或密码不正确");
        }
        TbUser user = list.get(0);
        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
            //返回登录失败
            return TaotaoResult.build(400,"用户名或密码不正确");
        }
        //生成token,使用uuid
        String token = UUID.randomUUID().toString();
        //清空密码
        user.setPassword(null);
        //把用户信息保存到redis,key就是token,value就是用户信息
        jedisClient.set(USER_SESSION+":"+token,JsonUtils.objectToJson(user));
        //设置key的过期时间
        jedisClient.expire(USER_SESSION+":"+token,SESSION_EXPIRE);
        //返回登录成功,把token返回
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(USER_SESSION + ":" + token);
        if(StringUtils.isBlank(json)){
            return TaotaoResult.build(400,"用户登录已过期");
        }
        //重置session的过期时间
        jedisClient.expire(USER_SESSION+":"+token,SESSION_EXPIRE);
        //把json转换成user对象
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        return TaotaoResult.ok(user);
    }
}
