package Utils;

import CommonHelpers.BrowserMethod;
import CommonHelpers.OncomateIOS;
import CommonHelpers.OncomateMethod;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import models.*;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.misc.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileApp {
    public enum EXECUTE {
        LOCAL_ANDROID,
        LOCAL_IOS,
        BS_ANDROID,
        BS_IOS,
        SL_ANDROID
    }

    private static MobileApp ourInstance = null;
    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    public static final ThreadLocal<AppiumDriver> pre_req_driver = new ThreadLocal<>();
    public static final Map<String, MobileScreen> MobileScreens = new HashMap<>();
    public static ExtentReports report = null;
    public static ExtentTest logger = null;
    private static Properties appProperties = null;
    private static Properties propChrome = null;
    private static Patient patient = null;
    public static Map<String, String> GLOBAL_VARIABLE = new HashMap<>();
    private static AppiumDriverLocalService service;
    public static boolean androidPlatform = true;

    private MobileApp(EXECUTE execute) throws Throwable {
        int port = 4723;

        runAppium(port);
        setDriver(execute);
    }

    public static MobileApp setUp() throws Throwable {
        @SuppressWarnings("ThrowableNotThrown") String callerClassName = new Exception().getStackTrace()[1].getClassName();

        System.out.println("************"+callerClassName);
        if (ourInstance == null || driver.get().getSessionId() == null) {

            switch (callerClassName) {

                case "cucumberTestng.Local_AndroidTestRunner":
                    ourInstance = new MobileApp(EXECUTE.LOCAL_ANDROID);
                    break;
                case "cucumberTestng.Local_IOSTestRunner":
                    androidPlatform = false ;
                    ourInstance = new MobileApp(EXECUTE.LOCAL_IOS);
                    break;
                case "cucumberTestng.BS_AndroidTestRunner":
                    ourInstance = new MobileApp(EXECUTE.BS_ANDROID);
                    break;
                case "cucumberTestng.BS_IOSTestRunner":
                    ourInstance = new MobileApp(EXECUTE.BS_IOS);
                    break;
                case "cucumberTestng.SL_AndroidTestRunner":
                    ourInstance = new MobileApp(EXECUTE.SL_ANDROID);
                    break;
                default:
                    throw new Exception("No runner calls is triggered for the execution....");
            }

            System.out.println("***** New session id : " + driver.get().getSessionId() + " *****");

        }
        return ourInstance;
    }

    private static Map<String, MobileScreen> getAllPages(String filePath) {
        Map<String, MobileScreen> pages = new HashMap<>();

        try {
            Iterator it = FileUtils.iterateFiles(new File(filePath), new String[]{"yaml"}, false);

            while (it.hasNext()) {
                File file = (File) it.next();
                MobileScreen mobileScreen = getPage(file);

                pages.put(mobileScreen.getPageName(), mobileScreen);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return pages;
    }

    private void setDriver(EXECUTE execute) throws Throwable {

        try {
            String filePath;
            switch (execute) {
                case LOCAL_ANDROID:
                    driver.set((AppiumDriver) getAndroidDriver().get());
//                    verifyDefaultAppMsg();
                    filePath = System.getProperty("user.dir") + "/src/main/resources/PageObjects/Android/";
//                    chnageAccessabilityOnFly(filePath);//cmnt
                    MobileScreens.putAll(getAllPages(filePath));
                    patient = new Patient(appProperties.getProperty("patientUsername"), appProperties.getProperty("patientPassword"), appProperties.getProperty("pid"));
                    setUpTheAppConfigurationAndriod(); //From Second Time
                    break;
                case LOCAL_IOS:
                    //appium-xcuitest-driver@2.76.2
                    //webkit 1.8
                    //buildWebDriverAgentProject();
                    MobileScreens.putAll(getAllPages(System.getProperty("user.dir") + "/src/main/resources/PageObjects/IOS/"));
                    driver.set((AppiumDriver) getIOSDriver().get());
                    patient = new Patient(appProperties.getProperty("patientUsername"), appProperties.getProperty("patientPassword"), appProperties.getProperty("pid"));
                    setUpTheAppConfigurationIOS();
                    break;
                case BS_ANDROID:
                    uploadAndroidChromeAppOnBS();
                    driver.set((AppiumDriver) getBSAndroidDriver().get());
                    filePath = System.getProperty("user.dir") + "/src/main/resources/PageObjects/Android/";
                    MobileScreens.putAll(getAllPages(filePath));
                    setBsChromeWebDriver();
                    patient = new Patient(appProperties.getProperty("patientUsername"), appProperties.getProperty("patientPassword"), appProperties.getProperty("pid"));
                    BrowserMethod.openOncomateWithURL(propChrome.getProperty("appUrl"));
                    //OncomateMethod.launchAppUsingCMD(appProperties.getProperty("appUrl"));
                    OncomateMethod.loginIntoOncomate(patient);
                    OncomateMethod.setPinFromCmd(appProperties.getProperty("setPin"));
                    OncomateMethod.confirmPinFromCmd(appProperties.getProperty("setPin"));
                    OncomateMethod.confirmPinConformationMessage();
                    OncomateMethod.skipTnC();
                    driver.set((AppiumDriver) getBSAndroidDriver().get());
                    break;
                case BS_IOS:
                    MobileScreens.putAll(getAllPages(System.getProperty("user.dir") + "/src/main/resources/PageObjects/IOS/"));
                    driver.set((AppiumDriver) getBSIOSDriver().get());
                    break;
                case SL_ANDROID:
                    driver.set((AppiumDriver) getSLAndroidDriver().get());
                    filePath = System.getProperty("user.dir") + "/src/main/resources/PageObjects/Android/";
                    MobileScreens.putAll(getAllPages(filePath));
                    setSLchromeWebDriver();
                    patient = new Patient(appProperties.getProperty("patientUsername"), appProperties.getProperty("patientPassword"), appProperties.getProperty("pid"));
                    BrowserMethod.openOncomateWithURL(propChrome.getProperty("appUrl"));
                    //OncomateMethod.launchAppUsingCMD(appProperties.getProperty("appUrl"));
                    OncomateMethod.loginIntoOncomate(patient);
                    OncomateMethod.setPinFromCmd(appProperties.getProperty("setPin"));
                    OncomateMethod.confirmPinFromCmd(appProperties.getProperty("setPin"));
                    OncomateMethod.confirmPinConformationMessage();
                    OncomateMethod.skipTnC();
                    driver.set((AppiumDriver) getBSAndroidDriver().get());
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

   private static MobileScreen getPage(File file) {
        MobileScreen mobileScreen = null;
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            mobileScreen = mapper.readValue(file, MobileScreen.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileScreen;
    }

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    private static ThreadLocal<WebDriver> getAndroidDriver() {
        appProperties = new Properties();
        try {
            FileInputStream input = new FileInputStream("src/main/resources/ConfigFiles/androidLocalConfig.properties");
            appProperties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getAndroidWebDriver(appProperties);
    }

    private static ThreadLocal<WebDriver> getIOSDriver() throws Exception {
        appProperties = new Properties();

        try {
            FileInputStream input = new FileInputStream("src/main/resources/ConfigFiles/iOSLocalConfig.properties");
            appProperties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getIOSWebDriver(appProperties);
    }

    private static ThreadLocal<WebDriver> getBSAndroidDriver() {
        appProperties = new Properties();
        AppUploadResponse bsLink = null;

        try {
            FileInputStream input = new FileInputStream("src/main/resources/ConfigFiles/andriodBSConfig.properties");
            appProperties.load(input);
            bsLink = uploadAndroidAppOnBS();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBsWebDriver(bsLink != null ? bsLink.app_url : null);
    }

    private static ThreadLocal<WebDriver> getSLAndroidDriver() {
        appProperties = new Properties();
        try {
            FileInputStream input = new FileInputStream("src/main/resources/ConfigFiles/andriodSLConfig.properties");
            appProperties.load(input);
            //    bsLink = uploadAndroidAppOnBS();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getSlWebDriver();
    }

    private static ThreadLocal<WebDriver> getBSIOSDriver() {
        appProperties = new Properties();
        AppUploadResponse bsLink = null;
        try {
            FileInputStream input = new FileInputStream("src/main/resources/ConfigFiles/iOS_BSConfig.properties");
            appProperties.load(input);
            bsLink = uploadIOSAppOnBS();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBsWebDriver(bsLink != null ? bsLink.app_url : null);
    }

    private static ThreadLocal<WebDriver> getAndroidWebDriver(final Properties prop) {
        System.out.println("***** Creating Local Android Driver Instance *****");
        final DesiredCapabilities capabilities = new DesiredCapabilities();

        //mandatory capabilities
        capabilities.setCapability("deviceName", prop.getProperty("device"));
        capabilities.setCapability("platformName", prop.getProperty("platform"));
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, prop.getProperty("package"));
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, prop.getProperty("activity"));
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 180);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");

        return new ThreadLocal<WebDriver>() {
            @Override
            protected WebDriver initialValue() {
                AndroidDriver androidDriver = null;

                try {
                    androidDriver = new AndroidDriver<>(new URL(prop.getProperty("appiumServer")), capabilities);
                    androidDriver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
                    System.out.println(" App already installed on mobile .... ");
                    androidDriver.resetApp();
                    Thread.sleep(Global.minWait);
                } catch (WebDriverException webDriverError) {
                    System.out.println("WebDriverException ...." + webDriverError.getMessage());
                    System.out.println(" App not installed on mobile .... ");
                    try {
                        File appDir = new File(prop.getProperty("appPath"));
                        File app = new File(appDir, prop.getProperty("application"));
                        System.out.println("Installing App....." + prop.getProperty("application"));
                        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                        androidDriver = new AndroidDriver<>(new URL(prop.getProperty("appiumServer")), capabilities);
                        androidDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                        if (androidDriver.isAppInstalled(prop.getProperty("package")))
                            System.out.println("Successfully installed app : " + prop.getProperty("package"));
                    } catch (Exception e1) {
                        webDriverError.printStackTrace();
                        throw webDriverError;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return androidDriver;
            }
        };
    }

    private static ThreadLocal<WebDriver> getIOSWebDriver(final Properties prop) throws Exception {
        System.out.println("************Creating Local PageObjects.IOS Driver Instance...*****");
        final DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.2.2");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 5s");
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, getConnectedIPhone());
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + File.separator + "apps" + File.separator + prop.getProperty("app"));
        desiredCapabilities.setCapability("startIWDP", true);
        //desiredCapabilities.setCapability("useNewWDA", true);
        //desiredCapabilities.setCapability("showXcodeLog", true);
        //desiredCapabilities.setCapability("xcodeOrgId", "4XVM2X87AA");
        //desiredCapabilities.setCapability("xcodeSigninId", "iPhone Developer");
        //desiredCapabilities.setCapability("xcodeConfigFile", "/Users/vmsoncomatehalo/Documents/workspace/oncomateAutomation/apps/oncomateConf.xccconfig");
        desiredCapabilities.setCapability("waitForQuiescence", false);


        return new ThreadLocal<WebDriver>() {
            @Override
            protected WebDriver initialValue() {
                System.out.println("initialValue--------    --" + prop.getProperty("appiumServer"));

                IOSDriver iosDriver = null;
                try {
                    iosDriver = new IOSDriver(new URL(prop.getProperty("appiumServer")), desiredCapabilities);
                    iosDriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
                    System.out.println("app launched again--------    -- " + iosDriver.getSessionId().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return iosDriver;
            }
        };
    }

    private static ThreadLocal<WebDriver> getBsWebDriver(String appUrl) {
        final DesiredCapabilities capabilities = new DesiredCapabilities();

        System.out.println("*****Creating Android Driver Instance for app...: " + appUrl);

        capabilities.setCapability("device", appProperties.getProperty("device"));
        capabilities.setCapability("os_version", appProperties.getProperty("version"));
        capabilities.setCapability("app", appUrl);

        return new ThreadLocal<WebDriver>() {
            @Override
            protected WebDriver initialValue() {
                AndroidDriver androidDriver = null;
                IOSDriver BS_iOSDriver = null;
                try {
                    if (appProperties.getProperty("applicationType").equals("android")) {
                        System.out.println("Android Driver intializing...with properties : " + appProperties);
                        /*
                        DesiredCapabilities cap1 = new DesiredCapabilities();
                        cap1.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
                        cap1.setCapability(MobileCapabilityType.FULL_RESET, false);
                        cap1.setCapability(MobileCapabilityType.NO_RESET, true);
                        cap1.setCapability("device", appProperties.getProperty("device"));
                        cap1.setCapability("os_version", appProperties.getProperty("version"));
                        WebDriver remote = new RemoteWebDriver(new URL(("http://" + appProperties.getProperty("user") + ":" + appProperties.getProperty("key") + "@hub-cloud.browserstack.com/wd/hub")), cap1);
                        remote.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
                        remote.get("https://www.google.co.in");
                        */
                        androidDriver = new AndroidDriver(new URL(("http://" + appProperties.getProperty("user") + ":" + appProperties.getProperty("key") + "@hub-cloud.browserstack.com/wd/hub")), capabilities);
                        androidDriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
                    } else {
                        System.out.println("iOS Driver intializing...with properties : " + appProperties);

                        IOSDriver driver1 = new IOSDriver(new URL(("http://" + appProperties.getProperty("user") + ":" + appProperties.getProperty("key") + "@hub-cloud.browserstack.com/wd/hub")), capabilities);

                        IOSElement loginButton = (IOSElement) new WebDriverWait(driver1, 30).until(
                                ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Log In")));
                        loginButton.click();
                        IOSElement emailTextField = (IOSElement) new WebDriverWait(driver1, 30).until(
                                ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Email address")));
                        emailTextField.sendKeys("hello@browserstack.com");

                        driver1.findElementByAccessibilityId("Next").click();
                        wait(Global.minWait);

                        List textElements = driver1.findElementsByXPath("//XCUIElementTypeStaticText");
                        assert (textElements.size() > 0);
                        String matchedString = "";
                        for (Object textElement : textElements) {
                            String textContent = ((IOSElement) textElement).getText();
                            if (textContent != null && textContent.contains("not registered")) {
                                matchedString = textContent;
                            }
                        }

                        System.out.println(matchedString);
                        assert (matchedString.contains("not registered on WordPress.com"));


                        BS_iOSDriver = new IOSDriver(new URL("https://" + "vishva1" + ":" + "aDpiKYtw31Uss1UzKDo9" + "@hub-cloud.browserstack.com/wd/hub"), capabilities);
                        BS_iOSDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return androidDriver == null ? BS_iOSDriver : androidDriver;
            }
        };
    }

    private static ThreadLocal<WebDriver> getSlWebDriver() {
        final DesiredCapabilities capabilities = new DesiredCapabilities();

        System.out.println("*****Creating Android Driver Instance for app...: ");

        capabilities.setCapability("testobjectApiKey", appProperties.getProperty("testObjectKey"));

        // Dynamic device allocation of an Android,
        capabilities.setCapability("platformName", appProperties.getProperty("platform"));
        capabilities.setCapability("platformVersion", appProperties.getProperty("version"));
        capabilities.setCapability("deviceName", appProperties.getProperty("device"));

        // Set allocation from private device pool only
        capabilities.setCapability("privateDevicesOnly", "true");

        // Set Application under test
        capabilities.setCapability("testobject_app_id", "1");
        capabilities.setCapability("name", "My Test 1!");

        // Set Appium version
        capabilities.setCapability("appiumVersion", "1.6.4");

        return new ThreadLocal<WebDriver>() {
            @Override
            protected WebDriver initialValue() {
                AndroidDriver slAndroidDriver = null;
                IOSDriver iOSDriver = null;
                try {
                    if (appProperties.getProperty("platform").equalsIgnoreCase("Android")) {
                        System.out.println("Android Driver intializing...with properties : " + appProperties);

                        capabilities.setCapability("automationName", "uiautomator2");

                        slAndroidDriver = new AndroidDriver(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities);
                        slAndroidDriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
                        slAndroidDriver.getCapabilities().getCapability("testobject_test_report_url");
                        slAndroidDriver.getCapabilities().getCapability("testobject_test_live_view_url");


                    } else {
                        System.out.println("iOS Driver intializing...with properties : " + appProperties);

                        IOSDriver slIOSDriver = new IOSDriver(new URL(("http://" + appProperties.getProperty("user") + ":" + appProperties.getProperty("key") + "@hub-cloud.browserstack.com/wd/hub")), capabilities);

                        IOSElement loginButton = (IOSElement) new WebDriverWait(slIOSDriver, 30).until(
                                ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Log In")));
                        loginButton.click();
                        IOSElement emailTextField = (IOSElement) new WebDriverWait(slIOSDriver, 30).until(
                                ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Email address")));
                        emailTextField.sendKeys("hello@browserstack.com");

                        slIOSDriver.findElementByAccessibilityId("Next").click();
                        wait(Global.minWait);

                        List textElements = slIOSDriver.findElementsByXPath("//XCUIElementTypeStaticText");
                        assert (textElements.size() > 0);
                        String matchedString = "";
                        for (Object textElement : textElements) {
                            String textContent = ((IOSElement) textElement).getText();
                            if (textContent != null && textContent.contains("not registered")) {
                                matchedString = textContent;
                            }
                        }

                        System.out.println(matchedString);
                        assert (matchedString.contains("not registered on WordPress.com"));


                        iOSDriver = new IOSDriver(new URL("https://" + "vishva1" + ":" + "aDpiKYtw31Uss1UzKDo9" + "@hub-cloud.browserstack.com/wd/hub"), capabilities);
                        iOSDriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return slAndroidDriver == null ? iOSDriver : slAndroidDriver;
            }
        };
    }

    private static void setBsChromeWebDriver() throws Throwable {
        System.out.println("*****Creating Android Chrome Driver Instance for app... ");

        AndroidDriver chromeDriver;

        DesiredCapabilities cap1 = new DesiredCapabilities();

        try {
            propChrome = new Properties();
            FileInputStream input = new FileInputStream("src/main/resources/ConfigFiles/andriodBSConfig.properties");
            propChrome.load(input);

            cap1.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
            cap1.setCapability("device", propChrome.getProperty("device"));
            cap1.setCapability("os_version", propChrome.getProperty("version"));
            chromeDriver = new AndroidDriver(new URL(("http://" + propChrome.getProperty("user") + ":" + propChrome.getProperty("key") + "@hub-cloud.browserstack.com/wd/hub")), cap1);
            chromeDriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
            chromeDriver.get(appProperties.getProperty("appUrl"));
/*
         cap1.setCapability("deviceName", appProperties.getProperty("device"));
         cap1.setCapability("platformName", appProperties.getProperty("platform"));
         cap1.setCapability(MobileCapabilityType.FULL_RESET, false);
         cap1.setCapability(MobileCapabilityType.NO_RESET, true);
         cap1.setCapability("os_version", appProperties.getProperty("version"));
         cap1.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, propChrome.getProperty("packageChrome"));
         cap1.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, propChrome.getProperty("activityChrome"));
         chromeDriver = new AndroidDriver(new URL(("http://" + appProperties.getProperty("user") + ":" + appProperties.getProperty("key") + "@hub-cloud.browserstack.com/wd/hub")), cap1);
         chromeDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);


     } catch (WebDriverException webDriverErr) {
         if (webDriverErr.getMessage().contains(propChrome.getProperty("packageChrome"))) {
             DesiredCapabilities cap2 = new DesiredCapabilities();
             System.out.println(" App not installed on mobile .... ");
             AppUploadResponse bsLink = uploadAndroidChromeAppOnBS();
             try {
                 cap2.setCapability("device", appProperties.getProperty("device"));
                 cap2.setCapability("os_version", appProperties.getProperty("version"));
                 cap2.setCapability("app", bsLink);
                 chromeDriver = new AndroidDriver(new URL(("http://" + appProperties.getProperty("user") + ":" + appProperties.getProperty("key") + "@hub-cloud.browserstack.com/wd/hub")), cap2);
                 chromeDriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
                 chromeDriver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
                 if (chromeDriver.isAppInstalled(propChrome.getProperty("package")))
                     System.out.println("Successfully installed app : " + propChrome.getProperty("package"));
             } catch (Exception e1) {
                 webDriverErr.printStackTrace();
             }
         } else {
             throw webDriverErr;
         }
        */

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        pre_req_driver.set(chromeDriver);
    }

    private static void setSLchromeWebDriver() throws Throwable {
        System.out.println("*****Creating Android Chrome Driver Instance for app... ");

        AndroidDriver chromeDriver;

        DesiredCapabilities cap1 = new DesiredCapabilities();

        try {
            cap1.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
            cap1.setCapability("testobjectApiKey", appProperties.getProperty("testObjectKey"));
            cap1.setCapability("platformName", appProperties.getProperty("platform"));
            cap1.setCapability("platformVersion", appProperties.getProperty("version"));
            cap1.setCapability("deviceName", appProperties.getProperty("device"));
            cap1.setCapability("privateDevicesOnly", "true");
            chromeDriver = new AndroidDriver(new URL("https://us1.appium.testobject.com/wd/hub"), cap1);
            chromeDriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
            chromeDriver.getCapabilities().getCapability("testobject_test_report_url");
            chromeDriver.getCapabilities().getCapability("testobject_test_live_view_url");
            chromeDriver.get(appProperties.getProperty("appUrl"));

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        pre_req_driver.set(chromeDriver);
    }

    public static void closeDriver() {
        if (getDriver() != null) {
            try {
                System.out.println("Closing the Driver instance....");
                getDriver().quit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        driver.remove();
    }


    // Browser stack specific

    private static AppUploadResponse uploadAndroidAppOnBS() {

        System.out.println("Installing the android on BS.......");
        File inFile;
        String name = appProperties.getProperty("user");
        String password = appProperties.getProperty("key");
        String uri = appProperties.getProperty("uri");
        String application = appProperties.getProperty("application");
        System.out.println("Browser Stack UserName : " + name);
        System.out.println("Browser Stack Key : " + password);
        System.out.println("Browser Stack URI : " + uri);
        AppUploadResponse BS_UPLOADER_RESPONSE;

        /*
        Iterator it = FileUtils.iterateFiles(new File(System.getProperty("user.dir") + File.separator + "apps"), new String[]{appProperties.getProperty("fileType")}, false);

        if (it.hasNext()) {
            inFile = (File) it.next();
            System.out.println("Installing the app " + inFile.getName() + " from : " + System.getProperty("user.dir") + File.separator + "apps" + " ......");
        } else {
            throw new Exception("No file found in root location : " + System.getProperty("user.dir") + File.separator + "apps");
        }
        */

        inFile = new File(System.getProperty("user.dir") + File.separator + "apps" + File.separator + application);

        System.out.println(System.getProperty("user.dir") + File.separator + "apps" + File.separator + application);

        BS_UPLOADER_RESPONSE = getAppUploadResponse(inFile, name, password, uri);

        return BS_UPLOADER_RESPONSE;
    }

    private static AppUploadResponse uploadAndroidChromeAppOnBS() {

        System.out.println("Installing the android chrome on BS.......");
        File inFile;
        String name = appProperties.getProperty("user");
        String password = appProperties.getProperty("key");
        String uri = appProperties.getProperty("uri");
        String application = appProperties.getProperty("applicationChrome");
        System.out.println("Browser Stack UserName : " + name);
        System.out.println("Browser Stack Key : " + password);
        System.out.println("Browser Stack URI : " + uri);
        System.out.println("Browser Stack Chrome : " + application);
        AppUploadResponse BS_UPLOADER_RESPONSE;

        /*
        Iterator it = FileUtils.iterateFiles(new File(System.getProperty("user.dir") + File.separator + "apps"), new String[]{appProperties.getProperty("fileType")}, false);

        if (it.hasNext()) {
            inFile = (File) it.next();
            System.out.println("Installing the app " + inFile.getName() + " from : " + System.getProperty("user.dir") + File.separator + "apps" + " ......");
        } else {
            throw new Exception("No file found in root location : " + System.getProperty("user.dir") + File.separator + "apps");
        }
        */

        inFile = new File(System.getProperty("user.dir") + File.separator + "apps" + File.separator + application);

        System.out.println(System.getProperty("user.dir") + File.separator + "apps" + File.separator + application);

        BS_UPLOADER_RESPONSE = getAppUploadResponse(inFile, name, password, uri);

        return BS_UPLOADER_RESPONSE;
    }

    private static AppUploadResponse uploadIOSAppOnBS() throws Exception {

        System.out.println("Installing the PageObjects.IOS on BS.......");
        File inFile;
        String name = appProperties.getProperty("user");
        String password = appProperties.getProperty("key");
        String uri = appProperties.getProperty("uri");
        System.out.println("Browser Stack UserName : " + name);
        System.out.println("Browser Stack Key : " + password);
        System.out.println("Browser Stack URI : " + uri);
        AppUploadResponse BS_UPLOADER_RESPONSE;

        Iterator it = FileUtils.iterateFiles(new File(System.getProperty("user.dir") + File.separator + "apps"), new String[]{appProperties.getProperty("fileType")}, false);

        if (it.hasNext()) {
            inFile = (File) it.next();
            System.out.println("Installing the app " + inFile.getName() + " from : " + System.getProperty("user.dir") + File.separator + "apps" + " ......");
        } else {
            throw new Exception("No IPA file found in root location : " + System.getProperty("user.dir") + File.separator + "apps");
        }

        BS_UPLOADER_RESPONSE = getAppUploadResponse(inFile, name, password, uri);

        return BS_UPLOADER_RESPONSE;
    }

    private static AppUploadResponse getAppUploadResponse(File inFile, String name, String password, String uri) {
        AppUploadResponse BS_RESPONSE = null;
        try {
            FileInputStream fis = new FileInputStream(inFile);
            HttpClient httpClient = HttpClientBuilder.create().build();

            // server back-end URL
            HttpPost httppost = new HttpPost(uri);

            String authString = name + ":" + password;
            String authStringEnc = new BASE64Encoder().encode(authString.getBytes());

            httppost.setHeader(HttpHeaders.AUTHORIZATION, ("Basic " + authStringEnc));

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // set the file input stream and file name as arguments
            builder.addPart("file", new InputStreamBody(fis, inFile.getName()));
            HttpEntity entity = builder.build();
            httppost.setEntity(entity);

            // execute the request
            HttpResponse response = httpClient.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");
            System.out.println("Response Status Code : [" + statusCode + "] ");

            //Construct response
            ObjectMapper mapper = new ObjectMapper(new JsonFactory());
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            BS_RESPONSE = mapper.readValue(responseString, AppUploadResponse.class);
            System.out.println("Uploaded App Location in Browser Stack : " + BS_RESPONSE.app_url);

        } catch (ClientProtocolException e) {
            System.err.println("Unable to make connection....");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Unable to read file.....");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BS_RESPONSE;
    }


    // Appium specific and need to moved to specific class

    private void runAppium(int port) throws Exception {

        //if(!androidPlatform) {
            killPort(port);
        //}

        String host = "0.0.0.0";

        if (!checkIfServerIsRunnning(port)) {
            System.out.println("############ Stating Appium server.... ############");
            startAppiumServer(host, port);
            Thread.sleep(Global.medWait);
        } else {
            System.out.println("Appium Server already running on Port - " + port);
        }
        //Runtime.getRuntime().exec("ios_webkit_debug_proxy -c "+getConnectedIPhone()+":"+webkit_proxy_port+" -d");
    }

    private void startAppiumServer(String host, int port) {

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("noReset", "false");
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress(host);
        builder.usingPort(port);
        builder.withCapabilities(cap);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
        service = AppiumDriverLocalService.buildService(builder);
        service.start();

    }

    public static void stopServer() {
        System.out.println("Stoping Server........");
        service.stop();
    }


    //Common Utils

    public static void killPort(int port) throws IOException {
        System.out.println("Initiated killing the port..." + port);
        String line;
        Process process = Runtime.getRuntime().exec("lsof -i :" + port);

        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

        int count = 0;
        while ((line = input.readLine()) != null) {
            System.out.println(count + " : " + line);

            Matcher matcher1 = Pattern.compile("(\\w+)(\\s+)(\\d+)").matcher(line);
            if (matcher1.find()) {

                System.out.println(matcher1.group(0));

                Matcher matcher2 = Pattern.compile("(\\d+)").matcher(matcher1.group(0));

                if (matcher2.find()) {
                    System.out.println("Killing the port... " + port);
                    System.out.println(matcher2.group(0));
                    Runtime.getRuntime().exec("kill -9 " + matcher2.group(0));
                } else {
                    System.out.println("The post " + port + " is free .... ");
                }
            }

            count += 1;
        }
        input.close();
    }

    private boolean checkIfServerIsRunnning(int port) {

        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            //If control comes here, then it means that the port is in use
            isServerRunning = true;
        }
        return isServerRunning;
    }


    //IOS Specific

    private static String getConnectedIPhone() throws Exception {
        String line;
        BufferedReader input;
        String rVal = null;

        String[] cmd = {"sh", "-c", "instruments -s devices | grep -v Simulator | grep -v Mac"};
        input = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(cmd).getInputStream()));

        while ((line = input.readLine()) != null) {

            Matcher matcher1 = Pattern.compile("\\[(.*)]").matcher(line);

            if (matcher1.find()) {
                rVal = matcher1.group(1);
            }
        }
        input.close();

        if (rVal == null) {
            throw new Exception("No iPhone device connected to the machine..");
        }

        return rVal;

    }

    private void buildWebDriverAgentProject() throws Exception {
        // Build WebDriverAgent
        System.out.println("**Build WebDriverAgent**");
        String line;
        String[] cmd = {
                "xcodebuild",
                "-project",
                "/usr/local/lib/node_modules/appium/node_modules/appium-xcuitest-driver/WebDriverAgent/WebDriverAgent.xcodeproj",
                "-scheme",
                "WebDriverAgentRunner",
                "-destination",
                "id=" + getConnectedIPhone()
        };
        Process process = Runtime.getRuntime().exec(cmd);
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        System.out.println(input.readLine());
        while ((line = input.readLine()) != null) {
            System.out.println(line);
        }
    }


    //App Specific

    private void setUpTheAppConfigurationAndriod() throws InterruptedException {
        Thread.sleep(20000);
        OncomateMethod.launchAppUsingCMD(appProperties.getProperty("appUrl"));
        OncomateMethod.loginIntoOncomate(patient);
        OncomateMethod.setPinFromCmd(appProperties.getProperty("setPin"));
        OncomateMethod.confirmPinFromCmd(appProperties.getProperty("setPin"));
        OncomateMethod.confirmPinConformationMessage();
//        OncomateMethod.skipTnC(); //Comment when user want to verify the Welcome screens

    }

    private void setUpTheAppConfigurationIOS() {
        boolean isAppConfigured = getDriver().findElementsByAccessibilityId("0").size() > 0;

        if(isAppConfigured){
            System.out.println("Pin Already set ..");
        }else{
            OncomateIOS.launchAppWithURL(appProperties.getProperty("appUrl"));
            OncomateIOS.loginIntoOncomate(patient);
            OncomateIOS.setPinFromCmd(appProperties.getProperty("setPin"));
            OncomateIOS.confirmPinFromCmd(appProperties.getProperty("setPin"));
            OncomateIOS.confirmPinConformationMessage();
            //OncomateIOS.skipTnC();
        }
    }


    //Unused functions

    private static Boolean isSessionEmpty() {
        Boolean isEmpty;

        SessionId session = getDriver().getSessionId();
        isEmpty = session == null;

        return isEmpty;
    }

    private static void getChromeAppDriver() {
        AndroidDriver chromeDriver = null;
        final DesiredCapabilities cap1 = new DesiredCapabilities();
        try {
            propChrome = new Properties();
            FileInputStream input = new FileInputStream("src/main/resources/ConfigFiles/androidLocalPreReqConfig.properties");
            propChrome.load(input);

            //mandatory capabilities
            cap1.setCapability("deviceName", propChrome.getProperty("device"));
            cap1.setCapability("platformName", propChrome.getProperty("platform"));
            cap1.setCapability(MobileCapabilityType.FULL_RESET, false);
            cap1.setCapability(MobileCapabilityType.NO_RESET, true);
            cap1.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, propChrome.getProperty("package"));
            cap1.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, propChrome.getProperty("activity"));


            chromeDriver = new AndroidDriver<>(new URL(propChrome.getProperty("appiumServer")), cap1);
            chromeDriver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        } catch (WebDriverException webDriverErr) {
            if (webDriverErr.getMessage().contains(propChrome.getProperty("package"))) {
                System.out.println(" App not installed on mobile .... ");
                try {
                    File appDir = new File(propChrome.getProperty("appPath"));
                    File app = new File(appDir, propChrome.getProperty("application"));
                    System.out.println("Installing App....." + propChrome.getProperty("application"));
                    cap1.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

                    chromeDriver = new AndroidDriver<>(new URL(propChrome.getProperty("appiumServer")), cap1);
                    chromeDriver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
                    chromeDriver.manage().timeouts().pageLoadTimeout(8, TimeUnit.SECONDS);
                    if (chromeDriver.isAppInstalled(propChrome.getProperty("package")))
                        System.out.println("Successfully installed app : " + propChrome.getProperty("package"));
                } catch (MalformedURLException e1) {
                    webDriverErr.printStackTrace();
                }
            } else {
                throw webDriverErr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pre_req_driver.set(chromeDriver);
        System.out.println("&&&&&&  chromeDriver.isAppInstalled  &&&&& : " + chromeDriver.isAppInstalled("com.varian.oncomate"));
    }

    private static void getSafariAppDriver() throws Exception {
        RemoteWebDriver safariLocalDriver = null;

        propChrome = new Properties();
        FileInputStream input = new FileInputStream("src/main/resources/iOSDriverPorp.properties");
        propChrome.load(input);

        final DesiredCapabilities cap1 = new DesiredCapabilities();

        //mandatory capabilities
        cap1.setCapability("deviceName", propChrome.getProperty("device"));
        cap1.setCapability("platformName", propChrome.getProperty("platform"));
        cap1.setCapability("platformVersion", propChrome.getProperty("platformVersion"));
        cap1.setCapability("browserName", propChrome.getProperty("browserName"));
        cap1.setCapability(MobileCapabilityType.UDID, getConnectedIPhone());
        cap1.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
//        cap1.setCapability("safariInitialUrl", "https://wwww.google.co.in");

        try {
            safariLocalDriver = new RemoteWebDriver(new URL(propChrome.getProperty("appiumServer")), cap1);
            safariLocalDriver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
            safariLocalDriver.get("https://gw2rb.app.goo.gl/ZVcpA1xMtm3BWEpi2");
            System.out.println("&&&&&&  safariLocalDriver Session&&&&& : " + safariLocalDriver.getSessionId().toString());

            safariLocalDriver.quit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //Mobile Screen
    private static void chnageAccessabilityOnFly(String filePath){

        Map<String, MobileScreen> MobileScreens = new HashMap<>();

        MobileScreens.putAll(getAllPages(filePath));


    }


//    public static void verifyDefaultAppMsg(){
//
//        getDriver().findElementByAccessibilityId("You have not activated the Patient Portal mobile app. To activate the app tap the activation link in the email you received from the hospital for installing the mobile app.").getText();
//
//
//
//    }


}
