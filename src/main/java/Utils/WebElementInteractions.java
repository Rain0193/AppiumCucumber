package Utils;

import com.relevantcodes.extentreports.LogStatus;
import gherkin.lexer.Th;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebElementInteractions extends Assert {

    String filePath = System.getProperty("user.dir") + File.separator + "target" + File.separator + "screenshots";

    protected void clickOn(String screen, String locatorName, AppiumDriver... targetDriver) throws Throwable {
        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();

        try {
            System.out.println("Clicking on "+locatorName);
            MobileApp.MobileScreens.get(screen).getElement(locatorName, driver).click();
            MobileApp.logger.log(LogStatus.PASS, "Clicked on " + locatorName + " in " + screen);
        } catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Failed to click element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }
    }

    protected void clickCheckBoxToMoveTaskToPendingScreen(String taskName){
        if(MobileApp.androidPlatform) {
            MobileApp.getDriver().findElementByXPath("//android.widget.TextView[contains(@text,'" + taskName + MobileApp.GLOBAL_VARIABLE.get("taskTimeStamp") + "')]/../..//android.view.ViewGroup//android.widget.TextView[@text='\uF147']").click();
        }
        else{
            List <IOSElement>li = (List<IOSElement>) (MobileApp.getDriver().findElementsByXPath("//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]"));

            for(int iterator=0;iterator<li.size();iterator++)
            {

                if(li.get(iterator).getText().contains(taskName+MobileApp.GLOBAL_VARIABLE.get("taskTimeStamp"))){

                    li.get(iterator).click();
                    break;
                }
            }
        }

    }

    protected void taskMovedToCompletedScreenOrNot(String taskName) throws Throwable{
        if(MobileApp.androidPlatform) {
            String test = MobileApp.getDriver().findElementByXPath("//android.widget.TextView[contains(@text,'" + MobileApp.GLOBAL_VARIABLE.get("taskTimeStamp") + "')]").getText();
            System.out.println("Test" + test);
            if (readElements("PendingTasks", "tasksList").toString().contains(test)) {
                System.out.println("Task Moved to Completed screen...");
            } else {
                System.out.println("Task is not available in completed screen. ");
            }
        }
        else{
            List<IOSElement> li = (List<IOSElement>) (MobileApp.getDriver().findElementsByXPath("//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]"));
            for(IOSElement ele : li)
            {
                if(ele.getText().contains(taskName+MobileApp.GLOBAL_VARIABLE.get("taskTimeStamp"))){
                    System.out.println("Task Moved to Completed screen...");
                }
            }

        }
    }

    protected void checkCheckBoxToMoveTaskToCompletedScreen(String taskName){
        if(MobileApp.androidPlatform) {

            MobileApp.getDriver().findElementByXPath("//android.widget.TextView[contains(@text,'" + taskName + MobileApp.GLOBAL_VARIABLE.get("taskTimeStamp") + "')]/../..//android.view.ViewGroup//android.widget.TextView[@text='\uF219']").click();
        }
        else{
            List<IOSElement> li = (List<IOSElement>) (MobileApp.getDriver().findElementsByXPath("//XCUIElementTypeScrollView/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]"));

            for (IOSElement ele : li){
                if(ele.getText().contains(taskName+MobileApp.GLOBAL_VARIABLE.get("taskTimeStamp"))){

                    System.out.println(ele.getText());
                    ele.click();
                    break;
                }
            }
        }

    }
    protected void verifyTaskMovedToPendingScreenOrNot(String taskName) throws Throwable{
        if(MobileApp.androidPlatform) {
            String test = MobileApp.getDriver().findElementByXPath("//android.widget.TextView[contains(@text,'" + MobileApp.GLOBAL_VARIABLE.get("taskTimeStamp") + "')]").getText();
            System.out.println("Test" + test);
            if (readElements("PendingTasks", "tasksList").toString().contains(test)) {
                System.out.println("Task Moved to Pending screen...");
            } else {
                System.out.println("Task is not available in Pending screen. ");
            }
        }
        else{
            List<IOSElement> li = (List<IOSElement>) (MobileApp.getDriver().findElementsByXPath("//XCUIElementTypeScrollView/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]"));
            for(IOSElement ele : li)
            {
                if(ele.getText().contains(taskName+MobileApp.GLOBAL_VARIABLE.get("taskTimeStamp"))){
                    System.out.println("Task Moved to Pending screen..");
                }
            }

        }

    }

    protected void clickLastItemInList(String screen, String locatorName, AppiumDriver... targetDriver) throws Throwable {
        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();

        try {

//           int sizeOfList =  (MobileApp.MobileScreens.get(screen).getElement(locatorName, driver)).size();
//           System.out.println("Size-----------------------: "+sizeOfList);

            System.out.println(MobileApp.MobileScreens.get(screen).getElement(locatorName,driver).getText());
//           MobileApp.MobileScreens.get(screen).getElement(locatorName,driver).click();

            MobileApp.logger.log(LogStatus.PASS, "Clicked on " + locatorName + " in " + screen);
        } catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Failed to click element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }
    }

    protected  void swipeFromRightToLeft() throws Throwable{
        Dimension size = MobileApp.getDriver().manage().window().getSize();
        System.out.println(size);
        int startX = (int) (size.width*0.70);
        int endX = (int) (size.width*0.001);
        int startY = size.height/2;
        System.out.println("Start X : "+startX);
        System.out.println("End X :"+endX);
        System.out.println("Start Y"+startY);
        MobileApp.getDriver().swipe(startX, startY, endX, startY,3000);
    }

    protected  void swipeFromLeftToRight() throws Throwable{
        Dimension size = MobileApp.getDriver().manage().window().getSize();
        System.out.println(size);
        int startX = (int) (size.width*0.70);
        int endX = (int) (size.width*0.30);
        int startY = size.height/2;
        System.out.println("Start X : "+startX);
        System.out.println("End X :"+endX);
        System.out.println("Start Y"+startY);
        MobileApp.getDriver().swipe(endX, startY, startX, startY,3000);
    }


    protected boolean isDisplayed(By locatorKey) throws Throwable {

        try {
            MobileApp.getDriver().findElement(locatorKey);
            System.out.println("Subject limit exceeded warning message Displayed");
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("Subject limit exceeded warning message Not Displayed");
            return false;
        }

    }

    protected void isPresented(String screen, String locatorName, AppiumDriver... targetDriver) throws Throwable {
        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();

        try {
            MobileApp.MobileScreens.get(screen).getElement(locatorName, driver).isDisplayed();
            System.out.println(locatorName + " is Presented in " + screen);
            MobileApp.logger.log(LogStatus.PASS, locatorName + " is Presented in " + screen);
        } catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Failed to identify element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }
    }
    protected void enterText(String screen, String locatorName, String text, AppiumDriver... targetDriver) throws Throwable {
        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();

        try {
            MobileApp.MobileScreens.get(screen).getElement(locatorName, driver).clear();
            MobileApp.MobileScreens.get(screen).getElement(locatorName, driver).sendKeys(text);
            MobileApp.logger.log(LogStatus.PASS, "Entered text " + text + " for " + locatorName + " in " + screen);
        } catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Failed to enterText text " + text + " on element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }
    }

    protected String readTextFromElement(String screen, String locatorName, AppiumDriver... targetDriver) throws Throwable{

        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() : MobileApp.getDriver();
        String text=  MobileApp.MobileScreens.get(screen).getElement(locatorName, driver).getText();
        return  text;
    }



    protected void selectByText(String screen, String locatorName, String text, AppiumDriver... targetDriver) throws Throwable {
        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();

        try {
            MobileApp.MobileScreens.get(screen).getElement(locatorName, driver).click();
            driver.findElement(By.xpath("//*[@text='" + text + "']")).click();

            MobileApp.logger.log(LogStatus.PASS, "Selected text " + text + " for " + locatorName + " in " + screen);
        } catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Failed to select value " + text + " on element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }
    }

    protected void compareText(String screen, String locatorName,String text, AppiumDriver... targetDriver) throws Throwable {
        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();
        try {

                if (MobileApp.MobileScreens.get(screen).getElement(locatorName, driver).getText().contains(text)) {
//                    Assert.assertEquals(text,
//                            MobileApp.MobileScreens.get(screen).getElement(locatorName, driver).getText());
                    System.out.println(text +" is displayed on : " + screen);
                    MobileApp.logger.log(LogStatus.PASS, "Returned  text for locator " + locatorName + " in " + screen);
                }
        } catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            MobileApp.logger.log(LogStatus.FAIL, "Failed to read value of element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }
    }

    protected String readText(String screen, String locatorName, AppiumDriver... targetDriver) throws Throwable {
        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();
        String text = "";
        try {
            text = MobileApp.MobileScreens.get(screen).getElement(locatorName, driver).getText();
            MobileApp.logger.log(LogStatus.PASS, "Returned  text for locator " + locatorName + " in " + screen);
        } catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Failed to read value of element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }
        return text;
    }

    protected void clearText(String screen, String locatorName, AppiumDriver... targetDriver) throws Throwable {
        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();

        try {
            MobileApp.MobileScreens.get(screen).getElement(locatorName, driver).clear();
            MobileApp.logger.log(LogStatus.PASS, "Cleared Text in " + locatorName + " in " + screen);
        } catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Failed to clear text in " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }

    }

    protected ArrayList readElements(String screen, String locatorName, AppiumDriver... targetDriver) throws Throwable {
        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();

        ArrayList listOfTasks = new ArrayList();
        try {
            for (WebElement task : MobileApp.MobileScreens.get(screen).getElements(locatorName,driver)){
//                System.out.println("^^^^^^^^^^^^"+task.getText());
                listOfTasks.add(task.getText());
//                System.out.println(listOfTasks);
            }
            MobileApp.logger.log(LogStatus.PASS, "Returned  text for locator " + locatorName + " in " + screen);
        } catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Failed to read value of element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }
        return listOfTasks;
    }

    protected void swipe(String screen, String source, String destination, AppiumDriver... targetDriver) throws Throwable{
        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();

        TouchAction action = new TouchAction(driver);
        try {

            WebElement expectd = MobileApp.MobileScreens.get(screen).getElement(source,driver);
            WebElement target = MobileApp.MobileScreens.get(screen).getElement(destination,driver);
            action.longPress(expectd).moveTo(target).release().perform();
        }catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element " + source + " and " + destination + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Failed to read value of element " + source + " and " + destination + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }


    }

    protected void moveToElement(String screen,   String locatorName, AppiumDriver... targetDriver) throws Throwable{

        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();

        try {
            int temp= MobileApp.MobileScreens.get(screen).getElements(locatorName,driver).size();

            while((MobileApp.MobileScreens.get(screen).getElements(locatorName,driver)).size()>=temp){
                Dimension  size = MobileApp.getDriver().manage().window().getSize();
                int starty = (int) (size.height * 0.80);
                int endy = (int) (size.height * 0.20);
                int startx = size.width / 2;
                System.out.println("starty = " + starty + " ,endy = " + endy + " , startx = " + startx);


                MobileApp.getDriver().swipe(startx, starty, startx, endy, 3000);


//            MobileApp.getDriver().swipe(startx, endy, startx, starty, 3000);
//            Thread.sleep(2000);
            }
        } catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Failed to clear text in " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }
    }



    protected void selctElementFromList(String screen, String locatorName,  String text, AppiumDriver... targetDriver) throws Throwable{

        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();

        try {


            do{
                List<MobileElement> options = MobileApp.MobileScreens.get(screen).getElements(locatorName,driver);
                int itemsCount =  options.size();


                //List<WebElement> we = driver.findElements(By.className("android.widget.TextView"));
                if(driver.findElement(By.xpath("//android.widget.TextView[@index='0']/..")).isDisplayed()) {
                    driver.findElement(By.xpath("//android.widget.TextView[@index='0']/..")).click();
                }
                     else{
                    Dimension  size = MobileApp.getDriver().manage().window().getSize();
                    int starty = (int) (size.height * 0.80);
                    int endy = (int) (size.height * 0.20);
                    int startx = size.width / 2;
                    System.out.println("starty = " + starty + " ,endy = " + endy + " , startx = " + startx);

                        MobileApp.getDriver().swipe(startx, starty, startx, endy, 3000);
                }
            }while(true);


        } catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Failed to clear text in " + locatorName + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }
    }


    protected void scroll(String screen, String from, String to, String noofYears, AppiumDriver... targetDriver) throws Throwable {
        long timeStamp = new Date().getTime();
        TouchAction touchAction = new TouchAction((AppiumDriver)MobileApp.getDriver());
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();
        Utility util = new Utility();

        try {
            int yearCount = MobileApp.MobileScreens.get(screen).getElements(noofYears,driver).size();
            for(int iterator=0;iterator<yearCount;iterator++){
                if(MobileApp.MobileScreens.get(screen).getElement(to,driver).isDisplayed()){
                    for(int index=0;index<=6;index++) {
                        MobileApp.MobileScreens.get(screen).getElements(noofYears,driver).get(index).getText();
                    }
//                    System.out.println("------------Years-----------"+MobileApp.MobileScreens.get(screen).getElements(noofYears,driver).get(iterator).getText());
                    touchAction.longPress(MobileApp.MobileScreens.get(screen).getElement(from,driver)).moveTo(MobileApp.MobileScreens.get(screen).getElement(to,driver)).release().perform();
                }
                else{
//                (MobileApp.MobileScreens.get(screen).getElement(to,driver)).click();
                    System.out.println("qwertyu");
                }
            }

        }catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element " + to + " and " + from + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Failed to read value of element " + to + " and " + from + " in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }
    }

    protected void selectYear(String screen, String from, String to, String noofYears, int year, AppiumDriver... targetDriver) throws Throwable {
        long timeStamp = new Date().getTime();
        int year2=0;
        String year1;
        TouchAction touchAction = new TouchAction((AppiumDriver)MobileApp.getDriver());
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();
        try{
            int yearCount = MobileApp.MobileScreens.get(screen).getElements(noofYears,driver).size();
            for(int iterator=0;iterator<yearCount;iterator++){
                if(MobileApp.MobileScreens.get(screen).getElement(to,driver).isDisplayed()){
                    for(int index=0;index<=6;index++) {
                        year1 =MobileApp.MobileScreens.get(screen).getElements(noofYears,driver).get(index).getText();
                        year2=Integer.parseInt(year1);
                        if(year==year2){
                            MobileApp.MobileScreens.get(screen).getElement(noofYears,driver).click();
                        }
                        touchAction.longPress(MobileApp.MobileScreens.get(screen).getElement(from,driver)).moveTo(MobileApp.MobileScreens.get(screen).getElement(to,driver)).release().perform();
                    }
                }

            }
        }catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element in " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Failed to read value of element " + screen, MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }
    }

    protected void clickOnElement(String screen, String locatorName, String text, AppiumDriver... targetDriver){
        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();

        try {
            List<MobileElement> options = MobileApp.MobileScreens.get(screen).getElements(locatorName,driver);
            int itemsCount =  options.size();
            for(int iterator=0;iterator<=itemsCount;iterator++){
                if((options.get(iterator).getText().equals(text))){
                    options.get(iterator).click();
                }
            }
        }catch(Exception e){
        }
    }
    protected void clickOnTask(String taskName, AppiumDriver... targetDriver) throws Throwable{
        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();

        if(MobileApp.androidPlatform){
            MobileApp.getDriver().findElementByXPath("//android.widget.TextView[contains(@text,'"+taskName+MobileApp.GLOBAL_VARIABLE.get("taskTimeStamp")+"')]").click();
            System.out.println("Click On Task...");
        }
        else{
            List <IOSElement>li = (List<IOSElement>) (MobileApp.getDriver().findElementsByXPath("//XCUIElementTypeScrollView/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]"));
            li.size();
            for(int iterator=0;iterator<li.size();iterator++)
            {
                if(li.get(iterator).getText().contains(taskName+MobileApp.GLOBAL_VARIABLE.get("taskTimeStamp"))){

                    li.get(iterator).click();
                }
            }
            System.out.println("Click On Task...");
        }

    }

    protected void selectDayOnNativeCalendar(String screen, String locatorName, Integer[] day, AppiumDriver... targetDriver) throws Throwable {
        long timeStamp = new Date().getTime();


        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();


        if (MobileApp.androidPlatform) {
            System.out.println("----Android");

            try{

                System.out.println("---Select Day---");

                MobileApp.MobileScreens.get(screen).getElements(locatorName,driver).get(day[3]-1).click();

            }catch (NullPointerException e) {
                FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

                MobileApp.logger.log(LogStatus.FAIL, "Unable to find element in " +  MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
                throw e.getCause();
            } catch (Exception e) {
                FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

                MobileApp.logger.log(LogStatus.FAIL, "Failed to read value of element " + MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
                throw e.getCause();
            }
        } else {

            List<IOSElement> wheels =  (List<IOSElement>) MobileApp.getDriver().findElementsByXPath("//XCUIElementTypePickerWheel");
            System.out.println("Wheels Count: "+ wheels.size());
            wheels.get(0).sendKeys(String.valueOf(day[2]));
            wheels.get(1).sendKeys(String.valueOf(day[1]));
            wheels.get(2).setValue(String.valueOf(day[0]));

        }
    }
    protected void selectDay(String screen, String locatorName, int day, AppiumDriver... targetDriver) throws Throwable {
        long timeStamp = new Date().getTime();
        AppiumDriver driver = (targetDriver == null) ? MobileApp.getDriver() :  MobileApp.getDriver();
        try{

            System.out.println("---Select Day---");

            MobileApp.MobileScreens.get(screen).getElements(locatorName,driver).get(day-1).click();

        }catch (NullPointerException e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Unable to find element in " +  MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        } catch (Exception e) {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(filePath + File.separator + "screenshot_" + timeStamp + ".png"));

            MobileApp.logger.log(LogStatus.FAIL, "Failed to read value of element " + MobileApp.logger.addScreenCapture(filePath + File.separator + "screenshot_" + timeStamp + ".png"));
            throw e.getCause();
        }
    }

    protected void hidekeyboard(){
        AppiumDriver driver = (AppiumDriver)MobileApp.getDriver();
        driver.hideKeyboard();
    }

    public static void explicitWait(ThreadLocal<AppiumDriver> driver, String pathOfLocator, int milisecond){
        //  WebDriverWait wait;
        //  wait = new WebDriverWait(driver.get(),milisecond);
        //  wait.until(ExpectedConditions.visibilityOf(element));
        int second = (milisecond)/1000;
        try {
            WebDriverWait wait = new WebDriverWait(driver.get(), second);
            wait.pollingEvery(3, TimeUnit.SECONDS).ignoring(java.util.NoSuchElementException.class).until(ExpectedConditions.visibilityOf(driver.get().findElement(By.xpath(pathOfLocator)))) ;
        } catch (Exception e) {
            System.out.println("Element not present : " + pathOfLocator);
            e.printStackTrace();
        }
    }

    public static void explicitWait(AppiumDriver driver, WebElement element, int milisecond){
        //   WebDriverWait wait;
        //   wait = new WebDriverWait(driver,milisecond);
        //   wait.until(ExpectedConditions.visibilityOf(element));
        int second = (milisecond)/1000;
        try {
            WebDriverWait wait = new WebDriverWait(driver, second);
            wait.pollingEvery(3, TimeUnit.SECONDS).ignoring(java.util.NoSuchElementException.class).until(ExpectedConditions.visibilityOf(element)) ;
        } catch (Exception e) {
            System.out.println("Element not present : " + element);
            e.printStackTrace();
        }
    }

    public static void ScrollDown(String name, String matchString){

        List <WebElement> li = MobileApp.getDriver().findElementsByAccessibilityId("name");
        int size= li.size();
        System.out.println("Size   :"+size);

        for (int i = 0; i<=size; i++) {
            if (li.get(i).getAttribute("name").equals("matchString")) {
                li.get(i).click();
                break;
            }
        }
    }

}
