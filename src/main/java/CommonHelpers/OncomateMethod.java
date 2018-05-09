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
public class OncomateMethod extends Utility {

    public static void loginIntoOncomate(Patient patient) {

        AppiumDriver driver = null;
        driver = MobileApp.getDriver();
        try {
            System.out.println("Login into Oncomate App");
            wait(Global.minWait);
            //explicitWait(driver,"//android.widget.Button[@index=\"1\"]",40000);
            checkAcceptPopUP();
            driver.findElement(By.xpath("//android.widget.EditText[@class=\"android.widget.EditText\"]")).sendKeys(patient.getPid());
            ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.ENTER);
            driver.findElementByXPath("//android.widget.Button[@class=\"android.widget.Button\"]").click();
            wait(Global.minWait);
            //explicitWait(pre_req_driver,pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_userid_inputtext\"]")),40000);
            driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_userid_inputtext\"]")).sendKeys(patient.getUsername());
            ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.ENTER);
            driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_password_inputtext\"]")).sendKeys(patient.getPassword());
            ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.ENTER);
            driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"cred_sign_in_button\"]")).click();
            wait(Global.medWait);
            //explicitWait(pre_req_driver,pre_req_driver.get().findElement(By.className("android.view.View")),40000);
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
        System.out.println("Set Pin for Oncomate : " + pin);
        String command = "adb shell input text \""+pin+"\"" ;
        Process result ;
        try{
            result = Runtime.getRuntime().exec(command);
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
        System.out.println("Confirm Pin for Oncomate : " + pin);
        String command = "adb shell input text \""+pin+"\"" ;
        Process result ;
        try{
            result = Runtime.getRuntime().exec(command);
            wait(Global.minWait);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void launchAppUsingCMD(String ulr) {
        System.out.println("Launch App using url : " + ulr);
        String command = "adb shell am start -a android.intent.action.VIEW -d " + ulr ;
        Process result ;
        try{
            result = Runtime.getRuntime().exec(command);
            wait(Global.minWait);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void confirmPinConformationMessage() {
        AppiumDriver driver = null;
        driver = MobileApp.getDriver();
        System.out.println("Click CONTINUE in conformation page");
        try{
            driver.findElement(By.xpath("(//android.widget.TextView[@text='CONTINUE'])")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void skipTnC() {
        AppiumDriver driver = null;
        driver = MobileApp.getDriver();
        System.out.println("Click SKIP in T&C page");
        try{
            checkAcceptPopUP();
            driver.findElement(By.xpath("(//android.widget.TextView[@text='SKIP'])")).click();
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
