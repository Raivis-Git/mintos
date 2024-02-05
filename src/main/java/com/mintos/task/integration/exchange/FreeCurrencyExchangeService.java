package com.mintos.task.integration.exchange;

import com.mintos.task.integration.exchange.response.FreeCurrencyExchangeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FreeCurrencyExchangeService {

    Logger logger = LoggerFactory.getLogger(FreeCurrencyExchangeService.class);
    private FreeCurrencyExchangeResponse currencyExchangeData = null;
    private LocalDateTime lastSuccessfulCall;

    @Scheduled(fixedRate = 600000)
    private void fetchExchangeRatesSchedule() {
        try {
            fetchExchangeRates();
        } catch (Exception e) {
            logger.error("Fetch for exchange rates FAILED\n" +
                    "Last successful call at:" + lastSuccessfulCall.toString() + "\n" +
                    e.getMessage());
        }
    }

    private void fetchExchangeRates() throws Exception {
        FreeCurrencyExchangeResponse freeCurrencyExchangeResponse = new FreeCurrencyExchange().getResponse();
        if (freeCurrencyExchangeResponse == null)
            throw new Exception("Couldn't retrieve currency exchange data");
        currencyExchangeData = freeCurrencyExchangeResponse;
    }

    public FreeCurrencyExchangeResponse getCurrencyExchangeData() {
        return currencyExchangeData;
    }
}
