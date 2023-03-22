package com.inforart.assignment.items.AssignmentItem.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceItemDto {

    private String code;
    private BigDecimal priceEur;
    private BigDecimal otherPrice;

}
