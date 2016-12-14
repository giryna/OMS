package com.softserve.edu.oms.tests.vbybetc;

import com.softserve.edu.oms.data.IUser;
import com.softserve.edu.oms.data.UserRepository;
import com.softserve.edu.oms.pages.AdminHomePage;
import com.softserve.edu.oms.tests.TestRunner;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TC41vbTest extends TestRunner {

    @DataProvider
    public Object[][] admUser() {
        return new Object[][] {
                { UserRepository.get().adminUser() }
        };
    }

    @Test(dataProvider = "admUser")
    public void TabSwitchingTest(IUser admUser) {

        AdminHomePage adminHomePage = loginPage.successAdminLogin(admUser);
        Assert.assertTrue(adminHomePage.getUserInfoTab().isEnabled());
        Assert.assertTrue(adminHomePage.getAdministrationTab().isDisplayed());

        adminHomePage.clickAdministrationTab();
        Assert.assertTrue((driver.getCurrentUrl()).contains("users.htm"));

        adminHomePage.clickUserInfoTab();
        Assert.assertTrue((driver.getCurrentUrl()).contains("userInfo.htm"));
    }
}