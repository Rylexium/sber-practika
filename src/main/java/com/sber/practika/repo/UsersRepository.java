package com.sber.practika.repo;

import com.sber.practika.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {
    Users findUsersByUsername(String username);
}
