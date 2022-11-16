package org.example;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {

    private static WebDriver driver;
    private static Map<String, Object> vars;
    static JavascriptExecutor js;

    private static Integer minutesToSleep = 2;

    public static void main(String[] args) throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        driver = new ChromeDriver(options);
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();

        driver.get("https://aviso.bz");
        System.out.println("please authenticate in opened tab, and hit any key");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        for (; ; ) {

            SerfKrownTest skt = new SerfKrownTest(driver, vars, js);
            try {
                skt.serfKrown();
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }

            SerfRegularTest srt = new SerfRegularTest(driver, vars, js);
            try {
                srt.serfRegular();
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }

            WatchYoutubeTest wut = new WatchYoutubeTest(driver, vars, js);
            try {
                wut.watchYoutube();
            }
            catch (ElementNotInteractableException e){
                System.out.println("Maybe youtube video was not loaded");
                e.printStackTrace();
                String originalHandle = driver.getWindowHandle();

                //Do something to open new tabs

                for(String handle : driver.getWindowHandles()) {
                    if (!handle.equals(originalHandle)) {
                        driver.switchTo().window(handle);
                        driver.close();
                    }
                }

                driver.switchTo().window(originalHandle);
                Thread.sleep(1000);
            }
            catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }

            try {
                System.out.println("I'm going to sleep for "+ minutesToSleep + " minutes. See you soon");
                Thread.sleep(minutesToSleep * 60000);
                System.out.println("It's time to wake up and start watching ads");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }


    }
}