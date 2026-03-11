package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HtmlReportGenerator {

    public static void generateReport(List<FlightData> flights, String filePath) {
        if (flights.isEmpty()) return;

        // 1. Havayolu bazında Min/Max/Avg verilerini hazırla
        Map<String, DoubleSummaryStatistics> stats = flights.stream()
                .collect(Collectors.groupingBy(FlightData::getAirline,
                        Collectors.summarizingDouble(FlightData::getPrice)));

        StringBuilder labels = new StringBuilder();
        StringBuilder minData = new StringBuilder();
        StringBuilder maxData = new StringBuilder();
        StringBuilder avgData = new StringBuilder();

        for (Map.Entry<String, DoubleSummaryStatistics> entry : stats.entrySet()) {
            labels.append("'").append(entry.getKey()).append("',");
            minData.append(entry.getValue().getMin()).append(",");
            maxData.append(entry.getValue().getMax()).append(",");
            avgData.append(entry.getValue().getAverage()).append(",");
        }

        // 2. Heatmap (Saat/Fiyat Dağılımı) için verileri hazırla
        StringBuilder scatterData = new StringBuilder();
        for (FlightData f : flights) {
            // Saati (Örn: 15:05) ondalık sayıya çevir (15.08) grafikte göstermek için
            String[] timeParts = f.getDepartureTime().split(":");
            double timeValue = Double.parseDouble(timeParts[0]) + (Double.parseDouble(timeParts[1]) / 60.0);
            scatterData.append("{x: ").append(timeValue).append(", y: ").append(f.getPrice()).append("},");
        }

        // 3. HTML ve Chart.js Kodunu İnşa Et
        String htmlContent = "<!DOCTYPE html>\n<html lang='tr'>\n<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <title>Uçuş Veri Analiz Raporu</title>\n" +
                "    <script src='https://cdn.jsdelivr.net/npm/chart.js'></script>\n" +
                "    <style>body{font-family: Arial, sans-serif; background:#f4f7f6; padding: 20px;} .container{width: 80%; margin: auto; background: white; padding: 20px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); border-radius: 8px;} h2{text-align: center; color: #333;} .chart-wrapper{margin-bottom: 50px;}</style>\n" +
                "</head>\n<body>\n" +
                "<div class='container'>\n" +
                "    <h2>✈️ Uçuş Fiyat Analizi Raporu</h2>\n" +
                "    \n" +
                "    <div class='chart-wrapper'>\n" +
                "        <canvas id='barChart'></canvas>\n" +
                "    </div>\n" +
                "    \n" +
                "    <div class='chart-wrapper'>\n" +
                "        <h3 style='text-align:center;'>🔥 Fiyat Dağılımı (Isı Haritası Simülasyonu)</h3>\n" +
                "        <canvas id='scatterChart'></canvas>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "<script>\n" +
                "    // --- 1. BAR CHART (MIN/MAX/AVG) ---\n" +
                "    new Chart(document.getElementById('barChart'), {\n" +
                "        type: 'bar',\n" +
                "        data: {\n" +
                "            labels: [" + labels + "],\n" +
                "            datasets: [\n" +
                "                { label: 'Min Fiyat', backgroundColor: '#2ecc71', data: [" + minData + "] },\n" +
                "                { label: 'Ortalama Fiyat', backgroundColor: '#3498db', data: [" + avgData + "] },\n" +
                "                { label: 'Max Fiyat', backgroundColor: '#e74c3c', data: [" + maxData + "] }\n" +
                "            ]\n" +
                "        },\n" +
                "        options: { plugins: { title: { display: true, text: 'Havayolu Bazlı Fiyat Karşılaştırması' } } }\n" +
                "    });\n" +
                "\n" +
                "    // --- 2. SCATTER CHART (HEATMAP / ZAMAN DAĞILIMI) ---\n" +
                "    new Chart(document.getElementById('scatterChart'), {\n" +
                "        type: 'scatter',\n" +
                "        data: {\n" +
                "            datasets: [{\n" +
                "                label: 'Uçuşlar (Saat vs Fiyat)',\n" +
                "                backgroundColor: 'rgba(255, 99, 132, 0.6)',\n" +
                "                pointRadius: 6,\n" +
                "                data: [" + scatterData + "]\n" +
                "            }]\n" +
                "        },\n" +
                "        options: {\n" +
                "            scales: {\n" +
                "                x: { title: { display: true, text: 'Günün Saatleri (00:00 - 24:00)' }, min: 0, max: 24 },\n" +
                "                y: { title: { display: true, text: 'Fiyat (TL)' } }\n" +
                "            }\n" +
                "        }\n" +
                "    });\n" +
                "</script>\n" +
                "</body>\n</html>";

        // HTML'i dosyaya yaz
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(htmlContent);
            System.out.println("Başarılı: Görsel HTML Raporu oluşturuldu -> " + filePath);
        } catch (IOException e) {
            System.out.println("HATA: HTML Raporu yazılamadı!");
        }
    }
}