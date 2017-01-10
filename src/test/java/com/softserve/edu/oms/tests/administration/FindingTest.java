package com.softserve.edu.oms.tests.administration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.softserve.edu.oms.data.DBUtils;
import com.softserve.edu.oms.data.IUser;
import com.softserve.edu.oms.data.UserRepository;
import com.softserve.edu.oms.enums.ConditionFilterDropdownList;
import com.softserve.edu.oms.enums.FieldFilterDropdownList;
import com.softserve.edu.oms.enums.LabelsNamesEnum;
import com.softserve.edu.oms.enums.SQLQueries;
import com.softserve.edu.oms.pages.AdminHomePage;
import com.softserve.edu.oms.pages.AdministrationPage;
import com.softserve.edu.oms.tests.TestRunner;

import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import ru.yandex.qatools.allure.model.SeverityLevel;

/**
 * Test class which verifies search function on Administration page.
 *
 * Based on LVSETOMS-47 in Jira
 * 
 * @author Oleh Lavrynenko, Roman Raba
 * @since 16.12.16
 * @link http://ssu-jira.softserveinc.com/browse/LVSETOMS-47
 */
@Features("Administration")
@Stories("LVSETOMS-4 As Admin I want to see all existing users and perform "
		+ "user searching on the 'Administration' tab so I can manage them ")

public class FindingTest extends TestRunner {
    private SoftAssert softAssert = new SoftAssert();
    private AdministrationPage administrationPage;

    private final String VALID_NAME = UserRepository.get().adminUser().getLoginname();
    
    /**
	 * Logger method for Allure Framework. It method is used
	 *  for inserting Allure Steps into different methods
	 * @param stepMsg
	 */
	@Step("{0}")
	private void innerStep(String stepMsg){}

    
    /**
     * Set preconditions for test:
     * login with Administrator role credentials
     * and go to Administration page
     */
    @BeforeMethod
    public void setUp() {
    	innerStep("Loging as administrator");
        AdminHomePage adminHomePage =
                loginPage.successAdminLogin(UserRepository.get().adminUser());
        innerStep("Go to Administration page");
        administrationPage =
                adminHomePage.gotoAdministrationPage();
    }

    
    /**
     * Logout from session.
     */
    @AfterMethod
    public void tearDown() {
    	innerStep("Log out from Application");
        administrationPage.logout();
    }

    
    /**
     * Verify that searching options in dropdown lists are correct
     *
     * @author Oleh Lavrynenko
     * @version 1.0
     * @since 16.12.16
     */
    @TestCaseId("LVSETOMS-47")
	@Severity(SeverityLevel.NORMAL)
	@Description("Verify that searching options in dropdown lists are correct.")
    @Test
    @Step("testOptionValues")
    public void testOptionValues() {
        softAssert = new SoftAssert();

        //verify that values in dropdown lists are correct and default values are correct too
        innerStep("verify that values in dropdown lists are correct and default values are correct too");
        softAssert.assertEquals(administrationPage.getSelectFieldDefaultValue(), FieldFilterDropdownList.FIRST_NAME.getFieldName());
        softAssert.assertEquals(administrationPage.getSelectFieldOptions(), new HashSet<>(Arrays.asList(FieldFilterDropdownList.values()))
                .stream()
                .map(p -> p.getFieldName().toLowerCase()).collect(Collectors.toSet()));
        softAssert.assertEquals(administrationPage.getSelectConditionDefaultValue(), ConditionFilterDropdownList.EQUALS.getNameOfConditionFilterField());
        softAssert.assertEquals(administrationPage.getSelectConditionOptions(),
                new HashSet<>(Arrays.asList(ConditionFilterDropdownList.values()))
                        .stream()
                        .map(condition -> condition.getNameOfConditionFilterField()).collect(Collectors.toSet()));
        softAssert.assertAll();
    }

