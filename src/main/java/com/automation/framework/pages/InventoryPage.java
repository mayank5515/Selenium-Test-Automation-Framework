package com.automation.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage extends BasePage {

    private final By pageTitle = By.cssSelector(".title");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLink = By.cssSelector(".shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getText(pageTitle);
    }

    public InventoryPage addToCart(String productName) {
        click(By.id("add-to-cart-" + toSlug(productName)));
        return this;
    }

    public InventoryPage removeFromCart(String productName) {
        click(By.id("remove-" + toSlug(productName)));
        return this;
    }

    /** Badge is absent when the cart is empty, so 0 means "no badge". */
    public int getCartCount() {
        var badges = driver.findElements(cartBadge);
        return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).getText());
    }

    public CartPage openCart() {
        click(cartLink);
        return new CartPage(driver);
    }

    // "Sauce Labs Backpack" -> "sauce-labs-backpack" (how the site builds its element ids)
    private String toSlug(String productName) {
        return productName.toLowerCase().replace(" ", "-");
    }
}
