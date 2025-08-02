# Janitri Login Automation

A comprehensive Selenium WebDriver automation framework for testing the Janitri login page. This project demonstrates modern automation practices with robust error handling and notification management.

## 🚀 Project Overview

This automation framework successfully tests the Janitri login page with **10 comprehensive test cases**, achieving **100% test pass rate** with robust handling of notification dialogs and browser stability issues.

## 📁 Project Structure

```
JanitriLoginAutomation/
├── src/
│   ├── main/java/com/janitri/
│   │   ├── base/
│   │   │   └── BaseTest.java          # Base test class with WebDriver setup
│   │   ├── pages/
│   │   │   └── LoginPage.java         # Page Object for login page
│   │   └── Main.java                  # Demo main class
│   └── test/java/tests/
│       └── LoginPageTests.java        # Test cases (10 selected tests)
├── pom.xml                            # Maven configuration
├── testng.xml                         # TestNG suite configuration          
└── README.md                          # This file
```

## 🛠️ Technology Stack

- **Java**: 21
- **Selenium WebDriver**: 4.27.0
- **TestNG**: 7.9.0
- **Maven**: Build and dependency management
- **ChromeDriver**: Browser automation (auto-managed)
- **WebDriverManager**: 5.9.2 (automatic driver management)

##  Test Cases Covered (10 Tests - All Passing)

| Test Case | Description | Status | Key Feature |
|-----------|-------------|------|-------------|
| **TC001** | Login with valid credentials |  PASS | Basic login functionality |
| **TC002** | Login with invalid email |  PASS | Error handling |
| **TC003** | Login with invalid password |  PASS | Error handling |
| **TC004** | Login with empty fields |  PASS | Validation testing |
| **TC005** | Password field masks input |  PASS | UI security testing |
| **TC006** | Password visibility toggle |  PASS | UI functionality |
| **TC007** | Login button disabled when fields empty |  PASS | UI state testing |
| **TC008** | Email input accepts text |  PASS | Form functionality |
| **TC009** | Email validation on invalid format | PASS | Input validation |
| **TC010** | All page elements present |  PASS | UI completeness |

##  Key Features

### **Robust Error Handling**
-  **Notification Dialog Management**: Automatically handles notification permission popups
-  **Browser Stability**: Optimized Chrome options to prevent crashes
-  **Element Synchronization**: Explicit waits for reliable element interaction
-  **JavaScript Execution**: Uses JavaScript clicks to avoid interception issues

### **Modern Automation Practices**
-  **Page Object Model**: Clean separation of page elements and test logic
-  **Explicit Waits**: Robust element synchronization with WebDriverWait
-  **Parallel Execution**: Single-thread execution for stability
-  **Comprehensive Logging**: Detailed console output for debugging

### **Cross-Platform Compatibility**
-  **Windows Support**: Tested and optimized for Windows
-  **Chrome Browser**: Latest Chrome version support
-  **Maven Build**: Standardized build process

##  Setup Instructions

### **Prerequisites**
1. **Java 21**: Download and install from Oracle or OpenJDK
2. **Maven**: Download from Apache Maven website
3. **Chrome Browser**: Latest version
4. **Git**: For version control

### **Environment Setup**
```bash
# Set Environment Variables
JAVA_HOME=C:\Program Files\Java\jdk-21
MAVEN_HOME=C:\Program Files\Apache\maven
PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%
```

## ️ Running Tests

### **Using Maven**
```bash
# Run all tests
mvn clean test

# Run specific test
mvn test -Dtest=LoginPageTests#testLoginWithValidCredentials

# Run with detailed output
mvn test -X
```

### **Using IntelliJ IDEA**
1. Open project in IntelliJ IDEA
2. Right-click on `testng.xml`
3. Select "Run 'testng.xml'"

### **Test Execution Results**
```
===============================================
Login Page Test Suite
Total tests run: 10, Passes: 10, Failures: 0, Skips: 0
===============================================
```

##  Technical Implementation

### **Browser Configuration**
- **Chrome Options**: Optimized for stability and performance
- **JavaScript Execution**: Handles notification dialogs automatically
- **Element Synchronization**: Explicit waits for all interactions
- **Error Recovery**: Graceful handling of browser issues

### **Test Framework Features**
- **Page Object Model**: `LoginPage.java` contains all page interactions
- **Base Test Class**: `BaseTest.java` handles WebDriver lifecycle
- **TestNG Integration**: Parallel execution and detailed reporting
- **Maven Integration**: Dependency management and build automation

### **Notification Handling**
The framework automatically detects and handles notification permission dialogs:
```java
// Automatic notification dialog handling
if (driver.getPageSource().contains("To proceed to the login page please allow")) {
    handleNotificationDialog();
}
```

##  Test Results Summary

### **Current Status:  ALL TESTS PASSING**
- **Total Tests**: 10
- **Passed**: 10
- **Failed**: 0
- **Skipped**: 0
- **Success Rate**: 100%

### **Performance Metrics**
- **Browser Stability**: No crashes or session issues
- **Element Synchronization**: All elements load and interact properly
- **Error Handling**: Robust handling of notification dialogs
- **Test Execution**: Reliable and consistent results

##  Troubleshooting

### **Common Issues & Solutions**

| Issue | Solution |
|-------|----------|
| **Maven not found** | Install Maven and set environment variables |
| **ChromeDriver issues** | Framework automatically manages ChromeDriver |
| **Notification popup** | Automatically handled by the framework |
| **Element not found** | Framework uses explicit waits and multiple selectors |
| **Browser crashes** | Optimized Chrome options prevent crashes |

### **Performance Optimizations**
-  **Single-thread execution**: Prevents browser conflicts
-  **Optimized Chrome options**: Faster and more stable
-  **Explicit waits**: Reliable element synchronization
-  **JavaScript clicks**: Avoids element interception issues

##  Future Enhancements

### **Potential Improvements**
- **Cross-browser testing**: Firefox, Safari support
- **Data-driven testing**: Excel/CSV test data
- **Reporting**: HTML test reports
- **CI/CD integration**: Jenkins, GitHub Actions
- **Mobile testing**: Appium integration

##  Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📝 License

This project is for educational and demonstration purposes.

---

**🎉 Project Status: Production Ready with 100% Test Pass Rate!** 