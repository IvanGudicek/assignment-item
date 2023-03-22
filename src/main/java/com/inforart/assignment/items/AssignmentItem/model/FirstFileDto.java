package com.inforart.assignment.items.AssignmentItem.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FirstFileDto extends CsvBean {

    private String itemCode;
    private String itemName;
    private BigDecimal priceItemEur;
    private BigDecimal itemAmount;
    private String itemMeasurementUnit;
    private BigDecimal itemTotalAmountEur;
    private BigDecimal itemTotalAmount;
    private long storeAmountByItem;

}
