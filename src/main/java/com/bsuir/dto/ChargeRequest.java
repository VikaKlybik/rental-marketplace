package com.bsuir.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargeRequest {
    private Integer amount;
    private String currency;
    private String stripeEmail;
    protected String stripeToken;
}