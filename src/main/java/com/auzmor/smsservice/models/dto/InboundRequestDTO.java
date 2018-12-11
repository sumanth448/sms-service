package com.auzmor.smsservice.models.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class InboundRequestDTO {

    @Size(min=6, max=16)
    @NotBlank
    private String from;

    @Size(min=6, max=16)
    @NotBlank
    private String to;

    @Size(min=1, max=120)
    @NotBlank
    private String text;
}
