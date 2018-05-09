package StepDefinitions;

import Utils.Utility;
import cucumber.api.java.en.Then;

import java.io.File;


public class HamburgerMenu extends Utility {

    private String filePath = System.getProperty("user.dir") + File.separator+ "target"+File.separator+ "screenshots";

    @Then("^Click on Hamburger Menu$")
    public void click_on_Hamburger_Menu() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        clickOn("HamburgerMenu","hamburgerMenuIcon");
    }


}
