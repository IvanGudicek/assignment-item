package com.inforart.assignment.items.AssignmentItem.service;

import com.inforart.assignment.items.AssignmentItem.config.ExportFileConfiguration;
import com.inforart.assignment.items.AssignmentItem.config.ImportFileConfiguration;
import com.inforart.assignment.items.AssignmentItem.mapper.FirstFileMapper;
import com.inforart.assignment.items.AssignmentItem.model.*;
import com.inforart.assignment.items.AssignmentItem.outerservice.hnb.CurrencyExchangeCalculationAdapter;
import com.inforart.assignment.items.AssignmentItem.util.BigDecimalUtil;
import com.opencsv.*;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Getter
public class FileReaderService implements ApplicationContextAware {

    private final ImportFileConfiguration importFileConfiguration;
    private List<ItemDto> items = new ArrayList<>();
    private List<PriceItemDto> prices = new ArrayList<>();
    private List<StockItemDto> stocks = new ArrayList<>();
    private List<StoreItemDto> stores = new ArrayList<>();
    private final ExportFileConfiguration exportFileConfiguration;
    private final CurrencyExchangeCalculationAdapter currencyExchangeCalculationAdapter;
    private final FirstFileMapper firstFileMapper;

    /**
     * READING
     */

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {


        CSVParser parser = new CSVParserBuilder().withSeparator(importFileConfiguration.getSeparator()).build();

        /**
         * read items
         */
        try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(
                getFileFromResourceAsStream(importFileConfiguration.getItemsFileName()), importFileConfiguration.getEncoding())).withCSVParser(parser).build()) {

            List<String[]> readData = csvReader.readAll();
            for (String[] row : readData) {
                this.items.add(buildItemDto(row));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * read prices
         */
        try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(
                getFileFromResourceAsStream(importFileConfiguration.getPricesFileName()), importFileConfiguration.getEncoding())).withCSVParser(parser).build()) {

            List<String[]> readData = csvReader.readAll();
            for (String[] row : readData) {
                this.prices.add(buildPriceItemDto(row));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * read stocks
         */
        try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(
                getFileFromResourceAsStream(importFileConfiguration.getStocksFileName()), importFileConfiguration.getEncoding())).withCSVParser(parser).build()) {

            List<String[]> readData = csvReader.readAll();
            for (String[] row : readData) {
                this.stocks.add(buildStockItemDto(row));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * read stores
         */
        try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(
                getFileFromResourceAsStream(importFileConfiguration.getStoresFileName()), importFileConfiguration.getEncoding())).withCSVParser(parser).build()) {

            List<String[]> readData = csvReader.readAll();
            for (String[] row : readData) {
                this.stores.add(buildStoreItemDto(row));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * writing in first file
         */
        try {
            writeFirstFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ItemDto buildItemDto(String[] row) {
        return ItemDto.builder()
                .code(row[0])
                .name(row[1])
                .measurementUnit(row[2])
                .build();
    }

    private PriceItemDto buildPriceItemDto(String[] row) {
        return PriceItemDto.builder()
                .code(row[0])
                .priceEur(BigDecimalUtil.toBigDecimal(row[1]))
                .build();
    }

    private StockItemDto buildStockItemDto(String[] row) {
        return StockItemDto.builder()
                .itemCode(row[0])
                .storeCode(row[1])
                .amount(BigDecimalUtil.toBigDecimal(row[2]))
                .build();
    }

    private StoreItemDto buildStoreItemDto(String[] row) {
        return StoreItemDto.builder()
                .code(row[0])
                .name(row[1])
                .build();
    }

    private InputStream getFileFromResourceAsStream(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file " + fileName + " not found!");
        } else {
            return inputStream;
        }
    }

    /**
     * WRITING
     */

    public void writeFirstFile() throws Exception {

        List<FirstFileDto> firstFileDtoList = new ArrayList<>();
        BigDecimal currencyExchangeRate = currencyExchangeCalculationAdapter.getCurrencyExchangeRate();

        this.items
                .forEach(item -> {

                    List<StockItemDto> stockItemDtos = this.stocks
                            .stream()
                            .filter(stock -> item.getCode().equals(stock.getItemCode()))
                            .collect(Collectors.toList());

                    PriceItemDto priceItemDto = null;
                    Optional<PriceItemDto> priceItemDtoOptional = this.prices.stream().findFirst();
                    if (priceItemDtoOptional.isPresent()) {
                        priceItemDto = priceItemDtoOptional.get();
                        priceItemDto.setOtherPrice(BigDecimalUtil.roundBigDecimal(currencyExchangeRate.multiply(priceItemDto.getPriceEur())));
                    }

                    firstFileDtoList.add(firstFileMapper.generateFirstFileDto(item, stockItemDtos, priceItemDto));
                });

        writeToFile(firstFileDtoList.stream().sorted(Comparator.comparing(FirstFileDto::getItemCode)).collect(Collectors.toList()), exportFileConfiguration.getFirstFileName());
    }

    private void writeToFile(List<FirstFileDto> csvBeanList, String path) throws Exception {

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8)) {

            StatefulBeanToCsv<FirstFileDto> sbc = new StatefulBeanToCsvBuilder<FirstFileDto>(writer)
//                    .withSeparator('\t')
                    .withSeparator(exportFileConfiguration.getSeparator())
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
//                    .withLineEnd(exportFileConfiguration.getEndLine())
                    .build();

            sbc.write(csvBeanList);
        }
    }

}
