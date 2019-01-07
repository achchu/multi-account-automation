package perkbox.login.tests.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import perkbox.login.tests.locators.LoginObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class LoginSteps extends BaseSteps {

    @FindBy(how = How.NAME, using = LoginObjects.EMAIL)
    private WebElement loginEmail;

    @FindBy(how = How.ID, using = LoginObjects.CONTINUE)
    private WebElement continueButton;

    //ideally this list should be loaded from the database against the email address
    private List<String> expectedAccountNames = new ArrayList<>();

    public LoginSteps() throws IOException {
        openBrowser();
        expectedAccountNames.add("themistrypenguin");
        expectedAccountNames.add("demo-test-france");
        expectedAccountNames.add("home");
        expectedAccountNames.add("poojatest2");
        expectedAccountNames.add("demoone");
        expectedAccountNames.add("guild");
        expectedAccountNames.add("testtenant3");
    }

    @After
    public void cleanUp() {
        chromeDriver.close();
    }

    @Given("^I am on perkbox login page$")
    public void iAmOnPerkboxLoginPage() {
        PageFactory.initElements(chromeDriver, this);
        chromeDriver.get(getPropertyValue("homepage"));
    }

    @When("^I enter valid email$")
    public void iEnterValidEmail() {
        WebDriverWait wait = new WebDriverWait(chromeDriver, 20);
        wait.until(ExpectedConditions.visibilityOf(this.loginEmail));
        this.loginEmail.sendKeys(getPropertyValue("email"));
    }

    @And("^I click on continue$")
    public void iClickOnContinue() {
        this.continueButton.click();
    }

    @Then("^I should see all the accounts available to me$")
    public void iShouldSeeAllTheAccountsAvailableToMe() {
        explicitWait(3000);
        List<String> actualAccountNames = new ArrayList<>();
        List<WebElement> resultsElement = chromeDriver.findElements(By.xpath(LoginObjects.ACCOUNTS_RESULTS));
        for (WebElement account : resultsElement) {
            actualAccountNames.add(account.getText());
        }
        Collections.sort(expectedAccountNames);
        Collections.sort(actualAccountNames);
        assertTrue(expectedAccountNames.equals(actualAccountNames));
    }

    @And("^I click on an account name$")
    public void iClickOnAnAccountName() {
        explicitWait(3000);
        chromeDriver.findElements(By.xpath(LoginObjects.ACCOUNTS_RESULTS)).get(0).click();
    }

    @When("^I click on confirm$")
    public void iClickOnConfirm() {
        chromeDriver.findElement(By.id(LoginObjects.CONFIRM_BUTTON)).click();
    }

    @Then("^I should see the email prepopulated in the login page$")
    public void iShouldSeeTheEmailPrepopulatedInTheLoginPage() {
        explicitWait(3000);
        String filledEmail = chromeDriver.findElement(By.id(LoginObjects.POPULATED_EMAIL)).getAttribute("value");
        String expectedEmail = getPropertyValue("email");
        assertEquals(expectedEmail, filledEmail);
    }
}
