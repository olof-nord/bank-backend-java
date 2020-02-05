package componenttest

import componenttest.setup.httpclient.HttpClient
import componenttest.setup.httpclient.HttpClientTesting
import componenttest.setup.wiremock.KeycloakAPIMock
import componenttest.setup.wiremock.WireMockTesting
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Mock API requests for the /token Keycloak endpoint
 * Note that Keycloak is currently not in use.
 */

class KeycloakTest extends Specification implements HttpClientTesting, WireMockTesting {

    String DEFAULT_SCOPE = "user"

    // TODO: Run this test without the Spring Application context
    @Ignore("Keycloak is not used")
    def "Should successfully retrieve a token"() {

        given: "The Keycloak instance will respond successfully to our request"
        KeycloakAPIMock.instance.mockAuthenticateTokenSuccess(DEFAULT_SCOPE)

        when: "We request a login"
        HttpClient.Response loginResponse = HttpClient.request().withUrl(KeycloakAPIMock.AUTHENTICATE_URL)
        .withBody([
            "client_id": "fake-bank-backend",
            "username": "olof",
            "password": "password",
            "grant_type": "password",
            "client_secret": "14b114fd-4d3d-4e20-8a5c-3b6314c20861",
            "scope": "user"
        ])
        .create()
        .post()

        then: "The request ist successful"
        loginResponse.statusCodeValue == 200

    }
}
