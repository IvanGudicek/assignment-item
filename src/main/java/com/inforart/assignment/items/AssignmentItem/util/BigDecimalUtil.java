package com.inforart.assignment.items.AssignmentItem.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BigDecimalUtil {

  public static BigDecimal toBigDecimal(String string) {
    String replacedString = string.replace(',', '.');
    return roundBigDecimal(BigDecimal.valueOf(Double.parseDouble(replacedString)));
  }

  public static BigDecimal roundBigDecimal(BigDecimal bigDecimal) {
    return bigDecimal.setScale(4, RoundingMode.HALF_EVEN);
  }

}
