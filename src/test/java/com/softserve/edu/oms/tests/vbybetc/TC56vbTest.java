package com.softserve.edu.oms.tests.vbybetc;

import com.softserve.edu.oms.data.DBUtils;
import com.softserve.edu.oms.data.IUser;
import com.softserve.edu.oms.data.UserRepository;
import com.softserve.edu.oms.pages.AdminHomePage;
import com.softserve.edu.oms.pages.AdministrationPage;
import com.softserve.edu.oms.pages.CreateNewUserPage;
import com.softserve.edu.oms.tests.TestRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class TC56vbTest extends TestRunner{

    @DataProvider
    public Object[][] admUser() {
        return new Object[][] {
                { UserRepository.get().adminUser() }
        };
    }

    @DataProvider
    public Object[][] nonExistingUser() {
        return new Object[][] {
                { UserRepository.get().nonExistingUser() }
        };
    }

    @Test(dataProvider = "admUser")
    public void PreconditionTest(IUser admUser) {

        AdminHomePage adminHomePage = loginPage.successAdminLogin(admUser);
        adminHomePage.clickAdministrationTab();

        AdministrationPage administrationPage = new AdministrationPage(driver);
        administrationPage.goToCreateNewUserPage();
    }

    @Test(dataProvider = "nonExistingUser")
    public void UniqueUserCreatingTest(IUser nonExistingUser) throws SQLException, InterruptedException {

        DBUtils dbUtils = new DBUtils();

        String nonExistingLogin = nonExistingUser.getLoginname();

        while(dbUtils.verifyThatUserIsInDB(nonExistingLogin)) {
            nonExistingLogin = RandomStringUtils.random(5, true, false).toLowerCase();
        }

        CreateNewUserPage newUserPage = new CreateNewUserPage(driver);
        newUserPage
                .setInputLogin(nonExistingLogin)
                .setInputFirstName(nonExistingUser.getFirstname())
                .setInputLastName(nonExistingUser.getLastname())
                .setInputPassword(nonExistingUser.getPassword())
                .setInputConfirmPassword(nonExistingUser.getPassword())
                .setInputEmail(nonExistingUser.getEmail())
                .successCreateNewUser();

        AdministrationPage administrationPage = new AdministrationPage(driver);
        administrationPage.goToCreateNewUserPage();

        CreateNewUserPage newUserPageAgain = new CreateNewUserPage(driver);
        newUserPageAgain.setInputLogin(nonExistingLogin.toUpperCase());

        Thread.sleep(10000);
        Assert.assertTrue(newUserPageAgain.getLoginErrorMessageText().contains("already in use"));

        dbUtils.deleteUserFromDB(nonExistingUser.getLoginname());
    }

}