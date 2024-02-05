package com.mintos.task.service;

import com.mintos.task.integration.exchange.FreeCurrencyExchangeService;
import com.mintos.task.integration.exchange.response.FreeCurrencyExchangeResponse;
import com.mintos.task.model.Currency;
import com.mintos.task.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    FreeCurrencyExchangeService freeCurrencyExchangeService;

    @Autowired
    CurrencyRepository currencyRepository;

    public BigDecimal convert(BigDecimal amount, String currencyFrom, String currencyTo) {
        FreeCurrencyExchangeResponse freeCurrencyExchangeResponse = freeCurrencyExchangeService.getCurrencyExchangeData();
        BigDecimal rateFrom = freeCurrencyExchangeResponse.findByIso(currencyFrom);
        BigDecimal rateTo = freeCurrencyExchangeResponse.findByIso(currencyTo);
        BigDecimal conversionRate = rateTo.divide(rateFrom, RoundingMode.HALF_EVEN);

        return amount.multiply(conversionRate);
    }

    public Optional<Currency> fetchCurrencyByIso(String iso) {
        return currencyRepository.findByIso(iso.toUpperCase());
    }

    public List<Currency> fetchAllCurrencies() {
        return currencyRepository.findAll();
    }
}
