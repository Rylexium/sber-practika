package com.sber.practika.repo;

import com.sber.practika.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findByPhone(BigInteger phone);
    Optional<Users> findByUsername(String username);
}
