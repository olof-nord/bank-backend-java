package componenttest.setup.httpclient

import org.junit.Before
import org.springframework.boot.web.server.LocalServerPort

trait HttpClientTesting {

    @LocalServerPort
    int localPort

    @Before
    void setUp() {
        if (HttpClient.baseUrl == null) {
            HttpClient.setBaseUrl("http://127.0.0.1:${localPort}")
        }
    }

}
