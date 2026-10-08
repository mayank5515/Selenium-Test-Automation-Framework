package com.automation.framework.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ScreenshotUtils {

    private static final String DIR = "reports/screenshots";

    private ScreenshotUtils() { }

    /** Saves a PNG to reports/screenshots and returns its path. */
    public static Path capture(WebDriver driver, String testName) throws IOException {
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String safeName = testName.replaceAll("[^a-zA-Z0-9._-]", "_");
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        Path target = Paths.get(DIR, safeName + "_" + time + ".png");
        Files.createDirectories(target.getParent());
        Files.copy(source.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
        return target;
    }
}
