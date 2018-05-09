package StepDefinitions;

import Utils.Global;
import Utils.MobileApp;
import Utils.Utility;
import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Messages extends Utility {


    @When("^Click on messages tab on landing page$")
    public void test0() throws Throwable {
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("HomePage", "messagesMenu");
        MobileApp.logger.log(LogStatus.PASS, "Clicked on Messages Option from Home screen.");
    }

    @When("^Click on Plus button on message screen$")
    public void test1() throws Throwable {
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "addNewMessageIcon");
        MobileApp.logger.log(LogStatus.PASS, "Clicked on Plus Icon on Messages screen to navigate to New Message screen..");
    }

    @When("^Enter subject (.*) for new message$")
    public void test2(String subject) throws Throwable {
        long timeStamp = new Date().getTime();
        MobileApp.GLOBAL_VARIABLE.put("messageTimeStamp", ""+timeStamp);
        wait(Global.minWait);
        enterText("Messages", "subjectText", subject +timeStamp);
        MobileApp.logger.log(LogStatus.PASS, "Entered Message subject");
    }

    @Then("^Verify the display of warning message$")
    public void verify_the_display_of_warning_message() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        isDisplayed(By.xpath("//android.widget.TextView[@text='The subject cannot exceed 100 characters']"));
    }

    @When("^Enter subject (.*) (\\d+) times\\.$")
    public void enter_subject_Test_times(String  subject, int noofTimes) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        for(int Iterator =0; Iterator<noofTimes;Iterator++){
            enterText("Messages", "subjectText", subject);
        }
        System.out.println("Entered 100 Characters in Subject line. ");
        MobileApp.logger.log(LogStatus.PASS, "Entered Message subject");
    }

    @Then("^Click on Sent Message$")
    public void click_on_Sent_Message() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("^Verify appointment request sent for (.*) or not$")
    public void verify_appointment_request_sent_for_Automation_Created_Appointment_or_not(String Reason) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String Message = readTextFromElement("Messages", "messageText");
        if(Message.contains(Reason)){
            System.out.println("Appointment Request sent");
        }
        else {
            System.out.println("Appointment Request not sent");
        }
    }

    @Then("^Enter subject (.*) for message$")
    public void enter_subject_T_for_message(String subject) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        enterText("Messages", "subjectText", subject);
        System.out.println("Entered 101 Character in Subject line. ");
    }

    @When("^Enter body (.*) for new message$")
    public void test3(String body) throws Throwable {
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        enterText("Messages", "bodyText", body);
        MobileApp.logger.log(LogStatus.PASS, "Entered Message body.");
    }

    @When("^Click on add recipient for new message$")
    public void test4() throws Throwable {
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "addToRecipient");
        MobileApp.logger.log(LogStatus.PASS, "Click on Add To Receipient icon");
    }


    @When("^Select recipient (.*) for new message$")
    public void test5(String recipient) throws Throwable {
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        setAttributeFor("Messages", "addToRecipientName", recipient);
        clickOn("Messages", "addToRecipientName");
    }

    @Then("^Click on Add Subject button to add message subject$")
    public void click_on_Add_Subject_button_to_add_message_subject() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "addSubjectButton");
    }

    @When("^Click on Sent messages Tab$")
    public void click_on_Sent_messages_Tab() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "sentMessagesTab");
    }

    @When("^Verify Automation Created Message is displaying in sent messages tab or not$")
    public void verify_Automation_Created_Message_is_displaying_in_sent_messages_tab_or_not() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        String test = MobileApp.getDriver().findElementByXPath("//android.widget.TextView[contains(@text,'"+MobileApp.GLOBAL_VARIABLE.get("messageTimeStamp")+"')]").getText();
        System.out.println("Test"+test);
        if(readElements("Messages","messagesList").toString().contains(test)){
            System.out.println("Recently sent message is displaying in sent messages tab");
        }else{
            System.out.println("Recently sent message is not displaying in sent messages tab");
        }
    }

    @When("^Swipe on Messages Menu option$")
    public void swipe_on_Messages_Menu_option() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        swipe("HomePage","addMessagesIcon","messagesMenu");

    }

    @Then("^Click on Add Message Icon$")
    public void click_on_Add_Message_Icon() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("HomePage","addMessagesIcon");

    }


    @When("^Click on add CC recipient for new message$")
    public void click_on_add_CC_recipient_for_new_message() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "addCCRecipient");
    }


    @When("^Click on Done button$")
    public void click_on_Done_button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "doneButton");
    }

    @Then("^Click on Back Button$")
    public void click_on_Back_Button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "backButton");
    }

    @When("^Click Send button for new message$")
    public void test6() throws Throwable {
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "sendButton");

    }
    @Then("^Click on Ok button Associated with the Alert Message$")
    public void click_on_Ok_button_Associated_with_the_Alert_Message() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "okButton");
    }

    @Then("^Click on Send button to send message without subject$")
    public void click_on_Send_button_to_send_message_without_subject() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "sendButtonForWithoutSubject");

    }

    @Then("^Click on Send button to send message without Message$")
    public void click_on_Send_button_to_send_message_without_Message() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "sendButtonForWithoutMessage");

    }

    @Then("^Click on Message$")
    public void click_on_Message() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        List<MobileElement> ele = MobileApp.getDriver().findElementsByXPath("//android.widget.ScrollView//android.view.ViewGroup//android.view.ViewGroup//android.view.ViewGroup");
        ele.get(0).click();
    }


    @Then("^Click on Reply Message Button$")
    public void click_on_Reply_Message_Button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "replyMessageButton");
    }

    @Then("^Click on Reply All Button$")
    public void click_on_Reply_All_Button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "replyAllMessageButton");

    }

    @Then("^Click on Forward Button$")
    public void click_on_Forward_Button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "forwardMessageButton");

    }

    @Then("^Enter (.*) for the message$")
    public void enter_Automation_Reply_Message_for_the_message(String reply) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        enterText("Messages", "bodyText", reply);
    }

    @Then("^Click on Add Content button to add the Message content$")
    public void click_on_Add_Content_button_to_add_the_Message_content() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "addContentButton");
    }

    @When("^Click on Add Attachment Icon\\.$")
    public void click_on_Add_Attachment_Icon() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "addAttachmentIcon");
    }


    @Then("^Verify the Sender Details$")
    public void verify_the_Sender_Details() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Sender Name     :"+readTextFromElement("Messages", "senderName"));
    }

    @Then("^Verify Message Timestamp$")
    public void verify_Message_Timestamp() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Message Timestamp     :"+readTextFromElement("Messages", "messageTimestamp"));
    }

    @Then("^Verify Subject$")
    public void verify_Subject() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Subject      :"+readTextFromElement("Messages", "messageSubject"));
    }

    @Then("^Verify More Details link is displayed or not$")
    public void verify_More_Details_link_is_displayed_or_not() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println("Clicking on :"+readTextFromElement("Messages", "moreDetails"));
    }

    @Then("^Click on More Details link$")
    public void click_on_More_Details_link() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "moreDetails");
    }

    @Then("^Verify To Receipient is displayed or not$")
    public void verify_To_Receipient_is_displayed_or_not() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.print(readTextFromElement("Messages", "toReceipient"));
        System.out.println(readTextFromElement("Messages", "toReceipientName"));
    }

    @Then("^Verify Less Details link is displayed or not\\.$")
    public void verify_Less_Details_link_is_displayed_or_not() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        System.out.println(readTextFromElement("Messages", "lessDetails"));
    }

    @Then("^Click on Less Details link$")
    public void click_on_Less_Details_link() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        long timeStamp = new Date().getTime();
        wait(Global.minWait);
        clickOn("Messages", "lessDetails");
    }

}
