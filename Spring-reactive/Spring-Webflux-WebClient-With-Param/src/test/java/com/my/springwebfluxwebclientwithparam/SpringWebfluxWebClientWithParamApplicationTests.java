package com.my.springwebfluxwebclientwithparam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;

@SpringBootTest
class SpringWebfluxWebClientWithParamApplicationTests {

    private ExchangeFunction exchangeFunction;
    private WebClient webClient;
    private String BASE_URL = "http://localhost:8080";

    private final ArgumentCaptor<ClientRequest> argumentCaptor = ArgumentCaptor.forClass(ClientRequest.class);

    @Test
    void contextLoads() {
    }

    @BeforeEach
    void setUp() {
        exchangeFunction = mock(ExchangeFunction.class);
        ClientResponse mockResponse = mock(ClientResponse.class);


        when(mockResponse.bodyToMono(String.class)).thenReturn(Mono.just("test"));

        when(exchangeFunction.exchange(argumentCaptor.capture()))
                .thenReturn(Mono.just(mockResponse));

        webClient = WebClient
                .builder()
                .baseUrl("http://localhost:8080")
                .exchangeFunction(exchangeFunction)
                .build();
    }

    @Test
    public void urlTest() {
        webClient.get()
                .uri("/products")
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.empty())
                .block();

        verifyCalledUrl("/products");
    }

    @Test
    public void uriComponentTest() {
        webClient.get()
                .uri("/products")
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.empty())
                .block();

        verifyCalledUrl("/products");
    }

    @Test
    public void uriComponentVariableTest() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/products/{id}").build(2))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.empty())
                .block();

        verifyCalledUrl("/products/2");
    }

    @Test
    public void uriComponentMultiVariableTest() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/products/{id}/attributes/{attributeId}").build(2,13))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.empty())
                .block();
        verifyCalledUrl("/products/2/attributes/13");
    }

    @Test
    public void uriComponentSingleValueTest() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/products/")
                        .queryParam("name", "AndroidPhone")
                        .queryParam("color", "black")
                        .queryParam("deliveryDate", "13/04/2019")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.empty())
                .block();

        verifyCalledUrl("/products/?name=AndroidPhone&color=black&deliveryDate=13/04/2019");
    }

    @Test
    public void uriComponentSingleValueByPlaceHolderTest() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products/")
                        .queryParam("name", "{title}")
                        .queryParam("color", "{authorId}")
                        .queryParam("deliveryDate", "{date}")
                        .build("AndroidPhone", "black", "13/04/2019"))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.empty())
                .block();

        verifyCalledUrl("/products/?name=AndroidPhone&color=black&deliveryDate=13%2F04%2F2019");
    }

    @Test
    public void uriComponentArrayParameterTest() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products/")
                        .queryParam("tag[]", "Snapdragon", "NFC")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.empty())
                .block();

        verifyCalledUrl("/products/?tag%5B%5D=Snapdragon&tag%5B%5D=NFC");
    }

    @Test
    public void uriComponentArrayParameterOmitSquareBracketsTest() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products/")
                        .queryParam("category", "Phones", "Tablets")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.empty())
                .block();
        verifyCalledUrl("/products/?category=Phones&category=Tablets");
    }

    @Test
    public void uriComponentArrayParameterByStringJoin() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products/")
                        .queryParam("category", String.join(",", "Phones", "Tablets"))
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.empty())
                .block();

        verifyCalledUrl("/products/?category=Phones,Tablets");
    }

    @Test
    public void uriComponentEncodingModeTest() {
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory(BASE_URL);
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);
        webClient = WebClient
                .builder()
                .uriBuilderFactory(defaultUriBuilderFactory)
                .baseUrl(BASE_URL)
                .exchangeFunction(exchangeFunction)
                .build();

        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products/")
                        .queryParam("name", "AndroidPhone")
                        .queryParam("color", "black")
                        .queryParam("deliveryDate", "13/04/2019")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.empty())
                .block();

        verifyCalledUrl("/products/?name=AndroidPhone&color=black&deliveryDate=13/04/2019");
    }

    private void verifyCalledUrl(String relativeUrl) {
        ClientRequest request = argumentCaptor.getValue();
        assertEquals(String.format("%s%s", BASE_URL, relativeUrl), request.url().toString());

        verify(this.exchangeFunction).exchange(request);
        verifyNoMoreInteractions(this.exchangeFunction);
    }

}
