package ohtu;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Stepdefs { 
    WebDriver driver = new ChromeDriver();
    String baseUrl = "http://localhost:4567";
    
    @Given("^login is selected$")
    public void login_selected() throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("login"));       
        element.click();          
    } 
	
	@Given("^command new user is selected$")
	public void create_selected() throws Throwable {
		driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("register new user"));       
        element.click();      
	}

    @When("^username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void username_and_password_are_given(String username, String password) throws Throwable {
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        element.submit();  
    }

    @Then("^system will respond \"([^\"]*)\"$")
    public void system_will_respond(String pageContent) throws Throwable {
        assertTrue(driver.getPageSource().contains(pageContent));
    }
    
    @When("^correct username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void username_correct_and_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @When("^correct username \"([^\"]*)\" and incorrect password \"([^\"]*)\" are given$")
    public void username_and_incorrect_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }
    
    @When("^nonexistent username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void nonexistent_username_and_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }
    
	@When("^a valid username \"([^\"]*)\" and password \"([^\"]*)\" and matching password confirmation are entered$")
	public void valid_username_and_password_are_given(String username, String password) throws Throwable {
		createUserWith(username, password, password);
	}

	@When("^a short username \"([^\"]*)\"  and password \"([^\"]*)\" and matching password confirmation are entered$")
	public void short_username_and_password_are_given(String username, String password) throws Throwable {
		createUserWith(username, password, password);
	}

	@When("^a valid username \"([^\"]*)\" and short password \"([^\"]*)\" and matching password confirmation are entered$")
	public void valid_username_and_short_password_are_given(String username, String password) throws Throwable {
		createUserWith(username, password, password);
	}

	@When("^a taken username \"([^\"]*)\" and valid password \"([^\"]*)\" and matching password confirmation are entered$")
	public void taken_username_and_password_are_given(String username, String password) throws Throwable {
		createUserWith(username, password, password);
	}

	@When("^a valid username \"([^\"]*)\" and valid password \"([^\"]*)\" and different password confirmation \"([^\"]*)\" are entered$")
	public void valid_username_and_password_and_wrong_confirmation_are_given(String username, String password, String confPassword) throws Throwable {
		createUserWith(username, password, confPassword);
	}

	@When("^user with username \"([^\"]*)\" with password \"([^\"]*)\" is successfully created$")
	public void user_with_username_and_password_is_created(String username, String password) throws Throwable {
		driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("register new user"));       
        element.click(); 
		createUserWith(username, password, password);
		logout();
	}

	@When("^user with username \"([^\"]*)\" and password \"([^\"]*)\" is tried to be created$")
	public void user_with_username_and_password_is_tried(String username, String password) throws Throwable {
		driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("register new user"));       
        element.click();
		createUserWith(username, password, password);
		logout_after_fail();
	}

	@Then("^a new user is created$")
	public void new_user_is_created() throws Throwable {
        pageHasContent("continue to application mainpage");
    }

    @Then("^user is logged in$")
    public void user_is_logged_in() throws Throwable {
        pageHasContent("Ohtu Application main page");
    }
    
    @Then("^user is not logged in and error message is given$")
    public void user_is_not_logged_in_and_error_message_is_given() throws Throwable {
        pageHasContent("invalid username or password");
        pageHasContent("Give your credentials to login");
    }     

	@Then("^user is not created and error \"([^\"]*)\" is reported$")
	public void user_is_not_created_and_error_message_is_given(String error) throws Throwable {
		pageHasContent(error);
	}
    
    @After
    public void tearDown(){
        driver.quit();
    }
        
    /* helper methods */
 
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }
	
	private void logout() {
		WebElement element = driver.findElement(By.linkText("continue to application mainpage"));
		element.click();
		element = driver.findElement(By.linkText("logout"));
		element.click();
	}
    private void logout_after_fail() {
		WebElement element = driver.findElement(By.linkText("back to home"));
		element.click();
	}
    
    private void logInWith(String username, String password) {
        assertTrue(driver.getPageSource().contains("Give your credentials to login"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        element.submit();  
    } 

	private void createUserWith(String username, String password, String confPassword) {
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(username);
		element = driver.findElement(By.name("password"));
		element.sendKeys(password);
		element = driver.findElement(By.name("passwordConfirmation"));
		element.sendKeys(confPassword);
		element = driver.findElement(By.name("signup"));
		element.submit();
	}

}
