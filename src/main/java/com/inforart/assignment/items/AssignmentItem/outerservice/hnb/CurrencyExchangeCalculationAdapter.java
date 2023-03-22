package com.inforart.assignment.items.AssignmentItem.outerservice.hnb;

import com.inforart.assignment.items.AssignmentItem.config.HnbProperties;
import com.inforart.assignment.items.AssignmentItem.util.BigDecimalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CurrencyExchangeCalculationAdapter {

    private final HnbProperties hnbProperties;
    private final RestTemplate restTemplate;

    public BigDecimal getCurrencyExchangeRate() {
        return BigDecimalUtil.toBigDecimal(getCurrencyExchangeRateFromHnb().getProdajni_tecaj());
    }

    private CurrencyExchangeDto getCurrencyExchangeRateFromHnb() {
        return restTemplate.exchange(
                hnbProperties.getServerAddress() + hnbProperties.getCurrencyValue(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CurrencyExchangeDto>>() {
                }
        ).getBody().get(0);
    }

}
