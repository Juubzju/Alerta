package br.com.inalerta.inalerta.service;

import br.com.inalerta.inalerta.entity.Alerta;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertaService {


    private static final String INPE_CSV_BASE_URL = "https://dataserver-coids.inpe.br/queimadas/queimadas/focos/csv/10min/";

    private final RestTemplate restTemplate;

    public List<Alerta> getUltimosFocos() {
        try {
//
            String url = buildUrlParaUltimoCsvDisponivel();
            System.out.println("Buscando dados de: " + url);


            String csvData = restTemplate.getForObject(url, String.class);

            if (csvData == null || csvData.isEmpty()) {
                return Collections.emptyList();
            }


            try (StringReader reader = new StringReader(csvData)) {
                return new CsvToBeanBuilder<Alerta>(reader)
                        .withType(Alerta.class)
                        .withSkipLines(1)
                        .build()
                        .parse();
            }

        } catch (Exception e) {

            System.err.println("Erro ao buscar ou processar dados do INPE: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private String buildUrlParaUltimoCsvDisponivel() {

        LocalDateTime agoraUTC = LocalDateTime.now(ZoneId.of("UTC"));


        int minutoAtual = agoraUTC.getMinute();
        int minutoArredondado = (minutoAtual / 10) * 10;

        LocalDateTime dataArquivo = agoraUTC.withMinute(minutoArredondado).withSecond(0).withNano(0);


        dataArquivo = dataArquivo.minusMinutes(10);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
        String nomeArquivo = "focos_10min_" + dataArquivo.format(formatter) + ".csv";

        return INPE_CSV_BASE_URL + nomeArquivo;
    }
}
