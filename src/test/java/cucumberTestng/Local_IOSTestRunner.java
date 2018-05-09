package cucumberTestng;


import Utils.MobileApp;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.io.File;

@CucumberOptions(features = "src/main/resources/Features/", format = { "pretty",
        "html:target/site/cucumber-pretty",
        "rerun:target/rerun.txt",
        "json:target/cucumber1.json" },
        glue = {"classpath:StepDefinitions"},
        tags = {"~@ignore"})
public class Local_IOSTestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public static void init() throws Throwable {
        //String fileName = System.getProperty("prerequisite");
        //System.out.println("Running pre req status : " + fileName);
        System.out.println("----IOS Test Suite started...----");
        System.out.println("----BeforeClass----");
        MobileApp.report = new ExtentReports(System.getProperty("user.dir") + File.separator+ "target"+File.separator+ "extended_report.html",true);
        try {
            MobileApp.setUp();
        } catch (Exception e) {
            MobileApp.logger = MobileApp.report.startTest("Installing pre requisites for local PageObjects.IOS...");
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
