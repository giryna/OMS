package com.softserve.edu.oms.locators;


import org.openqa.selenium.By;

public enum AddProductPageLocators {

    CREATING_PRODUCT_LABEL_CSS(By.cssSelector("#edit h3")),
    PRODUCT_NAME_INPUT_ID(By.id("name")),
    PRODUCT_DESCRIPTION_TEXTAREA_ID(By.id("description")),
    PRODUCT_PRICE_INPUT_ID(By.id("price")),
    OK_BUTTON_CSS(By.cssSelector("[value='OK']")),
    CANCEL_BUTTON_CSS(By.cssSelector("[value='Cancel']")),
    ITEM_MANAGEMENT_TAB_CSS(By.cssSelector("#nav .cur a")),
    PRODUCT_NAME_ERROR_MESSAGE_ID(By.id("productName.errors")),
    PRODUCT_PRICE_ERROR_MESSAGE_ID(By.id("productPrice.errors"));

    public final By by;
    AddProductPageLocators(final By by){
        this.by = by;
    }
}