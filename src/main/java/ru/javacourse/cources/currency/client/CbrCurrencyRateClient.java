package ru.javacourse.cources.currency.client;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//Компонент для получения списка курсов валют на дату
@Component
public class CbrCurrencyRateClient implements HttpCurrencyDateRateClient {
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    @Override
    public String requestByDate(LocalDate date) {
        // Формируем URL
        String baseurl = "http://www.cbr.ru/scripts/XML_daily.asp";
        String url = buildUrlRequest(baseurl, date);
        // Формируем сервис
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        // Отправляем на внешний ресурс
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Пример запроса http://www.cbr.ru/scripts/XML_daily.asp?date_req=02/03/2002
    // где  date_req= Date of query (dd/mm/yyyy)
    private String buildUrlRequest(String url, LocalDate date) {
        return UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("date_req", DATE_TIME_FORMATTER.format(date))
                .build()
                .toUriString();
    }

}
