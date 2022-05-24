package com.sber.practika.repo;

import com.sber.practika.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findByPhone(Long phone);
    Optional<Users> findByUsername(String username);

    @Query(value =
            "select * " +
            "from users " +
            "where bank_number = (select bank_number from bank_card where id=:cardNumber)", nativeQuery = true)
    Optional<Users> findByCardNumber(@Param("cardNumber") Long cardNumber);
}
