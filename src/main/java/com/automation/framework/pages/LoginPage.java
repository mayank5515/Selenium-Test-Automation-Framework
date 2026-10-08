package com.automation.framework.pages;

import com.automation.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(ConfigReader.get("base.url"));
        return this;
    }

    /** Successful login lands on the products page. */
    public InventoryPage loginAs(String username, String password) {
        enterCredentialsAndSubmit(username, password);
        return new InventoryPage(driver);
    }

    /** Used by negative tests that stay on the login page. */
    public LoginPage loginExpectingFailure(String username, String password) {
        enterCredentialsAndSubmit(username, password);
        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    private void enterCredentialsAndSubmit(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }
}
