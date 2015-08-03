package qa;

import com.codeborne.selenide.SelenideElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.net.*;

import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Date;

import static com.codeborne.selenide.Selenide.*;

/**
 * Created by svehlak on 27.7.15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BaseTest implements
        TestInterfaceHelper {



    public static void UrLog(String message) {
        StackTraceElement caller = new Throwable().getStackTrace()[1];
        System.out.println(new Date()
        + " :: " + caller.getClassName()
        + " :: " + caller.getMethodName()
        + " :: " + caller.getLineNumber()
        + " :: " + message);
    }

    public static boolean IsNet() {
        try {
            URL url = new URL("http://google.com");
            HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
            Object response = urlConnect.getContent();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return  false;
        } return true;
    }


    public Method OpenURL() throws Exception {
        UrLog("Checking internet connection");
        boolean online = IsNet();
            if (online) {
                UrLog("Internet connection is available");
            } else {
                UrLog("Internet connection is not available");
                System.exit(1);
            }
        UrLog("Opening URL: " + FINALURL);
        open(FINALURL);
        $("#cashplay_text").shouldHave("Welcome to Cahsplay");
        return null;
    }
    @Test
    public void testALogin() throws Exception {
        /**login test with existing user**/
        UrLog("Trying to login");
        OpenURL();
        $(By.name("user[email")).shouldHave("Email");
        $(By.name("user[password")).shouldHave("Password");
        $(By.name("user[email]")).setValue(TestInterfaceHelper.LOGIN);
        $(By.name("user[password]")).setValue(TestInterfaceHelper.PWD);
        $(By.name("commit")).pressEnter();
        $$(screenshot("SCR"));
        $(By.className("tournament_select_middle")).shouldHave("Select tournament");
        $(By.linkText("Profile")).click();
        $("container-fluid").shouldHave("Orlosup Lomihnat");
        UrLog("Login done");
    }
    @Test
    public void testBLogOut() {
        UrLog("Trying to logout");
        /**assure you are logged in**/
        /**System.out.println("Method is " + new Exception().getStackTrace()[0].getMethodName());**/
        $(By.className("profile-link")).shouldHave("Profile").click();
        $("#container-fluid").shouldHave("Orlosup Lomihn");
        $(By.partialLinkText("Logout")).click();
        UrLog("Logout done");

    }
    @Test
    public void testCreateNewAccount() throws Exception {
        UrLog("Creating new account");
        OpenURL();
        UrLog("klikam na registraci");
        $(By.xpath("/html/body/div[1]/div[2]/form/div[4]/a")).shouldHave("CREATE NEW ACCOUNT").click();
//        UrLog("pisu pokus");
        $(By.name("user[email]")).shouldHave("Email").setValue(TestInterfaceHelper.LOGIN);
//        UrLog("pisu okus");
        $(By.name("user[password]")).shouldHave("Password").setValue(TestInterfaceHelper.PWD);
//        UrLog("jdu do smycky");
        if ($(By.xpath("/html/body/div[1]/div[2]/form/div[2]/div[4]/div/label/input")).exists()) {
            UrLog("Majority check found");
        } else {
            UrLog("Majority check not found");
            System.exit(1);
        }

        UrLog("klikam na create new account");
        $(By.name("commit")).shouldHave("CREATE NEW ACCOUNT").click();
        $(By.partialLinkText("already been taken"));
//        UrLog("Create new account done");
//        $$(screenshot("NWC"));
        sleep(15000);
    }

}
