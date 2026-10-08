package com.automation.framework.pages;

import com.automation.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Common actions every page object reuses. Every action waits for the element first. */
public abstract class BasePage {

    protected final WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected void click(By locator) {
        WaitUtils.clickable(driver, locator).click();
    }

    protected void type(By locator, String text) {
        var element = WaitUtils.visible(driver, locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return WaitUtils.visible(driver, locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return WaitUtils.visible(driver, locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
