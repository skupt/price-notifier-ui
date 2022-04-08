package com.example.pricenotifierui.entity.restclient;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SearchTaskDTO {
    private String username;
    @NotBlank
    private String domain;
    @NotBlank
    private String keywords;
    private String domainUser;
    private String domainPassword;

}
