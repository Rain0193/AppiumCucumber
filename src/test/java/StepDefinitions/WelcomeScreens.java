package StepDefinitions;

import Utils.Global;
import Utils.Utility;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.io.File;
import java.util.Date;


public class WelcomeScreens extends Utility {

    private String filePath = System.getProperty("user.dir") + File.separator+ "target"+File.separator+ "screenshots";

    @Given("^Swipe from right to left in (.*) welcome screen$")
    public void swipe_from_right_to_left_in_Inbox_welcome_screen(String args) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Performing swipe operation from right to left on "+args+" welcome screen..");
        swipeFromRightToLeft();
    }

    @Then("^Click on Skip button$")
    public void click_on_Skip_button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Clicking on Skip Button on Welcome screen..");
        clickOn("WelcomeScreen","skipButton");

    }

}
