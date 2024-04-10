package ru.javacourse.cources.currency.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data // чтобы не писать геттеры и сеттеры
@EnableConfigurationProperties()
@ConfigurationProperties(prefix = "currency.client")
class CurrencyClientConfig {
    private String url;
}
