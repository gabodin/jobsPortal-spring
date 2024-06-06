package com.gb.jobPortal.services;

import com.gb.jobPortal.entity.UsersType;
import com.gb.jobPortal.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;

    @Autowired
    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    public List<UsersType> findAll() {
        return usersTypeRepository.findAll();
    }
}
