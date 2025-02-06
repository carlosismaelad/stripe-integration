package com.api.stripe.integration.stripe_integration.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class StripeChargeDto {
    private String stripeToken;
    private String Username;
    private Double amount;
    private Boolean success;
    private String message;
    private String chargeId;
    private Map<String, Object> additionalInfo = new HashMap<>();
}
