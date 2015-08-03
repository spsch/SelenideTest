package qa;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * Created by svehlak on 3.8.15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Profile extends BaseTest implements TestInterfaceHelper {

    @Test
    public void testAccountHistory() throws IOException, Exception {

        BaseTest.IsNet();
        /**Object OpenURLprofile = new BaseTest().OpenURL();**/
        this.OpenURL();
        this.testALogin();
        $(By.linkText("Profile")).click();
        $(By.linkText("Account History")).click();
        sleep(5000);

    }

}
