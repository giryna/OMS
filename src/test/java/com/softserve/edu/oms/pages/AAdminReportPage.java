package com.softserve.edu.oms.pages;

import com.softserve.edu.oms.data.DBUtils;
import com.softserve.edu.oms.data.User;
import com.softserve.edu.oms.enums.ConditionFilterDropdownList;
import com.softserve.edu.oms.enums.FieldFilterDropdownList;
import com.softserve.edu.oms.enums.UsersPerPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.softserve.edu.oms.locators.AAdminReportPageLocators.*;
/**
 * This abstract class represents common entities and functionality for
 * Administration Page and Create Report Page
 */
public abstract class AAdminReportPage extends ABasePage {

	public AAdminReportPage(WebDriver driver) {
		super(driver);
	}

	// get Data

	public WebElement getSubHeader() {
		return driver.findElement(SUBHEADER_CSS.by);
	}

	public WebElement getUsersFoundSpan() {
		return driver.findElement(USERS_FOUND_SPAN_ID.by);
	}

	public WebElement getSearchByLabel() {
		return driver.findElement(SEARCH_BY_LABEL_TAG_NAME.by);
	}

	public WebElement getFieldFilterLabel() {
		return driver.findElement(FIELD_FILTER_LABEL_CSS.by);
	}

	public Select getSelectField() {
		return new Select(driver.findElement(SELECT_FIELD_ID.by));
	}

	public Select getSelectCondition() {
		return new Select(driver.findElement(SELECT_CONDITION_ID.by));
	}

	public WebElement getSearchFieldInput() {
		return driver.findElement(SEARCH_FIELD_ID.by);
	}

	public WebElement getSearchButton() {
		return driver.findElement(SEARCH_BUTTON_NAME.by);
	}

	public WebElement getShowItemsLink() {
		return driver.findElement(SHOW_ITEMS_LINK_CSS.by);
	}

	public WebElement getFirstNameLink() {
		return driver.findElement(FIRST_NAME_LINK_CSS.by);
	}

	public WebElement getLastNameLink() {
		return driver.findElement(LAST_NAME_LINK_CSS.by);
	}

	public WebElement getLoginLink() {
		return driver.findElement(LOGIN_LINK_CSS.by);
	}

	public WebElement getRoleLink() {
		return driver.findElement(ROLE_LINK_CSS.by);
	}

	public WebElement getRegionLink() {
		return driver.findElement(REGION_LINK_CSS.by);
	}

	public WebElement getFirstButton() {
		return driver.findElement(FIRST_BUTTON_ID.by);
	}

	public WebElement getBackwardButton() {
		return driver.findElement(BACKWARD_BUTTON_ID.by);
	}

	public WebElement getForwardButton() {
		return driver.findElement(FORWARD_BUTTON_ID.by);
	}

	public WebElement getLastButton() {
		return driver.findElement(LAST_BUTTON_ID.by);
	}

	public WebElement getPageNumberSpan() {
		return driver.findElement(PAGE_NUMBER_SPAN_ID.by);
	}

	public WebElement getPageCountSpan() {
		return driver.findElement(PAGE_COUNT_SPAN_ID.by);
	}

	public WebElement getTableBody() {
		return driver.findElement(TABLE_BODY_TAGNAME.by);
	}

	public WebElement getUserByLogin() {
		return driver.findElement(GET_USER_BY_LOGIN_XPATH.by);
	}

	public List<WebElement> getLogins() {
		return driver.findElements(LOGINS_XPATH.by);
	}

	public List<WebElement> getFirstNames() {
		return driver.findElements(FIRST_NAMES_XPATH.by);
	}

	public List<WebElement> getLastNames() {
		return driver.findElements(LAST_NAMES_XPATH.by);
	}

	public List<WebElement> getRoles() {
		return driver.findElements(ROLES_XPATH.by);
	}

	public List<WebElement> getRegions() {
		return driver.findElements(REGION_XPATH.by);
	}

	// Functional

	public String getSubHeaderText() {
		return getSubHeader().getText();
	}

	public String getUsersFoundSpanText() {
		return getUsersFoundSpan().getText();
	}

