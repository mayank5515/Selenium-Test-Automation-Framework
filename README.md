# Selenium Test Automation Framework

End-to-end UI test automation framework for an e-commerce web application
([SauceDemo](https://www.saucedemo.com), a public demo store) covering **login, cart and checkout**.

**Tech stack:** Java 17, Selenium WebDriver 4, TestNG, Maven, Extent Reports, Jenkins

## Features
- **Page Object Model** - each page has its own class; tests never touch locators directly
- **Data-driven testing** - TestNG `@DataProvider` for invalid logins and checkout form validation
- **Explicit waits** - central `WaitUtils`, no `Thread.sleep`
- **Config file** - browser, headless mode, URL, timeout in `config.properties` (overridable from the command line)
- **Extent Reports** - HTML report with a **screenshot attached automatically on failure**
- **Jenkins pipeline** - `Jenkinsfile` with a weekday cron schedule and report publishing
- **Thread-safe driver** - `ThreadLocal<WebDriver>`, ready for parallel runs

## Project structure
```
src/main/java/.../config      ConfigReader (reads config.properties)
src/main/java/.../driver      DriverFactory (Chrome / Firefox / Edge)
src/main/java/.../pages       BasePage, LoginPage, InventoryPage, CartPage, CheckoutPage
src/main/java/.../utils       WaitUtils, ScreenshotUtils, ExtentManager
src/main/java/.../listeners   TestListener (reporting + failure screenshots)
src/test/java/.../base        BaseTest (browser start / quit)
src/test/java/.../tests       LoginTest, CartTest, CheckoutTest
testng.xml                    Suite definition
Jenkinsfile                   CI pipeline
```

## Run it
Prerequisites: JDK 17+, Maven 3.8+, Chrome (or Firefox/Edge). The browser driver is downloaded automatically.

```bash
mvn clean test                                  # default: Chrome, visible browser
mvn clean test -Dheadless=true                  # no browser window
mvn clean test -Dbrowser=firefox                # choose browser
```

## Where to see results
- Console: `Tests run: 13, Failures: 0, Errors: 0, Skipped: 0`
- Extent report: `reports/ExtentReport.html`
- Failure screenshots: `reports/screenshots/`
- TestNG/JUnit XML: `target/surefire-reports/`
