package com.alsab.boozycalc.party.wiremocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class ProductMocks {
    public static void setupMockProductResponse(WireMockServer mockService, List<Integer> products) throws IOException {
        for(Integer productId: products){
            if(productId < 100){
                mockService.stubFor(WireMock.get(WireMock.urlEqualTo(String.format("/api/v1/products/find?id=%d", productId)))
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(
                                        copyToString(
                                                CocktailMocks.class.getClassLoader().getResourceAsStream(String.format("payload/products/product_%d.json", productId)),
                                                defaultCharset()
                                        )
                                ))
                );
            } else {
                mockService.stubFor(WireMock.get(WireMock.urlEqualTo(String.format("/api/v1/products/find?id=%d", productId)))
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpStatus.BAD_REQUEST.value())
                                .withHeader("Content-Type", MediaType.TEXT_PLAIN_VALUE)
                                .withBody(
                                        copyToString(
                                                CocktailMocks.class.getClassLoader().getResourceAsStream(String.format("payload/products/product_%d", productId)),
                                                defaultCharset()
                                        )
                                ))
                );
            }

        }
    }
}