	public String getSearchByLabelText() {
		return getSearchByLabel().getText();
	}

	public String getFieldFilterLabelText() {
		return getFieldFilterLabel().getText();
	}

	public String getSelectFieldDefaultValue() {
		return getSelectField().getFirstSelectedOption().getText();
	}

	public String getSelectConditionDefaultValue() {
		return getSelectCondition().getFirstSelectedOption().getText();
	}

	public String getSearchFieldInputValue() {
		return getSearchFieldInput().getAttribute("value");
	}

	public String getSearchButtonValue() {
		return getSearchButton().getAttribute("value");
	}

	public String getShowItemsLinkText() {
		return getShowItemsLink().getText();
	}

	public String getFirstNameLinkText() {
		return getFirstNameLink().getText();
	}

	public String getLastNameLinkText() {
		return getLastNameLink().getText();
	}

	public String getLoginLinkText() {
		return getLoginLink().getText();
	}

	public String getRoleLinkText() {
		return getRoleLink().getText();
	}

	public String getRegionLinkText() {
		return getRegionLink().getText();
	}

	public String getFirstButtonValue() {
		return getFirstButton().getAttribute("value");
	}

	public String getBackwardButtonValue() {
		return getBackwardButton().getAttribute("value");
	}

	public String getForwardButtonValue() {
		return getForwardButton().getAttribute("value");
	}

	public String getLastButtonValue() {
		return getLastButton().getAttribute("value");
	}

	public String getPageNumberSpanText() {
		return getPageNumberSpan().getText();
	}

	public String getPageCountSpanText() {
		return getPageCountSpan().getAttribute("innerHTML");
	}

	public String getUsersFoundText() {
		return getUsersFoundSpan().getAttribute("innerHTML");
	}

	public String getUserByLoginText() {
		return getUserByLogin().getText();
	}

	@Step("getFoundUsersNumber")
	public int getFoundUsersNumber() {
		return Integer.parseInt(getUsersFoundText());
	}

	@Step("getPagesQuantity")
	public int getPagesQuantity() {
		return Integer.parseInt(getPageCountSpanText());
	}

	@Step("getCurrentPageNumber")
	public int getCurrentPageNumber() {
		return Integer.parseInt(getPageNumberSpanText());
	}

	public int getUsersPerPageNumber() {
		String itemsLinkText = getShowItemsLink().getText();
		int numberLinkText = Integer
				.parseInt(itemsLinkText.substring(itemsLinkText.indexOf(" ") + 1, itemsLinkText.lastIndexOf(" ")));
		if (UsersPerPage.TEN.getResultsPerPage() == numberLinkText)
			return UsersPerPage.FIVE.getResultsPerPage();
		else
			return UsersPerPage.TEN.getResultsPerPage();
	}

	// Check if navigation buttons is enabled
	@Step("isForwardButtonEnabled")
	public boolean isForwardButtonEnabled() {
		return getForwardButton().isEnabled();
	}

	@Step("isLastButtonEnabled")
	public boolean isLastButtonEnabled() {
		return getLastButton().isEnabled();
	}

	@Step("isBackwardButtonEnabled")
	public boolean isBackwardButtonEnabled() {
		return getBackwardButton().isEnabled();
	}

	@Step("isFirstButtonEnabled")
	public boolean isFirstButtonEnabled() {
		return getFirstButton().isEnabled();
	}

	// set Data

	public void clickFirstNameLink() {
		getFirstNameLink().click();
	}

	public void clickLastNameLink() {
		getLastNameLink().click();
	}

	public void clickLoginLink() {
		getLoginLink().click();
	}

	public void clickRoleLink() {
		getRoleLink().click();
	}

	public void clickRegionLink() {
		getRegionLink().click();
	}

	@Step("clickFirstButton")
	public void clickFirstButton() {
		getFirstButton().click();
	}

	@Step("clickBackwardButton")
	public void clickBackwardButton() {
		getBackwardButton().click();
	}

	@Step("clickForwardButton")
	public void clickForwardButton() {
		getForwardButton().click();
	}

	@Step("clickLastButton")
	public void clickLastButton() {
		getLastButton().click();
	}

