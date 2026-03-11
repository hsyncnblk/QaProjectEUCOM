package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    public static void writeToCSV(List<FlightData> flightList, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Havayolu,Kalkis_Saati,Ucus_Suresi,Fiyat\n");

            for (FlightData flight : flightList) {
                writer.append(flight.toString()).append("\n");
            }
            System.out.println("Başarılı: Uçuş verileri " + filePath + " dosyasına kaydedildi!");
        } catch (IOException e) {
            System.out.println("HATA: CSV dosyası oluşturulamadı - " + e.getMessage());
        }
    }
}