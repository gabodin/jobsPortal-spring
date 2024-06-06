package com.gb.jobPortal.repository;

import com.gb.jobPortal.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
}
