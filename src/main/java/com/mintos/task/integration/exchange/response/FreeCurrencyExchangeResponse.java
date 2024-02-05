package com.mintos.task.integration.exchange.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

public record FreeCurrencyExchangeResponse(HashMap<String, String> data) implements Serializable {

        public BigDecimal findByIso(String iso) {
                if (iso == null)
                        return null;
                return new BigDecimal(data().get(iso.trim().toUpperCase()));
        }
}

