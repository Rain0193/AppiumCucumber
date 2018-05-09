package models;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class MobileScreen {

    MobileScreen mobileScreen;
    String pageName;
    Map<String, Locator> locator;


    public MobileScreen() {
    }

    public MobileScreen setPageName(String pageName) {
        this.pageName = pageName;
        this.mobileScreen = new MobileScreen();
        return this.mobileScreen;
    }

    public MobileScreen setLocator(String givenLocatorName, Locator locate) {

        if (locate.typeOfLocator != null && !locator.containsKey(givenLocatorName)) {
            locator.put(givenLocatorName, new Locator().setTypeOfLocator(locate.typeOfLocator).setAttributeValue(locate.attributeValue));
        }
        return this.mobileScreen;
    }

    public Locator getLocator(String givenLocatorName) {
        Locator locate = null;

        if (locator.containsKey(givenLocatorName)) {
            locate = locator.get(givenLocatorName);
        }
        return locate;
    }

    public MobileElement getElement(String attribute, AppiumDriver driver) {
        Locator locate = null;

        if (locator.containsKey(attribute)) {
            locate = locator.get(attribute);
        }
        return locate.getWebElement(driver);
    }

    public MobileElement getElementwithReplace(String attribute, AppiumDriver driver, String oldStr, String newStr) {
        Locator locate = null;

        if (locator.containsKey(attribute)) {
            locate = locator.get(attribute);
        }
        return locate.getWebElementWithReplace(driver,oldStr,newStr);
    }

    public List<MobileElement> getElements(String attribute, AppiumDriver driver) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Locator locate = null;

        if (locator.containsKey(attribute)) {
            locate = locator.get(attribute);
        }
        return locate.getWebElements(driver);
    }

    public MobileScreen getPage(String pageName) {
        return this.mobileScreen;
    }

    public String getPageName() {
        return this.pageName;
    }

}
