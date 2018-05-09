package CommonHelpers;

import Utils.Global;
import Utils.MobileApp;
import Utils.Utility;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import models.Patient;
import org.openqa.selenium.By;

/**
 * Created by gjx2998 on 16/02/2018.
 */
public class OncomateIOS extends Utility {

    public static void loginIntoOncomate(Patient patient) {

        AppiumDriver iosDriver = null;
        iosDriver = MobileApp.getDriver();
        try {
            System.out.println("Login into Oncomate App with pid"+patient.getPid());
            iosDriver.findElementByAccessibilityId("Enter your Patient ID to configure the application").sendKeys(patient.getPid());
            iosDriver.findElementByAccessibilityId("Proceed").click();
            System.out.println("Entered Patient ID----");

            iosDriver.findElementByAccessibilityId("User account").sendKeys(patient.getUsername());
            iosDriver.findElementByAccessibilityId("Password").sendKeys(patient.getPassword());
            iosDriver.findElementByAccessibilityId("Sign in").click();
            iosDriver.findElementByAccessibilityId("Sign in").click();
            System.out.println("Logged In----");

            wait(Global.medWait);
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public static void setPin(int[] pin) {
        AppiumDriver driver = null;
        driver = MobileApp.getDriver();
        try{
            int j = 0 ;
            for(int i : pin) {
                System.out.println("(//android.widget.TextView[@text='―'])["+ j +"]");
                //pre_req_driver.get().findElement(By.xpath("(//android.widget.TextView[@text='" + i + "'])")).click();
                driver.findElement(By.xpath("(//android.widget.TextView[@text='―'])["+ j +"]")).sendKeys(String.valueOf(i));
                j++ ;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static void confirmPin(int[] pin) {
        AppiumDriver driver = null;
        driver = MobileApp.getDriver();
        System.out.println("Confirm Pin for Oncomate App");
        try{
            for(int i : pin)
                driver.findElement(By.xpath("(//android.widget.TextView[@text='" + pin[i] + "'])")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void setPinFromCmd(String pin) {
        AppiumDriver iosDriver = MobileApp.getDriver();

        System.out.println("Set Pin for Oncomate : " + pin);

        try{
            iosDriver.findElementByAccessibilityId(""+pin.charAt(0)).click();
            iosDriver.findElementByAccessibilityId(""+pin.charAt(1)).click();
            iosDriver.findElementByAccessibilityId(""+pin.charAt(2)).click();
            iosDriver.findElementByAccessibilityId(""+pin.charAt(3)).click();
            wait(Global.minWait);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void unlockAppPin(String pin) {
        System.out.println("Pin unlock : " + pin);
        try {
            MobileApp.getDriver().findElementByAccessibilityId("PinTextInput").sendKeys(pin);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    public static void confirmPinFromCmd(String pin) {
        AppiumDriver iosDriver = MobileApp.getDriver();

        System.out.println("Set Pin for Oncomate : " + pin);

        try{
            iosDriver.findElementByAccessibilityId(""+pin.charAt(0)).click();
            iosDriver.findElementByAccessibilityId(""+pin.charAt(1)).click();
            iosDriver.findElementByAccessibilityId(""+pin.charAt(2)).click();
            iosDriver.findElementByAccessibilityId(""+pin.charAt(3)).click();
            wait(Global.minWait);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void launchAppWithURL(String url) {

        System.out.println("Loading the setup url : "+url);
        try{
            MobileApp.getDriver().get(url);
            boolean allow = MobileApp.getDriver().findElementsByAccessibilityId("Allow").size() > 0;
            if(allow){
                MobileApp.getDriver().findElementByAccessibilityId("Allow").click();
                MobileApp.getDriver().findElementByAccessibilityId("OK").click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void confirmPinConformationMessage() {
        AppiumDriver iosDriver = null;
        iosDriver = MobileApp.getDriver();
        try{
            iosDriver.findElementByAccessibilityId("Continue Button").click();
//            iosDriver.findElementByAccessibilityId("Skip tour button").click();
//            System.out.println("Home Screen Visible..");
            wait(Global.minWait);
        } catch (Exception e) {
            System.out.println("Warning : Exception in Skip button as Calender pop up not came..");
            //  e.printStackTrace();
        }
    }


    public static void skipTnC() {
        AppiumDriver iosDriver = null;
        iosDriver = MobileApp.getDriver();
        try{
            iosDriver.findElementByAccessibilityId("accessibility.common.continueButton").click();
//            iosDriver.findElementByAccessibilityId("accessibility.tour.skip").click();
            iosDriver.findElementByAccessibilityId("accessibility.home.addTaskButton").click();
            System.out.println("Open tasks");
            wait(Global.minWait);
        } catch (Exception e) {
            System.out.println("Warning : Exception in Skip button as Calender pop up not came..");
            //  e.printStackTrace();
        }
    }


    public static void checkAcceptPopUP() {
        AppiumDriver driver = null;
        driver = MobileApp.getDriver();
        System.out.println("Check Pop up");
        try{
            Boolean playStore = driver.findElementsByXPath("//android.widget.Button[@index=\"1\"]").size() > 0;
            if(playStore){
                driver.findElement(By.xpath("//android.widget.Button[@index=\"1\"]")).click();
                System.out.println("Accept pop up");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
