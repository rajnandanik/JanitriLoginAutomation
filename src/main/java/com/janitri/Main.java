package com.janitri;

import com.janitri.base.BaseTest;
import com.janitri.pages.LoginPage;
import org.openqa.selenium.WebDriver;

/**
 * Main class to demonstrate the Janitri Login Automation Framework
 * This class shows how to use the Page Object Model and test the login functionality
 */
public class Main extends BaseTest {

    public static void main(String[] args) {
        Main main = new Main();
        main.runDemo();
    }

    public void runDemo() {
        try {
            System.out.println("=== Janitri Login Automation Demo ===");
            
            // Initialize the page object
            LoginPage loginPage = new LoginPage(driver);
            loginPage.waitForPageLoad();
            
            System.out.println("Page loaded successfully");
            System.out.println("Current URL: " + loginPage.getCurrentUrl());
            System.out.println("Page Title: " + loginPage.getPageTitle());
            
            // Demo: Test password masking
            System.out.println("\n--- Testing Password Masking ---");
            boolean isMasked = loginPage.isPasswordMasked();
            System.out.println("Password is masked: " + isMasked);
            
            // Demo: Test input functionality
            System.out.println("\n--- Testing Input Functionality ---");
            loginPage.enterUserId("demo@example.com");
            loginPage.enterPassword("demopassword123");
            System.out.println("Email entered: " + loginPage.getEmailFieldValue());
            System.out.println("Password entered: " + loginPage.getPasswordFieldValue());
            
            // Demo: Test login button
            System.out.println("\n--- Testing Login Button ---");
            boolean buttonEnabled = loginPage.isLoginButtonEnabled();
            System.out.println("Login button enabled: " + buttonEnabled);
            
            // Demo: Test eye icon presence
            System.out.println("\n--- Testing Eye Icon ---");
            boolean eyeIconPresent = loginPage.isEyeIconPresent();
            System.out.println("Eye icon present: " + eyeIconPresent);
            
            // Demo: Test invalid login
            System.out.println("\n--- Testing Invalid Login ---");
            loginPage.loginWithInvalidEmail("invalid@example.com", "wrongpassword");
            String errorMessage = loginPage.getErrorMessage();
            System.out.println("Error message: " + errorMessage);
            
            // Demo: Test empty fields
            System.out.println("\n--- Testing Empty Fields ---");
            loginPage.loginWithEmptyFields();
            String validationError = loginPage.getEmptyFieldsError();
            System.out.println("Validation error: " + validationError);
            
            System.out.println("\n=== Demo Completed Successfully ===");
            
        } catch (Exception e) {
            System.err.println("Error during demo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Clean up
            if (driver != null) {
                driver.quit();
                System.out.println("Browser closed");
            }
        }
    }
}