package com.inforart.assignment.items.AssignmentItem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "import.file.config")
@Getter
@Setter
public class ImportFileConfiguration {

    private String itemsFileName;
    private String pricesFileName;
    private String storesFileName;
    private String stocksFileName;
    private char separator;
    private String encoding;

}