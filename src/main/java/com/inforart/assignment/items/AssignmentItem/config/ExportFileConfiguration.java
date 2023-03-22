package com.inforart.assignment.items.AssignmentItem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "export.file.config")
@Getter
@Setter
public class ExportFileConfiguration {

    private String firstFileName;
    private String secondFileName;
    private char separator;
    private String endLine;
}