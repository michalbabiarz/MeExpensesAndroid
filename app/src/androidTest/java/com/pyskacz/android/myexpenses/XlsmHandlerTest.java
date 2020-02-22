package com.pyskacz.android.myexpenses;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.apache.poi.ss.usermodel.Sheet;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class XlsmHandlerTest {

    static {
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLInputFactory",
                "com.fasterxml.aalto.stax.InputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLOutputFactory",
                "com.fasterxml.aalto.stax.OutputFactoryImpl"
        );
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLEventFactory",
                "com.fasterxml.aalto.stax.EventFactoryImpl"
        );
    }

    private static XlsmHandler xlsmHandler;

    public XlsmHandlerTest() throws IOException {
        xlsmHandler = XlsmHandler.getInstance();
    }

    @BeforeClass
    public static void initialize() {

    }

    @Test
    public void getInstance() {
        try {
            XlsmHandler ref1 = XlsmHandler.getInstance();
            XlsmHandler ref2 = XlsmHandler.getInstance();

            assertThat(ref1, sameInstance(ref2));

        } catch (IOException e) {
            assertThat("IOException occurred:" + Boolean.FALSE, is(Boolean.TRUE));
        }
    }

    @Test
    public void isLoading() {
        boolean loadingDetected = false;
        while (xlsmHandler.isLoading()) ;
        try {
            xlsmHandler.reload();
            assertThat(xlsmHandler.isLoading(), is(true));
            while (xlsmHandler.isLoading()) {
                loadingDetected = true;
            }
            assertThat(xlsmHandler.isLoading(), is(false));
            assertThat(loadingDetected, is(true));
        } catch (IOException e) {
            assertThat("IOException occurred:" + Boolean.FALSE, is(Boolean.TRUE));
        }
    }

    @Test
    public void getSheet() {
        String sheetName = "02.2020";
        try {
            xlsmHandler.reload();
            Sheet sheet = xlsmHandler.getSheet(sheetName);
            assertThat(sheet.getSheetName(), is(sheetName));
        } catch (IOException e) {
            assertThat("IOException occurred:" + Boolean.FALSE, is(Boolean.TRUE));
        }
    }

    @Test
    public void onWorkbookStateChangeListener() {
        boolean[] callbaclsCalled = new boolean[2];
        try {
            xlsmHandler.addOnWorkbookStateChangeListener(new XlsmHandler.OnWorkbookStateChangeListener() {
                @Override
                public void onWorkbookReloading() {
                    callbaclsCalled[0] = true;
                }

                @Override
                public void onWorkbookReloaded() {
                    callbaclsCalled[1] = true;
                }
            });

            xlsmHandler.reload();
            while(xlsmHandler.isLoading());
            assertArrayEquals(callbaclsCalled, new boolean[]{true, true});
        } catch (IOException e) {
            assertThat("IOException occurred:" + Boolean.FALSE, is(Boolean.TRUE));
        }

    }
}
