package com.exercise6_fems_automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Khai báo các Locator (Id, Name, CSS) [cite: 295, 296]
    private By userField = By.id("username");
    private By passField = By.id("password");
    private By loginBtn = By.cssSelector("button[type='submit']");

    public void login(String user, String pass) {

        if (user != null) {
            type(userField, user);
        }

        if (pass != null) {
            type(passField, pass);
        }

        click(loginBtn);
    }
}
