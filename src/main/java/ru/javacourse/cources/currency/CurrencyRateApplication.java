package ru.javacourse.cources.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.javacourse.cources.currency.config.CurrencyClientCfg;

@SpringBootApplication
@EnableConfigurationProperties(CurrencyClientCfg.class)
public class CurrencyRateApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyRateApplication.class, args);
	}

}
