package models;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Locator implements ILocate {


    Locator(){}

    String typeOfLocator;
    String attributeValue;

    @Override
    public Locator setTypeOfLocator(String typeOfLocator) {
        this.typeOfLocator  = typeOfLocator;
        return this;
    }

    @Override
    public Locator setAttributeValue(String attributeValue) {
        this.attributeValue  = attributeValue;
        return this;
    }

    @Override
    public String getTypeOfLocator() {
        return this.typeOfLocator;
    }

    @Override
    public String getAttributeValue() {
        return this.attributeValue;
    }

    @Override
    public By getByLocator() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        By byLocator = null;
//        Class<By> cls = (Class<By>) Class.forName("org.openqa.selenium.By");
//        Method m = cls.getMethod(this.typeOfLocator, String[].class);
//        String[] params = {this.attributeValue};

        if(this.typeOfLocator.equals("id")){
            byLocator =  By.id(this.attributeValue);
        }else if(this.typeOfLocator.equals("css")){
            byLocator =  By.cssSelector(this.attributeValue);
        }

//        return (By) m.invoke(null, (Object) params);
        return byLocator;
    }

    @Override
    public MobileElement getWebElement(AppiumDriver driver) {

        MobileElement webElement = null;
        WebDriverWait wait = new WebDriverWait(driver, 60);

//        Class<By> cls = (Class<By>) Class.forName("org.openqa.selenium.By");
//        Method m = cls.getMethod(this.typeOfLocator, String[].class);
//        String[] params = {this.attributeValue};

        try {
            switch (this.typeOfLocator) {
                case "id":
                    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(this.attributeValue))));
                    webElement = (MobileElement) driver.findElement(By.id(this.attributeValue));
                    break;
                case "css":
                    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(this.attributeValue))));
                    webElement = (MobileElement) driver.findElement(By.cssSelector(this.attributeValue));
                    break;
                case "className":
                    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className(this.attributeValue))));
                    webElement = (MobileElement) driver.findElement(By.className(this.attributeValue));
                    break;
                case "xpath":
                    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(this.attributeValue))));
                    webElement = (MobileElement) driver.findElement(By.xpath(this.attributeValue));
                    break;
                case "accessibility":
                    wait.until(ExpectedConditions.visibilityOf(driver.findElementByAccessibilityId(this.attributeValue)));
                    webElement = (MobileElement) driver.findElementByAccessibilityId(this.attributeValue);
                    break;
            }
        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
        }

//        return driver.findElement((By) m.invoke(null, (Object) params));
        return webElement;
    }

    public MobileElement getWebElementWithReplace(AppiumDriver driver, String olsStr, String newStr) {

        MobileElement webElement = null;

//        Class<By> cls = (Class<By>) Class.forName("org.openqa.selenium.By");
//        Method m = cls.getMethod(this.typeOfLocator, String[].class);
//        String[] params = {this.attributeValue};

        try {
            switch (this.typeOfLocator) {
                case "id":
                    webElement = (MobileElement) driver.findElement(By.id(this.attributeValue.replace(olsStr,newStr)));
                    break;
                case "css":
                    webElement = (MobileElement) driver.findElement(By.cssSelector(this.attributeValue.replace(olsStr,newStr)));
                    break;
                case "className":
                    webElement = (MobileElement) driver.findElement(By.className(this.attributeValue.replace(olsStr,newStr)));
                    break;
                case "xpath":
                    webElement = (MobileElement) driver.findElement(By.xpath(this.attributeValue.replace(olsStr,newStr)));
                    break;
            }
        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
        }

//        return driver.findElement((By) m.invoke(null, (Object) params));
        return webElement;
    }

    @Override
    public List<MobileElement> getWebElements(AppiumDriver driver) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        List<MobileElement> webElements = null;

        try {
            switch (this.typeOfLocator) {
                case "id":
                    webElements = driver.findElements(By.id(this.attributeValue));
                    break;
                case "css":
                    webElements = driver.findElements(By.cssSelector(this.attributeValue));
                    break;
                case "className":
                    webElements = driver.findElements(By.className(this.attributeValue));
                    break;
                case "xpath":
                    webElements = driver.findElements(By.xpath(this.attributeValue));
                    break;
                case "name":
                    webElements = driver.findElements(By.name(this.attributeValue));
                    break;
                case "accessibility":
                    webElements =  driver.findElementsByAccessibilityId(this.attributeValue);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return webElements;
    }

    public String toString() {
        return this.typeOfLocator  +" :  "+this.attributeValue;
    }
}
