package Utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class Utility extends WebElementInteractions {

    public static int[] parsePinIntoArrayInt(String pin){

        int[] arrPin = new int[4];
        char[] c = pin.toCharArray();
        int i = 0;

        for(char p : c){
            arrPin[i] = Integer.parseInt(Character.toString(p));
        }

        return arrPin;
    }

    protected String timeStamp(){
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
    }

    public String returnYear(String string){

        String[] parts = string.split("-");
        return parts[0];
    }

    public String returnMonth(String string){

        String[] parts = string.split("-");
        return parts[1];
    }

    public String returnDay(String string){

        String[] parts = string.split("-");
        return parts[2];
    }


    protected static void wait(int milisecond){
        try {
            Thread.sleep(milisecond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void setAttributeFor(String screen, String attribute, String value){
        try {
            MobileApp.MobileScreens.get(screen).getLocator(attribute).setAttributeValue(value);
        } catch (Exception e) {
            System.out.println("Mo locator found with : " + attribute +" on screen :"+screen);
            e.printStackTrace();
        }
    }

    protected void replaceAttributeFor(String screen, String attribute, String target, String replaceValue){
        try {
            MobileApp.MobileScreens.get(screen).getLocator(attribute).getAttributeValue().replace(target, replaceValue);
        } catch (Exception e) {
            System.out.println("Mo locator found with : " + attribute +" on screen :"+screen);
            e.printStackTrace();
        }
    }

}
