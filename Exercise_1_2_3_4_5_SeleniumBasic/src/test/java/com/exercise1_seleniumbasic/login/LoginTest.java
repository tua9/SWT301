package com.exercise1_seleniumbasic.login;

import com.exercise1_seleniumbasic.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Login Tests for the-internet.herokuapp.com")
public class LoginTest {

    static WebDriver driver;
    static WebDriverWait wait;

    @BeforeAll
    static void setUp() {
        //WebDriverManager.chromedriver().setup(); // Tự động tải và cấu hình

        //disable JavaScript in Chrome
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.managed_default_content_settings.javascript", 2); //1: Cho phép (default), 2: Chặn JavaScript
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--incognito"); // Ẩn danh

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Thêm WebDriverWait
        driver.manage().window().maximize();
    }

    @Test
    @Order(1)
    @DisplayName("Should login successfully with valid credentials")
    void testLoginSuccess() {
        driver.get("https://the-internet.herokuapp.com/login");

        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        // Chờ thông báo thành công hiển thị
        WebElement successMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".flash.success"))
        );

        //String successMsg = driver.findElement(By.cssSelector(".flash.success")).getText();
        assertTrue(successMsg.getText().contains("You logged into a secure area!"));
    }

    @Test
    @Order(2)
    @DisplayName("Should display error when logging in with invalid credentials")
    void testLoginFail() {
        driver.get("https://the-internet.herokuapp.com/login");

        driver.findElement(By.id("username")).sendKeys("invalid");
        driver.findElement(By.id("password")).sendKeys("wrongpassword");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Chờ thông báo lỗi hiển thị
        WebElement errorMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".flash.error"))
        );

        assertTrue(errorMsg.getText().contains("Your username is invalid!"));
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @ParameterizedTest(name = "CSV File: {0} / {1}")
    @CsvFileSource(resources = "/login-data.csv", numLinesToSkip = 1)
    void testLoginFromCSV(String username, String password, String expected) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();

        username = (username == null) ? "" : username.trim();
        password = (password == null) ? "" : password.trim();

        loginPage.login(username, password);

        if (expected.equalsIgnoreCase("success")) {

            WebElement successMsg = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".flash.success")
                    )
            );

            assertTrue(successMsg.getText()
                    .contains("You logged into a secure area!"));

        } else {

            WebElement errorMsg = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".flash.error")
                    )
            );

            assertTrue(errorMsg.getText()
                    .toLowerCase()
                    .contains("invalid")
                    || errorMsg.getText()
                            .toLowerCase()
                            .contains("password"));

        }
    }

}
