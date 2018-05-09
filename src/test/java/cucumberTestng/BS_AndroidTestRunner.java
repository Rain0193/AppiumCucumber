package cucumberTestng;


import Utils.MobileApp;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

@CucumberOptions(features = "src/main/resources/Features/", format = { "pretty",
        "html:target/site/cucumber-pretty",
        "rerun:target/rerun.txt",
        "json:target/cucumber1.json" },
        glue = {"classpath:StepDefinitions"},
        tags = {"~@ignore"})
public class BS_AndroidTestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public static void init() throws Throwable {
        System.out.println("----BeforeClass----");
      //  String fileName = System.getProperty("prerequisite");
      //  System.out.println("Running pre req status : " + fileName);
        MobileApp.report = new ExtentReports("target\\report.html",true);
        try {
            MobileApp.setUp();
        } catch (Exception e) {
            MobileApp.logger = MobileApp.report.startTest("Installing pre requisites on browserstack for android...");
            MobileApp.logger.log(LogStatus.FAIL," Failed to setup the application pre requisites .... ");
            throw e.getCause();
        }
    }

    @AfterClass
    public static void tearDown() throws IOException {
        System.out.println("----------After Class----------");
        MobileApp.closeDriver();
        MobileApp.report.flush();
    }
}