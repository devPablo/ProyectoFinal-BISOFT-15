package com.cenfotec.taskly.service;

import com.cenfotec.taskly.domain.User;
import com.cenfotec.taskly.repository.SigninRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SigninServiceImpl implements SigninService {
    @Autowired
    SigninRepository signinRepository;

    @Override
    public void registerUser(User user) {
        signinRepository.save(user);
    }
}
