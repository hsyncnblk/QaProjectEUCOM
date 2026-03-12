# ✈️ Web UI Automation & Flight Data Analysis Suite

This project is a professional test automation framework designed to automate flight search, filtering, and passenger data entry workflows on a travel portal (Enuygun). Bridging the gap between strict Quality Assurance and Data Analysis, this suite goes beyond standard UI validation by extracting, processing, and visualizing complex flight datasets.

---

## 🚀 Tech Stack & Architecture
The framework is built with modern QA engineering standards, utilizing the following technologies:

* **Language:** Java 17
* **Automation:** Selenium WebDriver (4.18.1)
* **BDD Framework:** Cucumber
* **Build Tool:** Maven
* **Reporting & Visualization:** Allure Report & Custom HTML Charts
* **Logging:** Log4j2
* **Design Pattern:** Page Object Model (POM)

---

## 📊 Data Analytics & Reporting
Rather than just executing pass/fail assertions, this project features built-in data processing capabilities:
* **Airline Price Analysis:** Dynamically calculates Minimum, Maximum, and Average flight prices grouped by airline.
* **Data Extraction:** Automatically exports the scraped flight data into a `target/flight_data_report.csv` file for further downstream analysis.

---

## 🛠️ How to Run
To execute the test suite and generate reports locally, run the following Maven commands in your terminal:

## Install Dependencies:**
1. `mvn clean install`
2. `mvn test`
3. `mvn allure:serve`

---

# 📸 Showcase / Screenshots

### Test Report - 1
> ![Allure Report 1](photos/Ekran%20görüntüsü%202026-03-11%20190351.png)

### Test Report - 2
> ![Allure Report 2](photos/Ekran%20görüntüsü%202026-03-11%20190401.png)

### Test Report - 3
> ![Test Result 1](photos/Ekran%20görüntüsü%202026-03-11%20222609.png)

### Test Report - 4
> ![Test Result 2](photos/Ekran%20görüntüsü%202026-03-11%20223344.png)


