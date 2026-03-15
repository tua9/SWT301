package com.exercise6_fems_automation.test;

import com.exercise6_fems_automation.pages.ProfilePage;
import com.exercise6_fems_automation.pages.LoginPage;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FemsTest extends BaseTest {

    @Order(1)
    @ParameterizedTest(name = "Test Login - {0}")
    @CsvFileSource(resources = "/login-data.csv", numLinesToSkip = 1)
    void testLogin(String user, String pass, String expected) {

        test = extent.createTest("Kiểm thử Đăng nhập: " + user);

        driver.get("http://localhost:8080/FEMS_Mockup/login.jsp");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user, pass);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        if (expected.equals("success")) {

            wait.until(ExpectedConditions.urlContains("profile.jsp"));

            assertTrue(driver.getCurrentUrl().contains("profile.jsp"));

            test.pass("Đăng nhập thành công và chuyển hướng đúng");

        } else {

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    org.openqa.selenium.By.tagName("body")));

            assertTrue(driver.getPageSource().contains("Invalid"));

            test.pass("Hệ thống hiển thị thông báo lỗi đúng mong đợi");
        }
    }

    @Order(2)
    @Test
    void testUpdateProfileAndUpload() {

        test = extent.createTest("Kiểm thử Cập nhật Profile và Upload ảnh");

        driver.get("http://localhost:8080/FEMS_Mockup/profile.jsp");

        ProfilePage profilePage = new ProfilePage(driver);

        String imgPath = new java.io.File("src/test/resources/charlie.jpg").getAbsolutePath();

        profilePage.updateProfile("Tuan", imgPath);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                org.openqa.selenium.By.tagName("body")));

        assertTrue(profilePage.getMessageText().contains("thành công"));

        test.pass("Cập nhật profile và upload ảnh thành công");
    }
}
