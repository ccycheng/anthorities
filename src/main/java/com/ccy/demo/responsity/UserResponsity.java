package com.ccy.demo.responsity;

import com.ccy.demo.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResponsity extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

    User findByUserName(String name);


}
