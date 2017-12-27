package com.meiji.elegantcommuncity.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.meiji.elegantcommuncity.InitApp;


/**
 * Created by zhangzhengchao on 2017/12/26.
 */

public class UserInfoUtil {

    private UserInfoUtil(){}

    private static UserInfoUtil userInfoUtil;

    private SharedPreferences tokentils = PreferenceManager.getDefaultSharedPreferences(InitApp.AppContext);

    public static UserInfoUtil getInstance(){
        if(userInfoUtil ==null){
            return  new UserInfoUtil();
        }else{
            return userInfoUtil;
        }

    }

    public void saveToken(String token){
        SharedPreferences.Editor edit = tokentils.edit();
        edit.putString("token",token);
        edit.commit();
    }

    public String getToken(){
       return tokentils.getString("token", "");
    }

    public void saveUserName(String userName){
        SharedPreferences.Editor edit = tokentils.edit();
        edit.putString("userName",userName);
        edit.commit();

    }
    public String getUserName(){
        return tokentils.getString("userName", "");
    }

}
