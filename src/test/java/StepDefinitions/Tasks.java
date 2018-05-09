package StepDefinitions;

import Utils.Global;
import Utils.MobileApp;
import Utils.Utility;
import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.JavascriptExecutor;
import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Tasks extends Utility {

    private String filePath = System.getProperty("user.dir") + File.separator+ "target"+File.separator+ "screenshots";


    @When("^Click on Tasks Section in app$")
    public void click_on_Tasks_Section_in_app() throws Throwable {
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Click on Tasks Section in app");
        clickOn("HomePage","tasksMenu");
        MobileApp.logger.log(LogStatus.PASS, "Clicked on Tasks Section..");

    }

    @When("^Pending Tasks should open by default$")
    public void pending_Tasks_should_open_by_default() throws Throwable {
        long timeStamp = new Date().getTime();
        System.out.println("Verifying whether the default tab is Pending Tasks Tab or not...");
        isPresented("PendingTasks","pendingTabFocus");
        MobileApp.logger.log(LogStatus.PASS, "Pending Tasks tab is showing by default");

    }

    @Then("^(.*) Screen should display the label (.*) for (.*)")
    public void verify_label_on_screen( String screen, String text, String loc) throws Throwable {
        long timeStamp = new Date().getTime();
        MobileApp.getDriver().manage().timeouts().implicitlyWait(8000, TimeUnit.SECONDS);
        System.out.println("Verify label "+ text +" in screen : " + screen);
        compareText(screen,loc,text);
        MobileApp.logger.log(LogStatus.PASS, "Verify label "+ text +" in screen : " + screen);

    }

    @When("^Click on Add Task Button$")
    public void click_on_Add_Task_Button() throws Throwable {
        long timeStamp = new Date().getTime();
        System.out.println("Click on Add Task button...");
        clickOn("PendingTasks","addTask");
        MobileApp.logger.log(LogStatus.PASS, "Clicked on Add Task Button");
    }

    @Then("^Provide task data as (.*)$")
    public void provide_task_data_as_Automation_Created_Task(String task) throws Throwable {
        long timeStamp = new Date().getTime();
        MobileApp.GLOBAL_VARIABLE.put("taskTimeStamp", ""+timeStamp);
        clearText("NewTodo","taskNameTextEdit");
        enterText("NewTodo","taskName",task + timeStamp);
        System.out.println("Entered Text : "+task + timeStamp);
        MobileApp.logger.log(LogStatus.PASS, "Entered Task Data");
//        hidekeyboard();
    }

    @Then("^Provide task date as (\\d+)\\.(\\d+)\\.(\\d+)$")
    public void provide_task_date_as(int arg1, int arg2, int arg3) throws Throwable {
        long timeStamp = new Date().getTime();
        System.out.println("------------- Enter Task Date ----------");
        wait(Global.minWait);
        clickOn("NewTodo","dueDate");
        System.out.println("------Day-------");
        selectDayOnNativeCalendar("Calendar","day",new Integer[]{arg1, arg2, arg3});
        clickOn("Calendar","okButton");
        MobileApp.logger.log(LogStatus.PASS, "Entered Task Date");
    }

    @Then("^Click Save Button$")
    public void click_Save_Button() throws Throwable {
        long timeStamp = new Date().getTime();
        clickOn("NewTodo","saveButton");
        System.out.println("Task Saved...");
        MobileApp.logger.log(LogStatus.PASS, "Clicked on Save Button");
    }

    @Then("^Newly created task (.*) should present in Pending Tasks list$")
    public void newly_created_task_Automation_Created_Task_should_present_in_Pending_Tasks_list(String taskName) throws Throwable {
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        checkForNewlyCreatedTask(taskName);
        MobileApp.logger.log(LogStatus.PASS, "Checking for the Newly created task");
    }

    @When("^Click on task (.*)$")
    public void click_on_task(String taskName) throws Throwable {
        long timeStamp = new Date().getTime();
        System.out.println("Clicking on Task....");
        clickOnTask(taskName);
        MobileApp.logger.log(LogStatus.PASS, "Click on Task");
    }

    @Then("^Clear task name$")
    public void clear_task_name() throws Throwable {
        long timeStamp = new Date().getTime();
        System.out.println("Clearing Text....");
        clearText("NewTodo","taskNameTextEdit");

    }

    @When("^click on Surveys option from the Home screen$")
    public void click_on_Surveys_option_from_the_Home_screen() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Click on Surveys Section in app");
        clickOn("HomePage","surveysMenu");

    }

    @Then("^verify Burger menu icon should be present in Surveys screen$")
    public void verify_Burger_menu_icon_should_be_present_in_Surveys_screen() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Verify HamBurger menu icon in Surveys screen");
        isPresented("Surveys", "hamburgerMenu");
    }

    @Then("^verify Home icon should be present in Surveys screen$")
    public void verify_Home_icon_should_be_present_in_Surveys_screen() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Verify Home icon in Surveys screen");
        isPresented("Surveys", "homeIcon");
    }

    @Then("^Edit task as (.*)$")
    public void edit_task_as_Automation_Task_Edited(String edit) throws Throwable {
        long timeStamp = new Date().getTime();
        Global global = new Global();
        edit = edit + timeStamp();
        global.editTaskName = edit;
//        clearText("NewTodo","taskNameTextEdit");
        System.out.println("Entering Text in Task Name field.");
        enterText("NewTodo", "taskName", Global.editTaskName);
        System.out.println(readText("NewTodo", "taskName"));
    }

    @Then("^Check the checkbox to move (.*) to completed screen$")
    public void check_the_checkbox_to_move_Automation_Task_Created_to_completed_screen(String taskName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Clicking Check box to Move task to Completed screen");
        checkCheckBoxToMoveTaskToCompletedScreen(taskName);
    }

    @When("^Swipe on Tasks Menu option$")
    public void swipe_on_Tasks_Menu_option() throws Throwable {
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Swipe on Tasks Menu option from Landing screen..");
        if (MobileApp.androidPlatform) {
            swipe("HomePage", "addTasksIcon", "tasksMenu");
        }
        else{

            System.out.println("For IOS not able to automate, hence it moved to Manual in case of IOS");
//            JavascriptExecutor js = (JavascriptExecutor) MobileApp.getDriver();
//            HashMap scrollObject = new HashMap(); scrollObject.put("direction", "left");
//            scrollObject.put("xpath", "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[1]");
//            System.out.println(scrollObject.values());
//            System.out.println(MobileApp.getDriver().findElementByXPath("//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[1]").getText());
//            js.executeScript("mobile: swipe", scrollObject);
        }
    }

    @Then("^Verify add task button should not be present$")
    public void verify_Add_Task_button_is_displayed_on_Home_screen_or_not() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Verify Add Task button is displaying on Home screen or not..");
        wait(Global.minWait);
        isPresented("HomePage", "addTasksIcon");
    }

    @Then("^Click on Add Task Icon$")
    public void click_on_Add_Task_Icon() throws Throwable {
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Clicking on Add Task Icon.");
        clickOn("HomePage","addTasksIcon");

    }

    @Then("^Click on Completed Tab$")
    public void click_on_Completed_Tab() throws Throwable {
        long timeStamp = new Date().getTime();
        System.out.println("Clicking on Completed Tab");
        wait(Global.minWait);
        clickOn("PendingTasks", "completedTab");
    }

    @Then("^Check the checkbox to move (.*) to pending screen$")
    public void check_the_checkbox_to_move_Automation_Task_to_pending_screen(String taskName) throws Throwable {

        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Check Check box to Move task to Pending screen");
        wait(Global.minWait);
        clickCheckBoxToMoveTaskToPendingScreen(taskName);
    }

    @Then("^Click on Pending Tab$")
    public void click_on_Pending_Tab() throws Throwable {
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("PendingTasks", "pendingTab");
    }

    @Then("^Verify (.*) moved to completed screen or not$")
    public void verify_Automation_Created_moved_to_completed_screen_or_not(String taskName) throws Throwable {
                long timeStamp = new Date().getTime();

        wait(Global.minWait);
        taskMovedToCompletedScreenOrNot(taskName);
    }

    @Then("^Verify (.*) moved to Pending screen or not$")
    public void verify_Automation_Task_moved_to_Pending_screen_or_not(String taskName) throws Throwable {
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        verifyTaskMovedToPendingScreenOrNot(taskName);
    }


    @Then("^Click on Home icon from Header$")
    public void click_on_Home_icon_from_Header() throws Throwable {
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Click on HOME icon from header..");
        clickOn("HomePage","homeIcon");
    }

    @Then("^Click App Back Button$")
    public void click_App_Back_Button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Click on App Back button.");
        clickOn("NewTodo", "appBackButton");
    }

    @Then("^Click on Yes button associated with the aleart Message$")
    public void click_on_Yes_button_associated_with_the_aleart_Message() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Accept Alert");
        clickOn("NewTodo","acceptAlert");
    }

    @Then("^Click on No button associated with the aleart Message$")
    public void click_on_No_button_associated_with_the_aleart_Message() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Dismiss Alert");
        clickOn("NewTodo", "dismissAlert");
    }

}
