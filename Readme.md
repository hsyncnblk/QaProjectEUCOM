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

## 📸 Ekran Görüntüleri
### Allure Test Raporu
> ![Allure Report](https://github.com/hsyncnblk/QaProjectEUCOM/blob/main/allure_screenshot.png?raw=true)
> *Test adımlarının ve hata anındaki ekran görüntülerinin merkezi raporu.*

---

## 🛠️ Projeyi Çalıştırma
1. `mvn clean install`
2. `mvn test`
3. `mvn allure:serve`