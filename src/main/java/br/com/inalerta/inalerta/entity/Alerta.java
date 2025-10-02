
package br.com.inalerta.inalerta.entity;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Alerta {

    @CsvBindByPosition(position = 0)
    private Double latitude;

    @CsvBindByPosition(position = 1)
    private Double longitude;

    @CsvBindByPosition(position = 2)
    private String satelite;

    @CsvBindByPosition(position = 3)
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime data;

    @Override
    public String toString() {
        return "Alerta{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", satelite='" + satelite + '\'' +
                ", data=" + data +
                '}';
    }
}
