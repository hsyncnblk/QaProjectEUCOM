# ✈️ Web UI Automation & Flight Data Analysis Suite

Bu proje, bir seyahat portalı (Enuygun) üzerinde uçuş arama, filtreleme, yolcu bilgileri girişi ve gelişmiş veri analizi süreçlerini otomatize etmek için geliştirilmiş profesyonel bir test otomasyon framework'üdür.

---

## 🚀 Kullanılan Teknolojiler
Proje, modern QA standartlarına uygun olarak aşağıdaki teknoloji yığını ile inşa edilmiştir:

* **Dil:** Java 17
* **Otomasyon:** Selenium WebDriver (4.18.1)
* **Framework:** Cucumber BDD
* **Build Tool:** Maven
* **Raporlama:** Allure Report & Custom HTML Charts
* **Loglama:** Log4j2
* **Tasarım Deseni:** Page Object Model (POM)

---

## 📊 Analiz ve Raporlama 
Proje sadece test koşmakla kalmaz, aynı zamanda çekilen veriler üzerinden derinlemesine analiz yapar:
* **Havayolu Analizi:** Her havayolu firması için Min, Max ve Ortalama fiyat hesaplamaları.
* **Veri Aktarımı:** Tüm uçuş verileri `target/flight_data_report.csv` dosyasına kaydedilir.

---

## 🛠️ Projeyi Çalıştırma
1. `mvn clean install`
2. `mvn test`
3. `mvn allure:serve`

---

# 📸 Ekran Görüntüleri

### Allure Test Raporu - 1
> ![Allure Report 1](photos/Ekran%20görüntüsü%202026-03-11%20190351.png)

### Allure Test Raporu - 2
> ![Allure Report 2](photos/Ekran%20görüntüsü%202026-03-11%20190401.png)

### Test Otomasyon Sonuçları - 1
> ![Test Result 1](photos/Ekran%20görüntüsü%202026-03-11%20222609.png)

### Test Otomasyon Sonuçları - 2
> ![Test Result 2](photos/Ekran%20görüntüsü%202026-03-11%20223344.png)


