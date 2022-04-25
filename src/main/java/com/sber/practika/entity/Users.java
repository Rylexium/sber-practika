package com.sber.practika.entity;

import com.sber.practika.models.Status;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
public class Users {
    @Id
    private String username;
    private String password;
    private String salt1;
    private String salt2;
    private String name;
    private String family;
    private String patronymic;
    private String email;
    private String phone;
    private Status enabled;
}