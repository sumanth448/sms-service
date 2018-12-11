package com.auzmor.smsservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "phone_number")
@Data
@EqualsAndHashCode(callSuper = false)
public class PhoneNumber extends BaseEntity{
    private String number;

    @JsonIgnore
    @ManyToOne(targetEntity = Account.class, optional = false, fetch = FetchType.LAZY)
    private Account account;
}
