package com.exercise6_fems_automation.test;

import com.exercise6_fems_automation.pages.LoginPage;
import com.exercise6_fems_automation.pages.ProfilePage;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(2)
public class ProfileTest extends BaseTest {

    @Test
    @DisplayName("Testing Cập nhật thông tin cá nhân và Upload ảnh")
    void testUpdateProfileAndUpload() {

        driver.get("http://localhost:8080/FEMS_Mockup/login.jsp");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin", "12345");   // sửa password nếu hệ thống dùng admin/admin

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("profile.jsp"));

        ProfilePage profilePage = new ProfilePage(driver);

        String absolutePath = new java.io.File("src/test/resources/charlie.jpg").getAbsolutePath();

        profilePage.updateProfile("Tuan", absolutePath);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                org.openqa.selenium.By.tagName("body")));

        assertTrue(profilePage.getMessageText().toLowerCase().contains("thành công"));
    }
}
