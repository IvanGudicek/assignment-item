package com.inforart.assignment.items.AssignmentItem.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockItemDto {

    private String itemCode;
    private String storeCode;
    private BigDecimal amount;

}
