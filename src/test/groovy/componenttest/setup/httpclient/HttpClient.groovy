package componenttest.setup.httpclient

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.builder.Builder
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.http.client.ClientHttpResponse
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

import java.nio.charset.StandardCharsets

@Builder(prefix = "with", builderMethodName = "createRequest", buildMethodName = "create")
class HttpClient {

    static String baseUrl

    private static RestTemplate restTemplate = buildRestTemplate(null)

    String url
    Map<String, String> queryParameters
    Map<String, String> headers
    Object body

    static def request() {
        return createRequest()
    }

    static void setBaseUrl(String url){
        baseUrl = url
        restTemplate = new RestTemplateBuilder()
            .rootUri(baseUrl)
            .errorHandler(new NoopResponseErrorHandler())
            .build()
    }

    private static RestTemplate buildRestTemplate(String url) {
        HttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8)

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
            .errorHandler(new NoopResponseErrorHandler())
            .messageConverters(stringHttpMessageConverter)

        if (url != null) {
            restTemplateBuilder = restTemplateBuilder.rootUri(baseUrl)
        }

        return restTemplateBuilder.build()
    }

    Response<String> post() {
        execute(HttpMethod.POST)
    }

    Response<String> get() {
        execute(HttpMethod.GET)
    }

    private Response<String> execute(HttpMethod httpMethod) {
        HttpHeaders requestHeaders = new HttpHeaders(headers ?: [:])
        HttpEntity request

        if (body instanceof Map || body instanceof List) {
            request = new HttpEntity<>(JsonOutput.toJson(body), requestHeaders)
        } else if (body instanceof String) {
            request = new HttpEntity<>(body, requestHeaders)
        } else {
            request = new HttpEntity<>(requestHeaders)
        }

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(url)
        queryParameters.each {
            key, value -> uriComponentsBuilder.queryParam(key, value)
        }
        String combinedUris = uriComponentsBuilder.build().toUriString()

        return new Response<String>(restTemplate.exchange(combinedUris, httpMethod, request, String))
    }

    static class NoopResponseErrorHandler implements ResponseErrorHandler {

        @Override
        boolean hasError(ClientHttpResponse response) throws IOException {
            return false
        }

        @Override
        void handleError(ClientHttpResponse response) throws IOException {
        }
    }

    static class Response<T> {
        private static JsonSlurper jsonSlurper = new JsonSlurper()

        @Delegate
        private ResponseEntity<T> delegate

        @Lazy
        Object json = jsonSlurper.parseText(body as String)

        Response(ResponseEntity<T> delegate) {
            this.delegate = delegate
        }
    }
}
