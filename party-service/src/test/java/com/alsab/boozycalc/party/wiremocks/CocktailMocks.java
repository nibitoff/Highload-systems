package com.alsab.boozycalc.party.wiremocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class CocktailMocks {
    public static void setupMockCocktailResponse(WireMockServer mockService, List<Integer> cocktails) throws IOException {
        for(Integer cocktailId: cocktails){
            mockService.stubFor(WireMock.get(WireMock.urlEqualTo(String.format("/api/v1/cocktails/find?id=%d", cocktailId)))
                    .willReturn(WireMock.aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                            .withBody(
                                    copyToString(
                                            CocktailMocks.class.getClassLoader().getResourceAsStream(String.format("payload/cocktails/cocktail_%d.json", cocktailId)),
                                            defaultCharset()
                                    )
                            ))
            );
        }
    }
}
