package com.example.pricenotifierui.entity.restclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TaskUpdateDTO {
    @NotNull
    private long id;
    @NotBlank
    private String taskStatus;
}
