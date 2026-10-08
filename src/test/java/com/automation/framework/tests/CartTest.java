package com.automation.framework.tests;

import com.automation.framework.base.BaseTest;
import com.automation.framework.config.ConfigReader;
import com.automation.framework.pages.CartPage;
import com.automation.framework.pages.InventoryPage;
import com.automation.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    private static final String BACKPACK = "Sauce Labs Backpack";
    private static final String BIKE_LIGHT = "Sauce Labs Bike Light";

    private InventoryPage inventory;

    @BeforeMethod
    public void login() {
        inventory = new LoginPage(driver).open()
                .loginAs(ConfigReader.get("valid.username"), ConfigReader.get("valid.password"));
    }

    @Test(description = "Adding one product updates the cart badge to 1")
    public void addSingleItem() {
        inventory.addToCart(BACKPACK);
        Assert.assertEquals(inventory.getCartCount(), 1);
    }

    @Test(description = "Adding two products updates the cart badge to 2")
    public void addMultipleItems() {
        inventory.addToCart(BACKPACK).addToCart(BIKE_LIGHT);
        Assert.assertEquals(inventory.getCartCount(), 2);
    }

    @Test(description = "Removing a product empties the cart")
    public void removeItem() {
        inventory.addToCart(BACKPACK).removeFromCart(BACKPACK);
        Assert.assertEquals(inventory.getCartCount(), 0);
    }

    @Test(description = "Cart page lists the product that was added")
    public void cartPageShowsAddedItem() {
        CartPage cart = inventory.addToCart(BACKPACK).openCart();
        Assert.assertTrue(cart.getItemNames().contains(BACKPACK));
    }
}
