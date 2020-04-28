package com.cenfotec.taskly.service;

import com.cenfotec.taskly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    @Autowired
    UserRepository repo;
}
