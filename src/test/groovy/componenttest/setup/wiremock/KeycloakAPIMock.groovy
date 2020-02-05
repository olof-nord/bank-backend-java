package componenttest.setup.wiremock

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import groovy.json.JsonOutput
import org.apache.http.entity.ContentType

/**
 * Mock API responses for the /token Keycloak endpoint
 * Note that Keycloak is currently not in use.
 */

@Singleton(lazy = true, strict = false)
class KeycloakAPIMock extends BaseWireMock {

    public static final String AUTHENTICATE_URL = "/auth/realms/fake-bank-realm/protocol/openid-connect/token"

    @Override
    int getPort() {
        8180
    }

    void mockAuthenticateTokenSuccess(String scope) {

        Map responseJson = [
            "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJGSjg2R2NGM2pUYk5MT2NvNE52WmtVQ0lVbWZZQ3FvcXRPUWVNZmJoTmxFIn0.eyJqdGkiOiI5YzRjOTYwZC1iZTIwLTRhZmQtOTJlMS1iNzFmZGU2YWU1NjkiLCJleHAiOjE1ODA4NTMyMjUsIm5iZiI6MCwiaWF0IjoxNTgwODUyOTI1LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODAvYXV0aC9yZWFsbXMvZmFrZS1iYW5rLXJlYWxtIiwic3ViIjoiY2U0OGNkNjctODYzNS00YjA2LWI2MTEtNDYwMTE0YWU0OTgxIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZmFrZS1iYW5rLWJhY2tlbmQiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiJmNDMzNjgzZC1kNDgxLTRmNTYtYWMxYS0zMjQzNTViN2Y4ODkiLCJhY3IiOiIxIiwic2NvcGUiOiJ1c2VyIn0.DfUQcrUh2bePsGyJ6tSWpc42aln2QtHhi3Uqs0KI_cMgFQhJdD1MGqx1zOw52-Xaq_ZtZJFTvCO_C2kOtaTAQgzEJJVwfnW3PxWDtp5LkEsx07lF1OIFIoE5Zm5Zs-1WJwjGaX8MrbnNbPHf3cNDAIE5J-mZq7cGlgnEHXFG3ag",
            "expires_in": 300,
            "refresh_expires_in": 1800,
            "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI5YmUyOGFjNS01OWYwLTRmZTYtYTA1Zi00YzEwYjk2N2QxOTQifQ.eyJqdGkiOiJlNjI4MjhkNS0yZWRlLTQ5MjktYjk1Ni1lMzY1MjBhM2M1MjMiLCJleHAiOjE1ODA4NTQ3MjUsIm5iZiI6MCwiaWF0IjoxNTgwODUyOTI1LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODAvYXV0aC9yZWFsbXMvZmFrZS1iYW5rLXJlYWxtIiwiYXVkIjoiaHR0cDovL2xvY2FsaG9zdDo4MTgwL2F1dGgvcmVhbG1zL2Zha2UtYmFuay1yZWFsbSIsInN1YiI6ImNlNDhjZDY3LTg2MzUtNGIwNi1iNjExLTQ2MDExNGFlNDk4MSIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJmYWtlLWJhbmstYmFja2VuZCIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6ImY0MzM2ODNkLWQ0ODEtNGY1Ni1hYzFhLTMyNDM1NWI3Zjg4OSIsInNjb3BlIjoidXNlciJ9.jpSd-iJ6snylqC3O-wyHEX5qLA-C_ax9lxtdPd_JtOg",
            "token_type": "bearer",
            "not-before-policy": 0,
            "session_state": "f433683d-d481-4f56-ac1a-324355b7f889",
            "scope": "user"
        ]

        mockGetToken(200, responseJson)
    }

    void mockAuthenticateTokenMissingSecret() {

        Map responseJson = [
            "error": "unauthorized_client",
            "error_description": "Client secret not provided in request"
        ]

        mockGetToken(400, responseJson)
    }

    void mockAuthenticateTokenMissingGrantType() {

        Map responseJson = [
            "error": "invalid_request",
            "error_description": "Missing form parameter: grant_type"
        ]

        mockGetToken(400, responseJson)
    }

    void mockGetToken(int responseStatus, Map response) {
        MappingBuilder mappingBuilder = WireMock.post(WireMock.urlPathEqualTo(AUTHENTICATE_URL))

        wireMock.register(mappingBuilder.willReturn(
            WireMock.aResponse()
                .withStatus(responseStatus)
                .withHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType())
                .withBody(JsonOutput.toJson(response)))
        )
    }
}
