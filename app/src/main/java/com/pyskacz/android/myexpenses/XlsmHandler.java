package com.pyskacz.android.myexpenses;

import com.pyskacz.android.myexpenses.config.Configuration;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class XlsmHandler {

    private static XlsmHandler instance;

    private final Configuration configuration;
    private Workbook workbook;
    private ArrayList<OnWorkbookStateChangeListener> listeners = new ArrayList<>();

    private volatile boolean isLoading = false;

    private XlsmHandler() throws IOException {
        configuration = Configuration.getInstance();
    }

    public static XlsmHandler getInstance() throws IOException {
        if (Objects.isNull(instance)) {
            instance = new XlsmHandler();
        }

        return instance;
    }

    public void reload() throws IOException {
        waitTillLoaded();
        isLoading = true;
        listeners.forEach(OnWorkbookStateChangeListener::onWorkbookReloading);
        new Thread(() -> {
            try (InputStream in = new BufferedInputStream(new FileInputStream(configuration.workbookFileLocation), 1024)) {
                workbook = new XSSFWorkbook(in);
            } catch (IOException e) {
                //TODO: fix that
                e.printStackTrace();
            }
            isLoading = false;
            listeners.forEach(OnWorkbookStateChangeListener::onWorkbookReloaded);
        }).start();
    }

    public void write() throws IOException {
        waitTillLoaded();
        try (FileOutputStream out = new FileOutputStream(configuration.workbookFileLocation)) {
            workbook.write(out);
        }
    }

    public void addOnWorkbookStateChangeListener(OnWorkbookStateChangeListener listener) {
        listeners.add(listener);
    }

    public Sheet getSheet(String sheetName) {
        waitTillLoaded();
        return workbook.getSheet(sheetName);
    }

    public boolean isLoading() {
        return isLoading;
    }

    private void waitTillLoaded() {
        while (isLoading) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //TODO: fix that
                e.printStackTrace();
            }
        }
    }

    public interface OnWorkbookStateChangeListener {
        void onWorkbookReloading();

        void onWorkbookReloaded();
    }
}
