package ru.javacourse.cources.currency.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data // чтобы не писать геттеры и сеттеры
@ConfigurationProperties(prefix = "currency.client")
public class CurrencyClientCfg {
    private String url;
}
