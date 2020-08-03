package com.wruins.service;

import com.wruins.po.User;

public interface UserService {

    User checkUser(String username, String password);
}
