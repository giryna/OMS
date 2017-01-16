package com.softserve.edu.oms.tests.createuser;

import static com.softserve.edu.oms.enums.ErrorMessagesEnum.FIRST_NAME_ERROR_MESSAGE;
import static com.softserve.edu.oms.enums.ErrorMessagesEnum.LAST_NAME_ERROR_MESSAGE;
import static org.hamcrest.MatcherAssert.assertThat;

import javarestclient.TestResultsListener;
import javarestclient.annotations.TransferToJira;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.softserve.edu.oms.data.DBUtils;
import com.softserve.edu.oms.data.IUser;
import com.softserve.edu.oms.data.User;
import com.softserve.edu.oms.data.UserRepository;
import com.softserve.edu.oms.pages.CreateNewUserPage;
import com.softserve.edu.oms.tests.TestRunner;

import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import ru.yandex.qatools.allure.model.SeverityLevel;

/**
 * This test verifies that error messages are shown when we creating new user
 * with digits in last and first name
 *
 * Based on LVSETOMS-54 in Jira
 *
 * @author Anastasiia Maidanska
 * @version 1.0
 * @since 20.12.16
 */
@Features("Create New User")
@Stories("LVSETOMS-3 As Administrator I want to create new user so he can log into the application")
@Listeners(TestResultsListener.class)
public class CreateInvalidNewUserTest extends TestRunner {


	/**
	 * Provides data for user creation
	 * 
	 * @return invalidUser from UserRepository
	 */

	@DataProvider
	public Object[][] invalidUsers() {
		return new Object[][] { { UserRepository.get().invalidUser() } };
	}

	/**
	 * This test verifies that error messages are shown when we creating new
	 * user with digits in last and first name
	 */
	@TestCaseId("LVSETOMS-")
	@Severity(SeverityLevel.MINOR)
	@Description("")

	@Test(dataProvider = "invalidUsers")
	@Step
	@TransferToJira
	public void verifyErrorMessageCreatingUserWithInvalidData(User user) {
		IUser admin = UserRepository.get().adminUser();

		// Go to create new user page and set data
		CreateNewUserPage createNewUserPage = loginPage.successAdminLogin(admin).gotoAdministrationPage()
				.gotoCreateNewUserPage().setLoginData(user);

		// Verify that correct message displays near 'First Name' field
		assertThat(createNewUserPage.getFirstNameErrorMessageText(),
				CoreMatchers.equalTo(FIRST_NAME_ERROR_MESSAGE.message));

		// Verify that correct message displays near 'Last Name' field
		assertThat(createNewUserPage.getLastNameErrorMessageText(),
				CoreMatchers.equalTo(LAST_NAME_ERROR_MESSAGE.message));

		DBUtils dbUtils = new DBUtils();

		// Verify that user with invalid 'First Name' and 'Last Name' is not
		// created
		assertThat(dbUtils.getUserByLogin(user.getLoginname()), CoreMatchers.equalTo(null));
	}

}
