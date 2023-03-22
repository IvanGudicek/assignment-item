//package com.inforart.assignment.items.AssignmentItem.service;
//
//import com.inforart.assignment.items.AssignmentItem.config.ExportFileConfiguration;
//import com.inforart.assignment.items.AssignmentItem.mapper.FirstFileMapper;
//import com.inforart.assignment.items.AssignmentItem.model.CsvBean;
//import com.inforart.assignment.items.AssignmentItem.model.PriceItemDto;
//import com.inforart.assignment.items.AssignmentItem.model.StockItemDto;
//import com.inforart.assignment.items.AssignmentItem.outerservice.hnb.CurrencyExchangeCalculationAdapter;
//import com.inforart.assignment.items.AssignmentItem.util.BigDecimalUtil;
//import com.opencsv.CSVWriter;
//import com.opencsv.bean.StatefulBeanToCsv;
//import com.opencsv.bean.StatefulBeanToCsvBuilder;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.io.FileOutputStream;
//import java.io.OutputStreamWriter;
//import java.io.Writer;
//import java.math.BigDecimal;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class FileWriterService {
//
//    private final ExportFileConfiguration exportFileConfiguration;
//    private final FileReaderService fileReaderService;
//    private final CurrencyExchangeCalculationAdapter currencyExchangeCalculationAdapter;
//    private final FirstFileMapper firstFileMapper;
//
//    public void writeFirstFile() throws Exception {
//
//        List<CsvBean> firstFileDtoList = new ArrayList<>();
//        BigDecimal currencyExchangeRate = currencyExchangeCalculationAdapter.getCurrencyExchangeRate();
//
//        fileReaderService.getItems()
//                .forEach(item -> {
//
//                    List<StockItemDto> stockItemDtos = fileReaderService.getStocks()
//                            .stream()
//                            .filter(stock -> item.getCode().equals(stock.getItemCode()))
//                            .collect(Collectors.toList());
//
//                    PriceItemDto priceItemDto = null;
//                    Optional<PriceItemDto> priceItemDtoOptional = fileReaderService.getPrices().stream().findFirst();
//                    if (priceItemDtoOptional.isPresent()) {
//                        priceItemDto = priceItemDtoOptional.get();
//                        priceItemDto.setOtherPrice(BigDecimalUtil.roundBigDecimal(currencyExchangeRate.multiply(priceItemDto.getPriceEur())));
//                    }
//
//                    firstFileDtoList.add(firstFileMapper.generateFirstFileDto(item, stockItemDtos, priceItemDto));
//                });
//
//
//        writeToFile(firstFileDtoList, exportFileConfiguration.getFirstFileName());
//    }
//
//    private void writeToFile(List<CsvBean> csvBeanList, String path) throws Exception {
//
//        try (Writer writer = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8)) {
//
//            StatefulBeanToCsv<CsvBean> sbc = new StatefulBeanToCsvBuilder<CsvBean>(writer)
////                    .withSeparator('\t')
//                    .withSeparator(exportFileConfiguration.getSeparator())
//                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
////                    .withLineEnd(exportFileConfiguration.getEndLine())
//                    .build();
//
//            sbc.write(csvBeanList);
//        }
//    }
//
//}