    /**
     * Verify that search for too long name  returns all active users.
     *
     */
    @Test
    public void verifySearchTooLongName(IUser admin) {
        softAssert = new SoftAssert();

        //enter too long name in search field
        administrationPage.clickSearchButton();
        administrationPage.filterAndSearch(FieldFilterDropdownList.FIRST_NAME, ConditionFilterDropdownList.EQUALS,
                LabelsNamesEnum.TOO_LONG_NAME.name);

        DBUtils dbUtils = new DBUtils();
        int numberOfUsers = dbUtils.getAllCells("", "").size();
      //verify that result  are all active users
        softAssert.assertEquals(administrationPage.getAllUsers().size(), numberOfUsers);
        softAssert.assertAll();

    }

    /**
     * Verify that search by "Login" and "equals" work correctly.
     *
     * @author Oleh Lavrynenko
     * @version 1.0
     * @since 16.12.16
     */
    @TestCaseId("LVSETOMS-47")
   	@Severity(SeverityLevel.NORMAL)
   	@Description("Verify that search by 'Login' and 'equals' work correctly.")
    @Test
    @Step("verifySearchByEquals")
    public void verifySearchByEquals() {
        softAssert = new SoftAssert();
        //select equals option
        administrationPage.clickSearchButton();
        administrationPage.filterAndSearch(FieldFilterDropdownList.LOGIN, ConditionFilterDropdownList.EQUALS, VALID_NAME);
        DBUtils dbUtils = new DBUtils();

        //verify that search result is correct
        int numberOfUsers = dbUtils.getUserByLogin(VALID_NAME) == null ? 0 : 1;
        softAssert.assertEquals(administrationPage.getAllUsers().size(), numberOfUsers);
        softAssert.assertAll();
    }

    /**
     * Verify that search by "Login" and " not equals" work correctly.
     *
     * @author Oleh Lavrynenko
     * @version 1.0
     * @since 16.12.16
     */
    
    @TestCaseId("LVSETOMS-47")
   	@Severity(SeverityLevel.NORMAL)
   	@Description("Verify that search by 'Login' and 'not equals' work correctly.")
    @Test
    @Step("verifySearchByNotEquals")
    public void verifySearchByNotEquals() {
        softAssert = new SoftAssert();

        administrationPage.clickSearchButton();
        administrationPage.selectField(FieldFilterDropdownList.LOGIN);
        //select not equals option
        administrationPage.selectConditionByIndex(1);
        administrationPage.search(VALID_NAME);

        DBUtils dbUtils = new DBUtils();

        //verify that search result is correct
        int numberOfUsersWithLogin = dbUtils.getUserByLogin(VALID_NAME) == null ? 0 : 1;
        int numberOfUsers = dbUtils.countAllUsers() - numberOfUsersWithLogin;
        softAssert.assertEquals(new AdministrationPage(driver).getAllUsers().size(), numberOfUsers);
        softAssert.assertAll();
    }


    /**
     * Verify that search by "Last Name" and "starts with" work correctly.
     *
     */
    @TestCaseId("LVSETOMS-47")
   	@Severity(SeverityLevel.NORMAL)
   	@Description("Verify that search by 'Last Name' and 'starts with' work correctly.")
    @Test
    @Step("verifySearchLastName")
    public void verifySearchLastName() {
        List<String> columnListFromTable = new ArrayList<>();
        List<String> columnListFromDB;
        DBUtils dbUtils;

        //Initializing search by last name
        administrationPage.filterAndSearch(
                FieldFilterDropdownList.LAST_NAME,
                ConditionFilterDropdownList.START_WITH,
                LabelsNamesEnum.SEARCH_TEXT_NONE.name);

        //Create list of user's last name attribute options 
        //from table which is on Administration page.
        administrationPage.getAllUsers()
                          .forEach(user -> columnListFromTable.add(user.getLastname()));

        //Get list user's last name attribute from DB
        dbUtils = new DBUtils();
        columnListFromDB = dbUtils.getOneColumn(SQLQueries.GET_LASTNAME_LIKE.getQuery(),
                LabelsNamesEnum.BY_LAST_NAME.name,
                LabelsNamesEnum.SEARCH_TEXT_NONE.name,
                LabelsNamesEnum.SEARCH_TEXT_ER.name);

        //Equal two lists
        innerStep("Verify equal two lists from table and DB");
        Assert.assertTrue(columnListFromTable.equals(columnListFromDB));
    }


