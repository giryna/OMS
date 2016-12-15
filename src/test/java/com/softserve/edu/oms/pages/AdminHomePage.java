package com.softserve.edu.oms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.softserve.edu.oms.locators.UserHomePageLocators.*;

public class AdminHomePage extends HomePage {

	public AdminHomePage(WebDriver driver) {
		super(driver);
	}

	// PageObject

	// get Data

	public WebElement getAdministrationTab() {
		return driver.findElement(ADMINISTRATION_TAB_CSS.by);
	}

	public WebElement getOrderingTab(){
		return driver.findElement(ORDERING_TAB_XPATH.by);
	}


	// Functional
	
	public String getAdministrationTabText() {
		return getAdministrationTab().getText();
	}

	public String getOrderingTabText() {
		return getOrderingTab().getText();
	}

	// set Data

	public AdministrationPage clickAdministrationTab() {
		getAdministrationTab().click();
		return new AdministrationPage(driver);
	}

	public void clickOrderingTab() {
		getOrderingTab().click();
	}
	
    // Business Logic

    public AdministrationPage gotoAdministrationPage() {
    	clickAdministrationTab();
		return new AdministrationPage(driver);
	}

	public OrderingPage gotoOrderingPage(){
		clickOrderingTab();
		return new OrderingPage();
	}

}
