package com.ccy.demo.service.imp;

import com.ccy.demo.dao.User;
import com.ccy.demo.responsity.UserResponsity;
import com.ccy.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp  implements UserService {

    @Autowired
    UserResponsity userResponsity;

    @Override
    public User findByLoginName(String name) {
        return userResponsity.findByUserName(name);
    }
}
