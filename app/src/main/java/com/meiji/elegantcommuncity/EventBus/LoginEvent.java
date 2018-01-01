package com.meiji.elegantcommuncity.EventBus;

import com.meiji.elegantcommuncity.model.User;

/**
 * Created by Administrator on 2018/1/1.
 */

public class LoginEvent {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
