package com.api.stripe.integration.stripe_integration.controllers;


import com.api.stripe.integration.stripe_integration.dto.*;
import com.api.stripe.integration.stripe_integration.service.StripeService;
import com.stripe.model.Subscription;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("api/stripe")
@AllArgsConstructor
public class StripeController {

    private final StripeService stripeService;

    @PostMapping("/card/token")
    @ResponseBody
    public StripeTokenDto createCardToken(@RequestBody StripeTokenDto model) {
        return stripeService.createCardToken(model);
    }

    @PostMapping("/charge")
    @ResponseBody
    public StripeChargeDto charge(@RequestBody StripeChargeDto model) {
        return stripeService.charge(model);
    }

    @PostMapping("/customer/subscription")
    @ResponseBody
    public StripeSubscriptionResponse subscription(@RequestBody StripeSubscriptionDto subscriptionDto) {
        return stripeService.createSubscription(subscriptionDto);
    }

    @DeleteMapping("/subscription/{id}")
    @ResponseBody
    public SubscriptionCancelRecord cancelSubscription(@PathVariable(value = "id") String id) {
        Subscription subscription = stripeService.cancelSubscription(id);
        if (nonNull(subscription))
            return new SubscriptionCancelRecord(subscription.getStatus());

        return null;
    }
}
