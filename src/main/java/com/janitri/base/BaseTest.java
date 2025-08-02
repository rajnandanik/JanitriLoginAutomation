package com.janitri.base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.JavascriptExecutor;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        try {
            // Suppress CDP warnings
            Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
            System.setProperty("webdriver.chrome.silentOutput", "true");
            
            // Clear any system properties that might interfere
            System.clearProperty("webdriver.chrome.driver");

            System.out.println("Setting up ChromeDriver with Selenium Manager...");

            // Set up Chrome options for better stability and performance
            ChromeOptions options = new ChromeOptions();
            
            // Essential options for stability
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-allow-origins=*");
            
            // Handle notifications permission
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            
            // Suppress CDP warnings
            options.addArguments("--disable-logging");
            options.addArguments("--log-level=3");
            options.addArguments("--silent");

            // Let Selenium Manager handle ChromeDriver automatically
            driver = new ChromeDriver(options);

            System.out.println("ChromeDriver initialized successfully");

            // Set faster timeouts for better performance
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));

            // Maximize window and navigate
            driver.manage().window().maximize();

            System.out.println("Navigating to: https://dev-dash.janitri.in/");
            driver.get("https://dev-dash.janitri.in/");

            // Handle notification permission if needed
            handleNotificationPermission();

            // Wait a bit for page to fully load
            Thread.sleep(2000);

            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page title: " + driver.getTitle());

        } catch (Exception e) {
            System.err.println("Error in setUp: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
    }

    private void handleNotificationPermission() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Check if notification permission dialog appears
            if (driver.getPageSource().contains("notifications") || 
                driver.getPageSource().contains("Notifications") ||
                driver.getPageSource().contains("To proceed to the login page please allow")) {
                
                System.out.println("Handling notification permission...");
                
                // Try to find and click "Allow" button with more robust selectors
                try {
                    // Common selectors for notification permission buttons
                    String[] allowSelectors = {
                        "//button[contains(text(), 'Allow')]",
                        "//button[contains(text(), 'Allow notifications')]",
                        "//button[contains(@aria-label, 'Allow')]",
                        "//button[contains(@title, 'Allow')]",
                        "//div[contains(@class, 'notification')]//button[contains(text(), 'Allow')]",
                        "//*[contains(text(), 'Allow') and contains(@role, 'button')]",
                        "//*[contains(text(), 'Allow')]",
                        "//button[contains(., 'Allow')]",
                        "//*[contains(text(), 'Allow')]//ancestor::button",
                        "//button[contains(., 'Allow') or contains(., 'allow')]"
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
                    try {
                        String[] reloadSelectors = {
                            "//button[contains(text(), 'Reload')]",
                            "//a[contains(text(), 'Reload')]",
                            "//*[contains(text(), 'Reload')]",
                            "//*[contains(text(), 'reload')]",
                            "//button[contains(., 'Reload')]",
                            "//*[contains(text(), 'Reload')]//ancestor::button",
                            "//button[contains(., 'Reload') or contains(., 'reload')]"
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
                    } catch (Exception e) {
                        System.out.println("No reload button found");
                    }
                    
                } catch (Exception e) {
                    System.out.println("Could not handle notification permission: " + e.getMessage());
                }
            }
            
            // Wait a bit more to ensure the page is fully loaded after notification handling
            Thread.sleep(2000);
            
        } catch (Exception e) {
            System.out.println("Error handling notification permission: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Driver closed successfully");
            } catch (Exception e) {
                System.out.println("Error closing driver: " + e.getMessage());
            }
        }
    }
}