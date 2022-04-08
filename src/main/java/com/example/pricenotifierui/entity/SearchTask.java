package com.example.pricenotifierui.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class SearchTask extends Task {
    @NotBlank
    private String domain;
    @NotBlank
    private String keywords;
    private String domainUser;
    private String domainPassword;

}
