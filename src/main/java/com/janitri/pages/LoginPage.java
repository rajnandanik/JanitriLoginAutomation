package com.janitri.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.List;

public class LoginPage {
    WebDriver driver;

    @FindBy(name = "email")
    WebElement userId;

    @FindBy(name = "password")
    WebElement password;

    @FindBy(tagName = "button")
    WebElement loginButton;

    // Additional locators for better element identification
    @FindBy(xpath = "//button[@type='submit']")
    WebElement submitButton;

    // Use more reliable locators that work with the actual page
    @FindBy(name = "email")
    WebElement emailInput;

    @FindBy(name = "password")
    WebElement passwordInput;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Basic input methods
    public void enterUserId(String user) {
        userId.clear();
        userId.sendKeys(user);
    }

    public void enterPassword(String pass) {
        password.clear();
        password.sendKeys(pass);
    }

    public void clickLogin() {
        try {
            // Use JavaScript click to avoid interception
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        } catch (Exception e) {
            // Fallback to regular click
            loginButton.click();
        }
    }

    public void clickSubmit() {
        try {
            // Use JavaScript click to avoid interception
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
        } catch (Exception e) {
            // Fallback to regular click
            submitButton.click();
        }
    }

    // Test Case TC001: Login with valid credentials
    public void loginWithValidCredentials(String email, String password) {
        enterUserId(email);
        enterPassword(password);
        clickLogin();
    }

    // Test Case TC002: Login with invalid email
    public void loginWithInvalidEmail(String invalidEmail, String password) {
        enterUserId(invalidEmail);
        enterPassword(password);
        clickLogin();
    }

    // Test Case TC003: Login with invalid password
    public void loginWithInvalidPassword(String email, String invalidPassword) {
        enterUserId(email);
        enterPassword(invalidPassword);
        clickLogin();
    }

    // Test Case TC004: Login with both fields empty
    public void loginWithEmptyFields() {
        enterUserId("");
        enterPassword("");
        clickLogin();
    }

    // Test Case TC005: Login with only email
    public void loginWithOnlyEmail(String email) {
        enterUserId(email);
        enterPassword("");
        clickLogin();
    }

    // Test Case TC006: Login with only password
    public void loginWithOnlyPassword(String password) {
        enterUserId("");
        enterPassword(password);
        clickLogin();
    }

    // Test Case TC007: Password field masks input
    public boolean isPasswordMasked() {
        return password.getAttribute("type").equals("password");
    }

    // Test Case TC008 & TC009: Toggle password visibility
    public void togglePasswordVisibility() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Try multiple possible selectors for the password toggle button
            List<String> possibleSelectors = List.of(
                    "//button[contains(@class, 'eye') or contains(@class, 'toggle') or contains(@class, 'show')]",
                    "//button[.//*[name()='svg']]",
                    "//span[contains(@class, 'eye') or contains(@class, 'toggle')]",
                    "//i[contains(@class, 'eye') or contains(@class, 'fa-eye')]",
                    "//*[contains(@class, 'password')]//*[contains(@class, 'toggle') or contains(@class, 'eye')]",
                    "//div[contains(@class, 'password')]//button",
                    "//*[@type='button' and contains(@aria-label, 'password') or contains(@title, 'password')]",
                    "//*[contains(@class, 'password-toggle')]",
                    "//*[contains(@class, 'show-password')]"
            );

            WebElement toggle = null;
            for (String selector : possibleSelectors) {
                try {
                    toggle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                    break;
                } catch (TimeoutException e) {
                    // Continue to next selector
                }
            }

