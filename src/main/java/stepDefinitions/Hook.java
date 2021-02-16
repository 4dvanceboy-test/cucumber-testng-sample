package stepDefinitions;

import org.openqa.selenium.remote.RemoteWebDriver;

import MyRunner.TestRunner;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hook extends TestRunner {
    //public RemoteWebDriver driver = this.connection;

    @Before
    public void updateName(Scenario scenario) throws InterruptedException {
       Thread.sleep(30);
        driver.get().executeScript("lambda-name=" + scenario.getName());
    }

    @After
    public void close_the_browser(Scenario scenario) {
        driver.get().executeScript("lambda-status=" + (scenario.isFailed() ? "failed" : "passed"));
        driver.get().quit();
        driver.remove();
    }

}