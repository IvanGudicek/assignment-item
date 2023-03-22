package com.inforart.assignment.items.AssignmentItem.mapper;

import com.inforart.assignment.items.AssignmentItem.model.FirstFileDto;
import com.inforart.assignment.items.AssignmentItem.model.ItemDto;
import com.inforart.assignment.items.AssignmentItem.model.PriceItemDto;
import com.inforart.assignment.items.AssignmentItem.model.StockItemDto;
import com.inforart.assignment.items.AssignmentItem.util.BigDecimalUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class FirstFileMapper {

    public FirstFileDto generateFirstFileDto(ItemDto itemDto, List<StockItemDto> stockItemDtos, PriceItemDto priceItemDto) {

        if (itemDto == null && stockItemDtos == null && priceItemDto == null) {
            return null;
        }

        FirstFileDto.FirstFileDtoBuilder firstFileDto = FirstFileDto.builder();

        if (itemDto != null) {
            firstFileDto.itemCode(itemDto.getCode());
            firstFileDto.itemName(itemDto.getName());
            firstFileDto.itemMeasurementUnit(itemDto.getMeasurementUnit());
        }
        if (priceItemDto != null) {
            firstFileDto.priceItemEur(priceItemDto.getPriceEur());
        }

        if (stockItemDtos != null) {
            firstFileDto.itemTotalAmountEur(BigDecimalUtil.roundBigDecimal(stockItemDtos
                    .stream()
                    .map(StockItemDto::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .multiply(priceItemDto.getPriceEur()))
            );

            firstFileDto.itemTotalAmount(BigDecimalUtil.roundBigDecimal(stockItemDtos
                    .stream()
                    .map(StockItemDto::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .multiply(priceItemDto.getOtherPrice()))
            );

            firstFileDto.itemAmount(BigDecimalUtil.roundBigDecimal(stockItemDtos
                    .stream()
                    .filter(stockItemDto -> !stockItemDto.getAmount().equals(BigDecimal.ZERO))
                    .map(StockItemDto::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)));

            firstFileDto.storeAmountByItem(stockItemDtos
                    .stream()
                    .filter(stockItemDto -> !stockItemDto.getAmount().equals(BigDecimal.ZERO))
                    .count());
        }

        return firstFileDto.build();
    }

}
