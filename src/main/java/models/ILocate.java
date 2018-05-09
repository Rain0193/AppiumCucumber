package models;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ILocate {

    Locator setTypeOfLocator(String locatorType);
    Locator setAttributeValue(String attributeValue);
    String getTypeOfLocator();
    String getAttributeValue();
    By getByLocator() throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException;
    MobileElement getWebElement(AppiumDriver driver) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException;
    List<MobileElement> getWebElements(AppiumDriver driver) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException;
}
