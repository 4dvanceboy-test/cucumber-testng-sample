package stepDefinitions;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.RemoteWebDriver;

import MyRunner.TestRunner;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hook {
   public static RemoteWebDriver driver;

    @Before
    public void updateName(Scenario scenario) throws InterruptedException, MalformedURLException {
        	String username = System.getenv("LT_USERNAME") == null ? "YOUR LT_USERNAME" : System.getenv("LT_USERNAME"); 
    		String accesskey = System.getenv("LT_ACCESS_KEY") == null ? "YOUR LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY"); 

        String gridURL = "https://" + username + ":" + accesskey + "@hub.lambdatest.com/wd/hub";
            System.out.println(gridURL);
            this.driver = new RemoteWebDriver(new URL(gridURL), TestRunner.desqueue.poll());
        Thread.sleep(0);
        //scenario.write(driver.getSessionId().toString());
        System.out.println("I am in Before Hook" + driver.getSessionId()  + scenario.getId());
        driver.executeScript("lambda-name=" + scenario.getName());
    }

    @After
    public void close_the_browser(Scenario scenario) {
        System.out.println("I am in after Hook"+ driver.getSessionId() + scenario.getId());
        scenario.write(driver.getSessionId().toString());
        driver.executeScript("lambda-status=" + (scenario.isFailed() ? "failed" : "passed"));
        driver.quit();
    }

}