	public void clickShowItemsLink() {
		getShowItemsLink().click();
	}

	// Click "Search" button in "Search by" field
	public void clickSearchButton() {
		getSearchButton().click();
	}

	public void selectField(FieldFilterDropdownList fieldOption) {
		getSelectField().selectByVisibleText(fieldOption.getFieldName());
	}

	/**
	 * Gets all options available for selection in the Field Filter Drop Down List into a Set
	 */
	public Set<String> getSelectFieldOptions() {
		Select selectFieldFilterElement = getSelectField();
		List<WebElement> listOptions = selectFieldFilterElement.getOptions();
		Set<String> options = new HashSet<>();
		listOptions.forEach(p -> {
			options.add(p.getText().toLowerCase());
		});
		return options;
	}

	public void selectCondition(ConditionFilterDropdownList conditionOption) {
		getSelectCondition().selectByVisibleText(conditionOption.getNameOfConditionFilterField());
	}

	/**
	 * Gets all options available for selection in the Condition Filter Drop Down List into a Set
	 */
	public Set<String> getSelectConditionOptions() {
		Select selectConditionFilterElement = getSelectCondition();
		List<WebElement> listOptions = selectConditionFilterElement.getOptions();
		Set<String> options = new HashSet<>();
		listOptions.forEach(p -> {
			options.add(p.getText());
		});
		return options;
	}

	public void search(String searchTerm) {
		WebElement inputSearchField = getSearchFieldInput();
		inputSearchField.clear();
		inputSearchField.sendKeys(searchTerm);
		clickSearchButton();
	}

	/**
	 * Select Condition by index and stay on page
	 */
	public AAdminReportPage selectConditionByIndex(final int index) {
		final Select selectConditionFilter = getSelectCondition();
		selectConditionFilter.selectByIndex(index);
		return this;
	}

	/**
	 * Configure all search filters, input search term, perform search
	 * and stay on page
	 */
	public AAdminReportPage filterAndSearch(FieldFilterDropdownList fieldOption,
			ConditionFilterDropdownList conditionOption, String searchTerm) {
		selectField(fieldOption);
		selectCondition(conditionOption);
		search(searchTerm);
		return this;
	}


	public AAdminReportPage sortByFirstNameASC() {
		clickRegionLink();
		waitForLoad();
		clickFirstNameLink();
		return this;
	}

	public AAdminReportPage sortByFirstNameDESC() {
		clickRegionLink();
		waitForLoad();
		clickFirstNameLink();
		waitForLoad();
		clickFirstNameLink();
		return this;
	}

	public AAdminReportPage sortByLastNameASC() {
		clickRegionLink();
		waitForLoad();
		clickLastNameLink();
		return this;
	}

	public AAdminReportPage sortByLastNameDESC() {
		clickRegionLink();
		waitForLoad();
		clickLastNameLink();
		waitForLoad();
		clickLastNameLink();
		return this;
	}

	public AAdminReportPage sortByLoginASC() {
		clickRegionLink();
		waitForLoad();
		clickLoginLink();
		return this;
	}

	public AAdminReportPage sortByLoginDESC() {
		clickRegionLink();
		waitForLoad();
		clickLoginLink();
		waitForLoad();
		clickLoginLink();
		return this;
	}

	public AAdminReportPage sortByRoleASC() {
		clickRegionLink();
		waitForLoad();
		clickRoleLink();
		return this;
	}

	public AAdminReportPage sortByRoleDESC() {
		clickRegionLink();
		waitForLoad();
		clickRoleLink();
		waitForLoad();
		clickRoleLink();
		return this;
	}

	public AAdminReportPage sortByRegionASC() {
		clickFirstNameLink();
		waitForLoad();
		clickRegionLink();
		return this;
	}

	public AAdminReportPage sortByRegionDESC() {
		clickFirstNameLink();
		waitForLoad();
		clickRegionLink();
		waitForLoad();
		clickRegionLink();
		return this;
	}

	/**
	 * Expands default table mode of showing 5 users on a page
	 * to show 10 users on a page
	 */
	public AAdminReportPage showTenRows() {
		if (getShowItemsLinkText().equals("Show 5 items"))
			return this;
		clickShowItemsLink();
		return this;
	}