    /**
     * Verify that search by "Login Name" and "contains" work correctly.
     *
     */
    @TestCaseId("LVSETOMS-47")
   	@Severity(SeverityLevel.NORMAL)
   	@Description("Verify that search by 'Login Name' and 'contains' work correctly.")
    @Test
    @Step("verifySearchLoginName")
    public void verifySearchLoginName() {
        List<String> columnListFromTable = new ArrayList<>();
        List<String> columnListFromDB;
        DBUtils dbUtils;

        //Initializing search by login name
        administrationPage.filterAndSearch(
                FieldFilterDropdownList.LOGIN,
                ConditionFilterDropdownList.CONTAINS,
                LabelsNamesEnum.SEARCH_TEXT_NONE.name);

        //Create list of user's login name attribute options 
        //from table which is on Administration page.
        administrationPage.getAllUsers()
                          .forEach(user -> columnListFromTable.add(user.getLoginname()));

        //Get list user's login name attribute from DB
        dbUtils = new DBUtils();
        columnListFromDB = dbUtils.getOneColumn(SQLQueries.GET_LOGIN_LIKE.getQuery(),
                LabelsNamesEnum.BY_LOGIN_NAME.name,
                LabelsNamesEnum.SEARCH_TEXT_NONE.name,
                LabelsNamesEnum.SEARCH_TEXT_ER.name);

        //Equal two lists
        innerStep("Verify equal two lists from table and DB");
        Assert.assertTrue(columnListFromTable.equals(columnListFromDB));
    }


    /**
     * Verify that search by "Role" and "does not contain" work correctly.
     *
     */
    @TestCaseId("LVSETOMS-47")
   	@Severity(SeverityLevel.NORMAL)
   	@Description("Verify that search by 'Role' and 'does not contain' work correctly.")
    @Test
    @Step("verifySearchRole")
    public void verifySearchRole() {
        List<String> columnListFromTable = new ArrayList<>();
        List<String> columnListFromDB;
        DBUtils dbUtils;
        int numberOfusers;
        int pagesNumber;
        int newPagesCount;
        int numberOfItems;

      //Initializing search by role
        administrationPage.filterAndSearch(
                FieldFilterDropdownList.ROLE,
                ConditionFilterDropdownList.DOES_NOT_CONTAIN,
                LabelsNamesEnum.SEARCH_TEXT_ER.name);

        //Create list of user's role attribute options 
        //from table which is on Administration page.
        administrationPage.getAllUsers()
                          .forEach(user -> columnListFromTable.add(user.getRole()));

        //Get list user's login name attribute from DB
        dbUtils = new DBUtils();
        columnListFromDB = dbUtils.getOneColumn(SQLQueries.GET_ROLE_NOT_LIKE.getQuery(),
                LabelsNamesEnum.BY_ROLE.name,
                LabelsNamesEnum.SEARCH_TEXT_NONE.name,
                LabelsNamesEnum.SEARCH_TEXT_ER.name);

        //Equal two lists
        innerStep("Verify equal two lists from table and DB");
        Assert.assertTrue(columnListFromTable.equals(columnListFromDB));

        //Verify number users in table and in DB after search function apply.
        numberOfusers = administrationPage.getFoundUsersNumber();
        innerStep("Verify number users in table and in DB after search function apply.");
        Assert.assertEquals(columnListFromDB.size(), numberOfusers);

        //Verify that the number of records returned by script divided 
        //by number of records displayed in the table rounded to the bigger integer.
        numberOfItems = administrationPage.getUsersPerPageNumber();
        if ((columnListFromDB.size() % numberOfItems) != 0) {
            pagesNumber = ((columnListFromDB.size() -
                    (columnListFromDB.size() % numberOfItems)) / numberOfItems) + 1;
        } else {
            pagesNumber = columnListFromDB.size() / numberOfItems;
        }
        newPagesCount = administrationPage.getPagesQuantity();
        innerStep("Verify that the number of records returned by script divided" +                  
                "by number of records displayed in the table rounded to the bigger integer");
        Assert.assertEquals(pagesNumber, newPagesCount);

    }

}

