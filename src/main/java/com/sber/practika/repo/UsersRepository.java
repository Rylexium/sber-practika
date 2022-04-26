package com.sber.practika.repo;

import com.sber.practika.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface UsersRepository extends JpaRepository<Users, BigInteger> {
}
