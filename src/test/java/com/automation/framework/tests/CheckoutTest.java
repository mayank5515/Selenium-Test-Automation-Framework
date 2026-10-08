package com.automation.framework.tests;

import com.automation.framework.base.BaseTest;
import com.automation.framework.config.ConfigReader;
import com.automation.framework.pages.CheckoutPage;
import com.automation.framework.pages.InventoryPage;
import com.automation.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private InventoryPage inventory;

    @BeforeMethod
    public void login() {
        inventory = new LoginPage(driver).open()
                .loginAs(ConfigReader.get("valid.username"), ConfigReader.get("valid.password"));
    }

    @Test(description = "Full flow: add item, checkout, see order confirmation")
    public void completeEndToEndPurchase() {
        CheckoutPage checkout = inventory.addToCart("Sauce Labs Backpack")
                .openCart()
                .proceedToCheckout()
                .fillDetailsAndContinue("Mayank", "Tester", "110001")
                .finishOrder();

        Assert.assertEquals(checkout.getConfirmationMessage(), "Thank you for your order!");
    }

    @DataProvider(name = "incompleteCheckoutDetails")
    public Object[][] incompleteCheckoutDetails() {
        return new Object[][]{
                // first,  last,    zip,      expected error
                {"",       "Doe",   "12345",  "First Name is required"},
                {"John",   "",      "12345",  "Last Name is required"},
                {"John",   "Doe",   "",       "Postal Code is required"}
        };
    }

    @Test(dataProvider = "incompleteCheckoutDetails", description = "Checkout form validates required fields")
    public void checkoutRequiresAllDetails(String first, String last, String zip, String expectedError) {
        CheckoutPage checkout = inventory.addToCart("Sauce Labs Backpack")
                .openCart()
                .proceedToCheckout()
                .fillDetailsAndContinue(first, last, zip);

        Assert.assertTrue(checkout.getErrorMessage().contains(expectedError),
                "Unexpected error message: " + checkout.getErrorMessage());
    }
}
