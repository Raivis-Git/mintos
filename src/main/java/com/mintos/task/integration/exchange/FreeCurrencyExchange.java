package com.mintos.task.integration.exchange;

import com.google.gson.Gson;
import com.mintos.task.integration.exchange.response.FreeCurrencyExchangeResponse;
import org.apache.coyote.BadRequestException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.net.HttpURLConnection;

public class FreeCurrencyExchange {

    Logger log = LoggerFactory.getLogger(FreeCurrencyExchange.class);

    private FreeCurrencyExchangeResponse request(String url) {
        log.info("FreeCurrencyExchange request STARTED");
        Gson gson = new Gson();
        HttpGet request = new HttpGet(url);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setHeader("apiKey", "fca_live_6LIVDDUMAmvC7CoHpP6MuFBClIzG6kqPt6lInFVt");

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            CloseableHttpResponse response = httpClient.execute(request);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (HttpURLConnection.HTTP_OK == statusCode)
                return gson.fromJson(EntityUtils.toString(response.getEntity()), FreeCurrencyExchangeResponse.class);

            throw new BadRequestException();
        } catch (Exception e) {
            log.info("FreeCurrencyExchange request ERROR");
            e.printStackTrace();
            return null;
        } finally {
            log.info("FreeCurrencyExchange request FINISHED");
        }
    }

    public FreeCurrencyExchangeResponse getResponse() {
        return request("https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_6LIVDDUMAmvC7CoHpP6MuFBClIzG6kqPt6lInFVt");
    }
}
