package ru.javacourse.cources.currency.controller;

import ru.javacourse.cources.currency.service.CbrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

// Всегда возвращает серилиазованный строки вместо MVC модели
@RestController
@RequestMapping("currency")
@RequiredArgsConstructor // добавляем все финальные переменные в конструктор
public class CurrencyController {
    private final CbrService currencyService;

    @GetMapping("/rate/{code}")
    public BigDecimal currencyRate(@PathVariable("code") String code){
        return currencyService.requestByCurrencyCode(code);
    }
}
