package com.automation.framework.tests;

import com.automation.framework.base.BaseTest;
import com.automation.framework.config.ConfigReader;
import com.automation.framework.pages.InventoryPage;
import com.automation.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Valid user can log in and sees the products page")
    public void validLoginShowsProducts() {
        InventoryPage inventory = new LoginPage(driver).open()
                .loginAs(ConfigReader.get("valid.username"), ConfigReader.get("valid.password"));

        Assert.assertEquals(inventory.getTitle(), "Products");
    }

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLogins() {
        return new Object[][]{
                // username,            password,        expected part of error message
                {"locked_out_user",     "secret_sauce",  "locked out"},
                {"standard_user",       "wrong_password", "do not match"},
                {"",                    "",              "Username is required"},
                {"standard_user",       "",              "Password is required"}
        };
    }

    @Test(dataProvider = "invalidLogins", description = "Invalid logins show the right error")
    public void invalidLoginShowsError(String username, String password, String expectedError) {
        LoginPage login = new LoginPage(driver).open().loginExpectingFailure(username, password);

        Assert.assertTrue(login.getErrorMessage().contains(expectedError),
                "Unexpected error message: " + login.getErrorMessage());
    }
}
