package com.ccy.demo.service;

import com.ccy.demo.dao.User;

public interface UserService {

    User findByLoginName(String name);
}
