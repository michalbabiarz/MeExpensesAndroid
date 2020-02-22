package com.pyskacz.android.myexpenses.config;

import java.util.Objects;

import lombok.Getter;

public class Configuration {
    private static final String DEFAULT_WORKBOOK_FILE_PATH = "/storage/emulated/0/Download/Budget.xlsm";
    private static final String DEFAULT_DATE_FORMAT = "dd-MMM-yyyy";

    @Getter
    public String workbookFileLocation = DEFAULT_WORKBOOK_FILE_PATH;
    //public DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);

    private static Configuration instance;

    private Configuration() {
    }

    public static Configuration getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Configuration();
        }

        return instance;
    }

}