            if (toggle != null) {
                toggle.click();
                System.out.println("Password toggle clicked successfully");
            } else {
                System.out.println("Password toggle button not found");
            }
        } catch (Exception e) {
            System.out.println("Could not toggle password visibility: " + e.getMessage());
        }
    }

    // Test Case TC010: Button disabled when fields are empty
    public boolean isLoginButtonEnabled() {
        return loginButton.isEnabled();
    }

    public boolean isLoginButtonDisabled() {
        return !loginButton.isEnabled();
    }

    // Test Case TC011: Email input accepts text
    public void enterEmailText(String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
            emailField.clear();
            emailField.sendKeys(text);
        } catch (Exception e) {
            System.out.println("Could not enter email text: " + e.getMessage());
        }
    }

    // Test Case TC012: Password input accepts text
    public void enterPasswordText(String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
            passwordField.clear();
            passwordField.sendKeys(text);
        } catch (Exception e) {
            System.out.println("Could not enter password text: " + e.getMessage());
        }
    }

    // Test Case TC013: Email field shows validation on invalid format
    public String getEmailValidationError() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            
            // Try to find email validation error
            List<String> emailErrorSelectors = List.of(
                    "//*[contains(text(), 'invalid email') or contains(text(), 'Invalid email')]",
                    "//*[contains(text(), 'email format') or contains(text(), 'Email format')]",
                    "//*[contains(text(), 'valid email') or contains(text(), 'Valid email')]",
                    "//*[contains(@class, 'error') and contains(text(), 'email')]",
                    "//*[contains(@class, 'validation') and contains(text(), 'email')]"
            );

            for (String selector : emailErrorSelectors) {
                try {
                    WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selector)));
                    return error.getText().trim();
                } catch (TimeoutException e) {
                    // Continue to next selector
                }
            }
            
            return "No email validation error found";
        } catch (Exception e) {
            return "Error checking email validation: " + e.getMessage();
        }
    }

    // Test Case TC014: Fields are cleared on reload
    public void reloadPage() {
        driver.navigate().refresh();
        waitForPageLoad();
    }

    public String getEmailFieldValue() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
            return emailField.getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    public String getPasswordFieldValue() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
            return passwordField.getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    // Test Case TC015: Login with long email and password
    public void loginWithLongCredentials(String longEmail, String longPassword) {
        enterUserId(longEmail);
        enterPassword(longPassword);
        clickLogin();
    }

    // Test Case TC016: Eye icon is present
    public boolean isEyeIconPresent() {
        try {
            List<String> eyeIconSelectors = List.of(
                    "//*[contains(@class, 'eye')]",
                    "//*[contains(@class, 'toggle')]",
                    "//*[contains(@class, 'show')]",
                    "//*[contains(@class, 'password-toggle')]",
                    "//*[contains(@class, 'show-password')]",
                    "//*[contains(@aria-label, 'password')]",
                    "//*[contains(@title, 'password')]"
            );

            for (String selector : eyeIconSelectors) {
                try {
                    WebElement eyeIcon = driver.findElement(By.xpath(selector));
                    if (eyeIcon.isDisplayed()) {
                        return true;
                    }
                } catch (NoSuchElementException e) {
                    // Continue to next selector
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // Test Case TC017: Submit with Enter key
    public void submitWithEnterKey() {
        password.sendKeys(Keys.ENTER);
    }

    // Test Case TC018: Error disappears on correct input
    public void clearFieldsAndEnterCorrectInput(String correctEmail, String correctPassword) {
        enterUserId("");
        enterPassword("");
        enterUserId(correctEmail);
        enterPassword(correctPassword);
    }

    // Error message methods
    public String getErrorMessage() {
        try {
            // First check if notification dialog is blocking the page
            if (driver.getPageSource().contains("To proceed to the login page please allow")) {
                handleNotificationDialog();
                return "Notification permission required - please allow notifications and reload the page";
            }
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Try multiple possible error message selectors
            List<String> errorSelectors = List.of(
                    "//p[contains(text(),'Invalid') or contains(text(),'invalid')]",
                    "//div[contains(@class, 'error') or contains(@class, 'alert')]",
                    "//span[contains(@class, 'error') or contains(@class, 'invalid')]",
                    "//*[contains(text(), 'credentials') or contains(text(), 'Credentials')]",
                    "//*[contains(text(), 'wrong') or contains(text(), 'incorrect')]",
                    "//p[@class='normal-text']",
                    "//*[contains(@class, 'message') and (contains(text(), 'Invalid') or contains(text(), 'Error'))]",
                    "//*[contains(text(), 'failed') or contains(text(), 'Failed')]",
                    "//*[contains(text(), 'not found') or contains(text(), 'Not found')]"
            );

            for (String selector : errorSelectors) {
                try {
                    WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selector)));
                    return error.getText().trim();
                } catch (TimeoutException e) {
                    // Continue to next selector
                }
            }

            // If no specific error found, check for any error text
            return getAnyErrorText();

        } catch (Exception e) {
            return "No error message found: " + e.getMessage();
        }
    }

    public String getEmptyFieldsError() {
        try {
            // First check if notification dialog is blocking the page
            if (driver.getPageSource().contains("To proceed to the login page please allow")) {
                handleNotificationDialog();
                return "Notification permission required - please allow notifications and reload the page";
            }
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Try multiple possible validation error selectors
            List<String> validationSelectors = List.of(
                    "//p[contains(text(), 'required') or contains(text(), 'Required')]",
                    "//span[contains(text(), 'required') or contains(text(), 'Required')]",
                    "//div[contains(text(), 'Please enter') or contains(text(), 'please enter')]",
                    "//*[contains(text(), 'field') and contains(text(), 'required')]",
                    "//*[contains(text(), 'email') and contains(text(), 'required')]",
                    "//*[contains(text(), 'password') and contains(text(), 'required')]",
                    "//small[contains(@class, 'error') or contains(@class, 'invalid')]",
                    "//*[contains(@class, 'validation') or contains(@class, 'field-error')]",
                    "//*[contains(text(), 'cannot be empty') or contains(text(), 'Cannot be empty')]"
            );

            for (String selector : validationSelectors) {
                try {
                    WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selector)));
                    return error.getText().trim();
                } catch (TimeoutException e) {
                    // Continue to next selector
                }
            }

            // If no validation error found, return a descriptive message
            return getAnyErrorText();

        } catch (Exception e) {
            return "No validation error found: " + e.getMessage();
        }
    }

    private String getAnyErrorText() {
        try {
            // Wait a bit for any error to appear
            Thread.sleep(2000);

            // Look for any error-like elements
            List<WebElement> possibleErrors = driver.findElements(By.xpath(
                    "//*[contains(@class, 'error') or contains(@class, 'alert') or contains(@class, 'message') or " +
                            "contains(@class, 'invalid') or contains(@class, 'validation') or " +
                            "contains(text(), 'Invalid') or contains(text(), 'required') or contains(text(), 'Please')]"
            ));

            if (!possibleErrors.isEmpty()) {
                for (WebElement error : possibleErrors) {
                    String text = error.getText().trim();
                    if (!text.isEmpty()) {
                        return text;
                    }
                }
            }

            // Check if we're still on login page (no navigation occurred)
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("login") || currentUrl.contains("dev-dash.janitri.in")) {
                return "Login failed - remained on login page";
            }

            return "No specific error message found";

        } catch (Exception e) {
            return "Error checking for messages: " + e.getMessage();
        }
    }

    public boolean isOnLoginPage() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("dev-dash.janitri.in") &&
                (currentUrl.contains("login") || currentUrl.equals("https://dev-dash.janitri.in/"));
    }

    public void waitForPageLoad() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // First, check if we need to handle notification permission
            if (driver.getPageSource().contains("notifications") || 
                driver.getPageSource().contains("Notifications") ||
                driver.getPageSource().contains("To proceed to the login page please allow")) {
                
                System.out.println("Notification permission dialog detected, handling...");
                
                // Try to find and click "Allow" button with more robust selectors
                String[] allowSelectors = {
                    "//button[contains(text(), 'Allow')]",
                    "//button[contains(text(), 'Allow notifications')]",
                    "//button[contains(@aria-label, 'Allow')]",
                    "//button[contains(@title, 'Allow')]",
                    "//div[contains(@class, 'notification')]//button[contains(text(), 'Allow')]",
                    "//*[contains(text(), 'Allow') and contains(@role, 'button')]",
                    "//*[contains(text(), 'Allow')]",
                    "//*[contains(text(), 'Allow')]//ancestor::button",
                    "//button[contains(., 'Allow') or contains(., 'allow')]",
                    "//*[contains(text(), 'Allow')]//parent::button"
                };
                
                for (String selector : allowSelectors) {
                    try {
                        WebElement allowButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                        // Use JavaScript click to avoid interception
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", allowButton);
                        System.out.println("Clicked Allow for notifications using selector: " + selector);
                        Thread.sleep(2000);
                        break;
                    } catch (Exception e) {
                        // Continue to next selector
                    }
                }
                
                // If there's a reload button, click it
                String[] reloadSelectors = {
                    "//button[contains(text(), 'Reload')]",
                    "//a[contains(text(), 'Reload')]",
                    "//*[contains(text(), 'Reload')]",
                    "//*[contains(text(), 'reload')]",
                    "//*[contains(text(), 'Reload')]//ancestor::button",
                    "//button[contains(., 'Reload') or contains(., 'reload')]",
                    "//*[contains(text(), 'Reload')]//parent::button"
                };
                
                for (String selector : reloadSelectors) {
                    try {
                        WebElement reloadButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                        // Use JavaScript click to avoid interception
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", reloadButton);
                        System.out.println("Clicked Reload button using selector: " + selector);
                        Thread.sleep(3000);
                        break;
                    } catch (Exception e) {
                        // Continue to next selector
                    }
                }
                
                // Wait a bit more to ensure the page is fully loaded after notification handling
                Thread.sleep(2000);
            }
            
            // Wait for essential elements to be present AND interactable
            try {
                // Wait for email field to be both present and interactable
                WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
                System.out.println("Email field is ready for interaction");
                
                // Wait for password field to be both present and interactable
                WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
                System.out.println("Password field is ready for interaction");
                
                // Wait for login button to be clickable
                WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.tagName("button")));
                System.out.println("Login button is ready for interaction");
                
                System.out.println("Page loaded successfully - all login form elements are interactable");
            } catch (Exception e) {
                System.out.println("Could not find all login form elements: " + e.getMessage());
                // Continue anyway, as the page might still be functional
            }
            
        } catch (Exception e) {
            System.out.println("Error waiting for page load: " + e.getMessage());
            // Continue anyway, as the page might still be functional
        }
    }

    // Additional utility methods
    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void clearAllFields() {
        enterUserId("");
        enterPassword("");
    }

    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementVisible(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Method to check and handle notification dialog
    public boolean handleNotificationDialog() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Check if notification dialog is still present
            if (driver.getPageSource().contains("To proceed to the login page please allow")) {
                System.out.println("Notification dialog still present, trying to handle...");
                
                // Try multiple approaches to handle the notification
                String[] allowSelectors = {
                    "//button[contains(text(), 'Allow')]",
                    "//button[contains(text(), 'Allow notifications')]",
                    "//*[contains(text(), 'Allow')]",
                    "//button[contains(., 'Allow')]",
                    "//*[contains(text(), 'Allow')]//ancestor::button"
                };
                
                for (String selector : allowSelectors) {
                    try {
                        WebElement allowButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", allowButton);
                        System.out.println("Clicked Allow using selector: " + selector);
                        Thread.sleep(2000);
                        
                        // Try to find and click reload button
                        String[] reloadSelectors = {
                            "//button[contains(text(), 'Reload')]",
                            "//*[contains(text(), 'Reload')]",
                            "//button[contains(., 'Reload')]"
                        };
                        
                        for (String reloadSelector : reloadSelectors) {
                            try {
                                WebElement reloadButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(reloadSelector)));
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", reloadButton);
                                System.out.println("Clicked Reload using selector: " + reloadSelector);
                                Thread.sleep(3000);
                                return true;
                            } catch (Exception e) {
                                // Continue to next selector
                            }
                        }
                        return true;
                    } catch (Exception e) {
                        // Continue to next selector
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error handling notification dialog: " + e.getMessage());
            return false;
        }
    }
}







