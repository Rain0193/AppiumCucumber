package StepDefinitions;

import CommonHelpers.OncomateIOS;
import Utils.Global;
import Utils.MobileApp;
import Utils.Utility;
import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import CommonHelpers.OncomateMethod;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MainScreen extends Utility {

    String filePath = System.getProperty("user.dir") + File.separator+ "target"+File.separator+ "screenshots";

    @Before
    public void startTest(Scenario scenario) throws InterruptedException, IOException {
        System.out.println("----------Before----------");
        MobileApp.logger = MobileApp.report.startTest(scenario.getName());
//        MobileApp.killPort(27753);
    }



    @Given("^Launch the Oncomate App$")
    public void launch_the_Oncomate_App() throws Throwable {
        long timeStamp = new Date().getTime();
        MobileApp.getDriver().launchApp();
        MobileApp.logger.log(LogStatus.PASS, "Oncomate App is launched successfully");
        System.out.println("App launched...");
        Assert.assertTrue(true);
    }

    @When("^Provide Security Pin as (.*)$")
    public void provide_Security_Pin_as(String arg1) throws Throwable {
        long timeStamp = new Date().getTime();
        wait(Global.medWait);
       // explicitWait(MobileApp.getDriver(),MobileApp.getDriver().findElement(By.className("android.view.View")),40000);
        if(MobileApp.androidPlatform) {
            OncomateMethod.setPinFromCmd(arg1);
        }else {
            OncomateIOS.setPinFromCmd(arg1);
        }
        MobileApp.logger.log(LogStatus.PASS, "Security Pin is Entered");
        Assert.assertTrue(true);
    }

    @After
    public void endTest(Scenario scenario){
        System.out.println("----------After----------");
        MobileApp.report.endTest(MobileApp.logger);
        String scrFile = ((TakesScreenshot) MobileApp.getDriver()).getScreenshotAs(OutputType.BASE64);
        MobileApp.logger.addBase64ScreenShot(scrFile);
    }
}
