package com.pyskacz.android.myexpenses.model;

import lombok.Getter;

public class Configuration {
    private static final String DEFAULT_WORKBOOK_FILE_PATH = "/mnt/sdcard/Download/Budget.xlsm";
    private static final String DEFAULT_DATE_FORMAT = "dd-MMM-yyyy";

    @Getter
    public String workbookFileLocation = DEFAULT_WORKBOOK_FILE_PATH;
    //public DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);

    public Configuration(){}
}
