package MyRunner;

import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions(
        features = "src/main/java/Features",
        glue = {"stepDefinitions"},dryRun = false,
        tags = {"~@Ignore"},
        format = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        },plugin = "json:target/cucumber-reports/CucumberTestReport.json")

public class TestRunner {
	
    private TestNGCucumberRunner testNGCucumberRunner;

    public static ConcurrentLinkedQueue<DesiredCapabilities> desqueue = new ConcurrentLinkedQueue();
  
    // public RemoteWebDriver driver;
    
    @BeforeClass(alwaysRun = true)
    public void setUpCucumber() {
    	 testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }
    
    @BeforeMethod(alwaysRun = true)
    @Parameters({ "browser", "version", "platform" })
    public void setUpClass(String browser, String version, String platform) throws Exception {

    	
    		DesiredCapabilities capability = new DesiredCapabilities();    		
    		capability.setCapability(CapabilityType.BROWSER_NAME, browser);
    		capability.setCapability(CapabilityType.VERSION,version);
    		capability.setCapability(CapabilityType.PLATFORM, platform);
    		    		
    		capability.setCapability("build", "Cucumber Sample Build 6");
    		
    		capability.setCapability("network", true);
    		capability.setCapability("video", true);
    		capability.setCapability("console", true);
    		capability.setCapability("visual", true);

    		
            desqueue.add(capability);
            
            //connection.get("https://lambdatest.github.io/sample-todo-app/");
           // System.out.println("testrunner\n"+connection);
}
 
    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features", invocationCount = 3)
    public void feature(CucumberFeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }
 
    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
    }
 
    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
    }
}