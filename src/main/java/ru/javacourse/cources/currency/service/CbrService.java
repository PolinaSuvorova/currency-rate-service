package ru.javacourse.cources.currency.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import ru.javacourse.cources.currency.schema.ValCurs;
import ru.javacourse.cources.currency.client.HttpCurrencyDateRateClient;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.google.common.collect.Maps.toMap;

@Service
public class CbrService {
    // При первом обращении кешируем валюты, при последующих берём из кеша
    // кеш на дату кусов валют и их кодов
    private final Cache<LocalDate, Map<String, BigDecimal>> cache;

    private HttpCurrencyDateRateClient client;

    public CbrService(HttpCurrencyDateRateClient client) {
        this.cache = CacheBuilder.newBuilder().build();
        this.client = client;
    }

    public BigDecimal requestByCurrencyCode(String code) {
        try {
            return cache.get(LocalDate.now(), this::callAllByCurrentDate).get(code);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, BigDecimal> callAllByCurrentDate() {
        String xml = client.requestByDate(LocalDate.now());
        ValCurs response = unmarshal(xml);

        return response.getValute()
                .stream()
                .collect(Collectors.toMap(ValCurs.Valute::getCharCode,
                         item -> parseWithLocale(item.getValue()), (a, b) -> b));
    }

    private BigDecimal parseWithLocale(String currency){
        try{
            double v = NumberFormat.getNumberInstance(Locale.getDefault()).parse(currency).doubleValue();
            return BigDecimal.valueOf(v);
        }catch (ParseException e){
            throw new RuntimeException(e);
        }
    }

    //Конвертируем XML в класс ValCurs
    private ValCurs unmarshal(String xml){
        try(StringReader reader = new StringReader(xml)){
            JAXBContext context = JAXBContext.newInstance(ValCurs.class);
            return (ValCurs) context.createUnmarshaller().unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
