package utils;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataAnalyzer {

    public static void analyzeFlights(List<FlightData> flights) {
        if (flights.isEmpty()) {
            System.out.println("Analiz edilecek uçuş verisi bulunamadı!");
            return;
        }

        Map<String, DoubleSummaryStatistics> stats = flights.stream()
                .collect(Collectors.groupingBy(FlightData::getAirline,
                        Collectors.summarizingDouble(FlightData::getPrice)));

        stats.forEach((airline, stat) -> {
            System.out.printf("Firma: %-15s | Min: %7.2f TL | Max: %7.2f TL | Ortalama: %7.2f TL\n",
                    airline, stat.getMin(), stat.getMax(), stat.getAverage());
        });


        FlightData bestFlight = flights.stream()
                .min(Comparator.comparingDouble(FlightData::getPrice))
                .orElse(null);

        if (bestFlight != null) {
            System.out.println("Önerilen Uçuş: " + bestFlight.getAirline() +
                    " | Kalkış: " + bestFlight.getDepartureTime() +
                    " | Süre: " + bestFlight.getDuration() +
                    " | Fiyat: " + bestFlight.getPrice() + " TL");
        }
    }
}