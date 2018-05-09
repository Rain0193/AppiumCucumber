package CommonHelpers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import models.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;

import static Utils.MobileApp.pre_req_driver;

/**
 * Created by gjx2998 on 16/02/2018.
 */
public class BrowserMethod {



    public static void runPreReqSteps() {

        pre_req_driver.get().get("https://client-e2e.hdev.in");
        Actions ac = new Actions(pre_req_driver.get());

        try {
            Boolean isElementPresent = pre_req_driver.get().findElementsByXPath("//android.widget.Button[contains(@resource-id,'user-informations-button-exclude')]").size() > 0;
            if(!isElementPresent){
                System.out.println("Logging in...");

                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'i0116')]")).sendKeys("oncologist@hdev.in");
                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[contains(@resource-id,'idSIButton9')]")).click();
                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'i0118')]")).sendKeys("Onc0l0g@st");
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[contains(@text,'Sign in')]")).click();
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Boolean isStaySignInPresent = pre_req_driver.get().findElementsByXPath("//android.widget.Button[contains(@resource-id,'idBtn_Back')]").size() > 0;

                if(isStaySignInPresent){
                    System.out.println("exist");
                    pre_req_driver.get().findElement(By.xpath("//android.widget.Button[contains(@resource-id,'idBtn_Back')]")).click();
                }else{
                    System.out.println("not exist");
                }

                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ac.moveToElement(pre_req_driver.get().findElement(By.xpath("//android.view.MenuItem[contains(@text,'Tools')]")));
                pre_req_driver.get().findElement(By.xpath("//android.view.MenuItem[contains(@text,'Tools')]")).click();
                pre_req_driver.get().findElement(By.xpath("//android.view.MenuItem[contains(@text,'Register patient')]")).click();
                pre_req_driver.get().findElement(By.xpath("//android.view.View[contains(@text,'First name')]")).sendKeys("fnTest1"+new Date().getTime());
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);
                pre_req_driver.get().findElement(By.xpath("//android.view.View[contains(@text,'Last name')]")).sendKeys("lnTest1"+new Date().getTime());
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);
                pre_req_driver.get().findElement(By.xpath("//android.view.View[contains(@text,'Date of birth(yyyy-mm-dd)')]")).sendKeys("1990-09-09");
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

                pre_req_driver.get().findElement(By.xpath("//android.widget.ListView[contains(@text,'Sex')]")).sendKeys("Male");
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);
                pre_req_driver.get().findElement(By.xpath("//android.view.View[contains(@text,'Patient ID')]")).sendKeys("pTest1"+new Date().getTime());
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[contains(@text,'Add')]")).click();

            } else {
                ac.moveToElement(pre_req_driver.get().findElement(By.xpath("//android.view.MenuItem[contains(@text,'Tools')]")));

                System.out.println("Already logged in...");
                pre_req_driver.get().findElement(By.xpath("//android.view.MenuItem[contains(@text,'Tools')]")).click();
                pre_req_driver.get().findElement(By.xpath("//android.view.MenuItem[contains(@text,'Register patient')]")).click();
                pre_req_driver.get().findElement(By.xpath("//android.view.View[contains(@text,'First name')]")).sendKeys("fnTest1"+new Date().getTime());
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);
                pre_req_driver.get().findElement(By.xpath("//android.view.View[contains(@text,'Last name')]")).sendKeys("lnTest1"+new Date().getTime());
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);
                pre_req_driver.get().findElement(By.xpath("//android.view.View[contains(@text,'Date of birth(yyyy-mm-dd)')]")).sendKeys("1990-09-09");
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

                pre_req_driver.get().findElement(By.xpath("//android.widget.ListView[contains(@text,'Sex')]")).sendKeys("Male");
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);
                pre_req_driver.get().findElement(By.xpath("//android.view.View[contains(@text,'Patient ID')]")).sendKeys("pTest1"+new Date().getTime());
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[contains(@text,'Add')]")).click();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void completeSignUp(String patient) {

        pre_req_driver.get().get("https://portal-qanightly.hdev.in");
        pre_req_driver.get().get("https://portal-qanightly.hdev.in");
        pre_req_driver.get().get("https://portal-qanightly.hdev.in");
        Actions ac = new Actions(pre_req_driver.get());
        WebDriverWait wait = new WebDriverWait(pre_req_driver.get(), 8);


        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Boolean isElementPresent = pre_req_driver.get().findElementsByXPath("//android.view.View[@resource-id=\"use_another_account_link\"]").size() > 0;
            if(isElementPresent){
                System.out.println("Logging in use_another_account_link...");


                pre_req_driver.get().findElementByXPath("//android.view.View[@resource-id=\"use_another_account_link\"]").click();
                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_userid_inputtext\"]")).clear();
                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_userid_inputtext\"]")).sendKeys(patient);
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_password_inputtext\"]")).click();
                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_password_inputtext\"]")).clear();
                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_password_inputtext\"]")).sendKeys("H@lo2017");
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@resource-id=\"cred_sign_in_button\"]")).click();

                Thread.sleep(15000);


                Boolean isSavePassDisplayed = pre_req_driver.get().findElementsByXPath("//android.widget.Button[@resource-id=\"com.android.chrome:id/button_secondary\"]").size() > 0;

                if(isSavePassDisplayed){
                    pre_req_driver.get().findElementByXPath("//android.widget.Button[@resource-id=\"com.android.chrome:id/button_secondary\"]").click();
                }

                Boolean isTCDisplayed = pre_req_driver.get().findElementsByXPath("//android.widget.Button[@resource-id=\"btnAgreeTermsAndConditions\"]").size() > 0;

                if(isTCDisplayed){
                    pre_req_driver.get().findElementByXPath("//android.widget.Button[@resource-id=\"btnAgreeTermsAndConditions\"]").click();
                    Thread.sleep(15000);

                    pre_req_driver.get().findElementByXPath("//android.widget.Button[@text=\"Close\"]").click();
                }


                Boolean isNewLandingPage = pre_req_driver.get().findElementsByXPath("//android.view.View[@resource-id=\"view-todo\"]").size() > 0;

                if(isNewLandingPage){
                    pre_req_driver.get().findElementByXPath("//android.view.View[@resource-id=\"view-todo\"]").click();
                }

                Thread.sleep(5000);


                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@resource-id=\"mobile-menu\"]")).click();
                Thread.sleep(5000);

                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@resource-id=\"mobileapp-link\"]")).click();
                Thread.sleep(5000);



                Boolean allowPopUp = pre_req_driver.get().findElementsByXPath("//android.widget.Button[@resource-id=\"com.android.chrome:id/button_primary\"]").size() > 0;

                if(allowPopUp){
                    pre_req_driver.get().findElementByXPath("//android.widget.Button[@resource-id=\"com.android.chrome:id/button_primary\"]").click();
                }
                Thread.sleep(5000);


                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@index=\"0\"]")).click();

                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@NAF=\"true\"]")).sendKeys("PID51-1000");
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);
                Thread.sleep(8000);



                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_userid_inputtext\"]")).sendKeys(patient);
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_password_inputtext\"]")).sendKeys("H@lo2017");
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@resource-id=\"cred_sign_in_button\"]")).click();

                Thread.sleep(15000);
            } else {
                System.out.println("Logging in...");


                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_userid_inputtext\"]")).clear();
                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_userid_inputtext\"]")).sendKeys(patient);
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_password_inputtext\"]")).clear();
                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_password_inputtext\"]")).sendKeys("H@lo2017");
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@resource-id=\"cred_sign_in_button\"]")).click();

                Thread.sleep(15000);

                Boolean isSavePassDisplayed = pre_req_driver.get().findElementsByXPath("//android.widget.Button[@resource-id=\"com.android.chrome:id/button_secondary\"]").size() > 0;

                if(isSavePassDisplayed){
                    pre_req_driver.get().findElementByXPath("//android.widget.Button[@resource-id=\"com.android.chrome:id/button_secondary\"]").click();
                }

                Boolean isTCDisplayed = pre_req_driver.get().findElementsByXPath("//android.widget.Button[@resource-id=\"btnAgreeTermsAndConditions\"]").size() > 0;

                if(isTCDisplayed){
                    pre_req_driver.get().findElementByXPath("//android.widget.Button[@resource-id=\"btnAgreeTermsAndConditions\"]").click();
                    Thread.sleep(15000);

                    pre_req_driver.get().findElementByXPath("//android.widget.Button[@text=\"Close\"]").click();
                }

                Boolean isNewLandingPage = pre_req_driver.get().findElementsByXPath("//android.view.View[@resource-id=\"view-todo\"]").size() > 0;

                if(isNewLandingPage){
                    pre_req_driver.get().findElementByXPath("//android.view.View[@resource-id=\"view-todo\"]").click();
                }

                Thread.sleep(5000);

                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@resource-id=\"mobile-menu\"]")).click();
                Thread.sleep(5000);

                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@resource-id=\"mobileapp-link\"]")).click();
                Thread.sleep(5000);

                Boolean allowPopUp = pre_req_driver.get().findElementsByXPath("//android.widget.Button[@resource-id=\"com.android.chrome:id/button_primary\"]").size() > 0;

                if(allowPopUp){
                    pre_req_driver.get().findElementByXPath("//android.widget.Button[@resource-id=\"com.android.chrome:id/button_primary\"]").click();
                }
                Thread.sleep(5000);

                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@index=\"0\"]")).click();


                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@NAF=\"true\"]")).sendKeys("PID51-1000");
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);
                Thread.sleep(8000);



                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_userid_inputtext\"]")).sendKeys(patient);
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

                pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_password_inputtext\"]")).sendKeys("H@lo2017");
                ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@resource-id=\"cred_sign_in_button\"]")).click();

                Thread.sleep(15000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loginToPatientPortalMobile(String env, Patient patient){
      //  pre_req_driver.get().get("https://portal-" +env+ ".hdev.in");
        pre_req_driver.get().get(env);
        Actions ac = new Actions(pre_req_driver.get());
        WebDriverWait wait = new WebDriverWait(pre_req_driver.get(), 8);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Boolean isElementPresent = pre_req_driver.get().findElementsByXPath("//android.view.View[@resource-id=\"use_another_account_link\"]").size() > 0;
            if(isElementPresent) {
                System.out.println("Logging in use_another_account_link...");
                pre_req_driver.get().findElementByXPath("//android.view.View[@resource-id=\"use_another_account_link\"]").click();
            }else{
                System.out.println("Logging in...");
            }
            pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_userid_inputtext\"]")).clear();
            pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_userid_inputtext\"]")).sendKeys(patient.getUsername());
            ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

            pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_password_inputtext\"]")).clear();
            pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_password_inputtext\"]")).sendKeys(patient.getPassword());
            ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

            pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@resource-id=\"cred_sign_in_button\"]")).click();

            Thread.sleep(15000);

            Boolean isSavePassDisplayed = pre_req_driver.get().findElementsByXPath("//android.widget.Button[@resource-id=\"com.android.chrome:id/button_secondary\"]").size() > 0;
            if(isSavePassDisplayed){
                pre_req_driver.get().findElementByXPath("//android.widget.Button[@resource-id=\"com.android.chrome:id/button_secondary\"]").click();
            }

            Boolean isTCDisplayed = pre_req_driver.get().findElementsByXPath("//android.widget.Button[@resource-id=\"btnAgreeTermsAndConditions\"]").size() > 0;

            if(isTCDisplayed){
                pre_req_driver.get().findElementByXPath("//android.widget.Button[@resource-id=\"btnAgreeTermsAndConditions\"]").click();
                Thread.sleep(1000);
                pre_req_driver.get().findElementByXPath("//android.widget.Button[@text=\"Close\"]").click();
            }

            Boolean isNewLandingPage = pre_req_driver.get().findElementsByXPath("//android.view.View[@resource-id=\"view-todo\"]").size() > 0;

            if(isNewLandingPage){
                System.out.println("New landing Page");
                pre_req_driver.get().findElementByXPath("//android.view.View[@resource-id=\"view-todo\"]").click();
                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@resource-id=\"mobile-menu\"]")).click();
                Thread.sleep(1000);

                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@resource-id=\"mobileapp-link\"]")).click();
                Thread.sleep(3000);
            }else{
                System.out.println("Old landing Page");
                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@resource-id=\"mobile-menu\"]")).click();
                Thread.sleep(1000);
                pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@resource-id=\"mobileapp-link\"]")).click();
                Thread.sleep(5000);
            }

            Boolean allowPopUp = pre_req_driver.get().findElementsByXPath("//android.widget.Button[@resource-id=\"com.android.chrome:id/button_primary\"]").size() > 0;

            if(allowPopUp){
                pre_req_driver.get().findElementByXPath("//android.widget.Button[@resource-id=\"com.android.chrome:id/button_primary\"]").click();
                System.out.println("Pop up allowed in chrome.. ");
            }
            Thread.sleep(5000);

            pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@index=\"0\"]")).click();


            pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@class=\"android.widget.EditText\"]")).sendKeys(patient.getPid());
            ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);
            pre_req_driver.get().findElementByXPath("//android.widget.Button[@class=\"android.widget.Button\"]").click();
            Thread.sleep(8000);

            pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_userid_inputtext\"]")).sendKeys(patient.getUsername());
            ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

            pre_req_driver.get().findElement(By.xpath("//android.widget.EditText[@resource-id=\"cred_password_inputtext\"]")).sendKeys(patient.getPassword());
            ((AndroidDriver) pre_req_driver.get()).pressKeyCode(AndroidKeyCode.BACK);

            pre_req_driver.get().findElement(By.xpath("//android.widget.Button[@resource-id=\"cred_sign_in_button\"]")).click();

            Thread.sleep(15000);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void openOncomateWithURL(String url){
        try {
            pre_req_driver.get().get(url);

            Boolean playStore = pre_req_driver.get().findElementsByXPath("//android.widget.Button[@resource-id=\"com.android.chrome:id/button_primary\"]").size() > 0;

            if(playStore){
                System.out.println("Click Open in Play Store");
                pre_req_driver.get().findElementByXPath("//android.widget.Button[@text='OPEN']").click();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