	/**
	 * Execute wait from super class and stay on current page
	 */
	@Override
	public AAdminReportPage waitForLoad() {
		super.waitForLoad();
		return this;

	}

	// Get list of users from current page
	@Step("Get list of users from current page")
	public List<User> getUsersFromCurrentPage() {
		final List<User> userListFormCurrentPage = new ArrayList<>();
		final WebElement table = getTableBody();
		final List<WebElement> webElements = table.findElements(TR_TAGNAME.by);
		for (WebElement rows : webElements) {
			final List<WebElement> tableCells = rows.findElements(TD_TAGNAME.by);
			User user = new User();
			user.setFirstname(tableCells.get(0).getText());
			user.setLastname(tableCells.get(1).getText());
			user.setLoginname(tableCells.get(2).getText());
			user.setRole(tableCells.get(3).getText());
			user.setRegion(tableCells.get(4).getText());
			userListFormCurrentPage.add(user);
		}
		return userListFormCurrentPage;
	}

	/**
	 * Traverses every page and adds every user displayed
	 * to the list
	 */
	public List<User> getAllUsers() {
		clickFirstButton();
		final List<User> usersOnAllPages = new ArrayList<>();
		List<User> usersFromCurrentPage = this.getUsersFromCurrentPage();
		while (usersFromCurrentPage != null) {
			usersOnAllPages.addAll(usersFromCurrentPage);
			if (isForwardButtonEnabled()) {
				clickForwardButton();
				usersFromCurrentPage = this.getUsersFromCurrentPage();
			} else {
				break;
			}
		}
		return usersOnAllPages;
	}

	/**
	 * Gets user by login and creates java object of class User
	 * based on the data taken
	 */
	public User getUserByLoginAndTransferToJavaObject(String login) {
		selectField(FieldFilterDropdownList.LOGIN);
		selectCondition(ConditionFilterDropdownList.EQUALS);
		search(login);

		User user = new User();
		user.setFirstname(getUserByLoginText());
		user.setLastname(getUserByLoginText());
		user.setPassword(getUserByLoginText());
		user.setLoginname(getUserByLoginText());
		user.setEmail(getUserByLoginText());
		user.setRegion(getUserByLoginText());
		user.setRole(getUserByLoginText());

		return user;
	}

	@Step("Compare result of sorting from table with results of sql query")
	public boolean compareLogins(String SQLQuery) {
		List<WebElement> logins = getLogins();
		List<WebElement> firstNames = getFirstNames();
		List<WebElement> lastNames = getLastNames();
		List<WebElement> roles = getRoles();
		List<WebElement> regions = getRegions();

		int size = logins.size();
		String[] textofLogins = new String[size];
		String[] textofFirstNames = new String[size];
		String[] textofLastNames = new String[size];
		String[] textOfRoles = new String[size];
		String[] textOfRegions = new String[size];
		for (int i = 0; i < size; i++) {
			System.out.println(i);
			textofLogins[i] = logins.get(i).getText().trim();
			textofFirstNames[i] = firstNames.get(i).getText().trim();
			textofLastNames[i] = lastNames.get(i).getText().trim();
			textOfRoles[i] = roles.get(i).getText().trim();
			textOfRegions[i] = regions.get(i).getText().trim();
			System.out.println(textOfRoles[i]+"  "+textOfRegions[i]);
		}

		DBUtils dbUtility = new DBUtils();
		List<String> loginsFromDB = dbUtility.getLogins(SQLQuery);

		boolean isEqual = true;

		System.out.printf("%-20s%-20s%-20s%s\n", "\nLogin/table   ", "First Name/table   ", "Last Name/table  ",
				"Login/DB");
		for (int i = 0; i < size; i++) {
			System.out.printf("%-20s%-20s%-20s%-20s%-20s%s\n", textofLogins[i], textofFirstNames[i], textofLastNames[i],
					textOfRegions[i], textOfRoles[i], loginsFromDB.get(i));
			if (textofLogins[i].equals(loginsFromDB.get(i))) {

				continue;
			} else {
				isEqual = false;
				break;
			}
		}

		return isEqual;

	}
}
