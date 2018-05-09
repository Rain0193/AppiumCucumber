package cucumberTestng;


import Utils.MobileApp;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

@CucumberOptions(features = "src/main/resources/Features/OM_Tasks.feature", format = { "pretty",
        "html:target/site/cucumber-pretty",
        "rerun:target/rerun.txt",
        "json:target/cucumber1.json" },
        glue = {"classpath:StepDefinitions"},
        tags = {"~@ignore"})
public class BS_IOSTestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public static void init() throws Throwable {
        System.out.println("----BeforeClass----");
        MobileApp.report = new ExtentReports("target\\report.html",true);
        String fileName = System.getProperty("prerequisite");
        System.out.println("Running pre req status : " + fileName);
        try {
            MobileApp.setUp();
        } catch (Exception e) {
            MobileApp.logger = MobileApp.report.startTest("Installing pre requisites on browserstack for IOS...");
            MobileApp.logger.log(LogStatus.FAIL," Failed to setup the application pre requisites .... ");
            throw e.getCause();
        }
    }

    @AfterClass
    public static void tearDown(){
        System.out.println("----------After Class----------");
        MobileApp.closeDriver();
        MobileApp.report.flush();
    }
}