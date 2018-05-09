package StepDefinitions;

import Utils.Global;
import Utils.MobileApp;
import Utils.Utility;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Calendar extends Utility {

    private String filePath = System.getProperty("user.dir") + File.separator+ "target"+File.separator+ "screenshots";

    @When("^Swipe on Calendar Menu option$")
    public void swipe_on_Calendar_Menu_option() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        System.out.println("Swipe on Tasks Menu option from Landing screen..");
        swipe("HomePage","addAppointmentIcon","calendarMenu");    }

    @Then("^Click on Add Appointment Icon$")
    public void click_on_Add_Appointment_Icon() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        clickOn("HomePage", "addAppointmentIcon");
    }

    @Then("^Provide Appointment reason as (.*)$")
    public void provide_Appointment_reason_as_Automation_Created_Appointmtnt(String reason) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        enterText("Appointment", "appointmentReason", reason);
    }

    @Then("^Click on First Available Appointment button\\.$")
    public void click_on_First_Available_Appointment_button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        clickOn("Appointment", "firstAvailableAppointmentButton");
    }

    @Then("^Click Ok Button$")
    public void click_Ok_Button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        clickOn("Appointment", "okButton");
    }

    @Then("^Provide Contact number as (\\d+)$")
    public void provide_Contact_number_as(long Number) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Contact Number :"+Number);

        String ContactNumber = String.valueOf(Number);
        enterText("Appointment", "myContact", ContactNumber);
    }

    @Then("^Click on Choose dates button\\.$")
    public void click_on_Choose_dates_button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        clickOn("Appointment", "chooseDatesButton");
    }

    @Then("^Click on Next Month button$")
    public void click_on_Next_Month_button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        clickOn("Appointment", "nextMonth");
    }

    @Then("^Select Appointment (\\d+)\\.(\\d+)\\.(\\d+)$")
    public void select_Appointment(int arg1, int arg2, int arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("------Day-------");
        selectDay("Calendar","day",arg3);
    }

    @Then("^Click on Ok button$")
    public void click_on_Ok_button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Calendar","okButton");
    }

    @Then("^Click on Calendar icon$")
    public void click_on_Calendar_icon() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Appointment", "calendarIcon");

    }

    @Then("^Click on Add Choice button$")
    public void click_on_Add_Choice_button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Appointment", "addChoiceIcon");
    }

    @Then("^Select Appointment Timing as (.*)$")
    public void select_Appointment_Timing_as(String Time) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        MobileApp.getDriver().findElementByXPath("//android.view.ViewGroup//android.view.ViewGroup//android.widget.TextView[contains(@text,'"+Time+"')]").click();
    }

    @Then("^Deselect the appointment (.*)$")
    public void deselect_the_appointment(String Time) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        MobileApp.getDriver().findElementByXPath("//android.view.ViewGroup//android.view.ViewGroup//android.widget.TextView[contains(@text,'"+Time+"')]").click();
    }

    @Then("^Click Done Button$")
    public void click_Done_Button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Appointment", "doneButton");
    }

    @Then("^Click Send Button$")
    public void click_Send_Button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Appointment", "sendButton");
    }

    @Then("^Click on Ok Button$")
    public void click_on_Ok_Button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Appointment", "appointmentConfirmationButton");
    }

    @Then("^Click on Edit Details Button$")
    public void click_on_Edit_Details_Button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Appointment", "editDetailsButton");
    }
    @When("^Click on Calendar$")
    public void click_on_Calendar() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        clickOn("HomePage", "calendarMenu");
        AppiumDriver driver;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.google.android.calendar");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.google.android.calendar.AllInOneCalendarActivity");
        driver =  new AndroidDriver( new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 180);
        driver.manage().timeouts().implicitlyWait(8000, TimeUnit.SECONDS);

        driver.findElementByAccessibilityId("3:30 PM – 3:45 PM: Follow-up (VARIANTEST:At_2020)").click();

    }


}
