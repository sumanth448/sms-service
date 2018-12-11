package com.auzmor.smsservice.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "account")
@Data
@EqualsAndHashCode(callSuper = false)
public class Account extends BaseEntity{
    private String userName;
    private String authId;
}
