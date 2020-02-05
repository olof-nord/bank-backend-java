package componenttest.setup.wiremock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.common.Slf4jNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.extension.Extension
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import com.github.tomakehurst.wiremock.verification.LoggedRequest

abstract class BaseWireMock {

    private WireMockServer server
    protected WireMock wireMock

    abstract int getPort()

    BaseWireMock() {
        if (!server) {
            server = new WireMockServer(WireMockConfiguration.options()
                .port(getPort())
                .notifier(new Slf4jNotifier(true))
                .extensions(getExtensions()))
            server.start()
        }

        wireMock = new WireMock(server)
    }

    void start() {
        if(server && !server.isRunning()) {
            server.start()
        }
    }

    void stop() {
        server.stop()
        server = null
    }

    void reset() {
        if(server && server.isRunning()) {
            server.resetAll()
        }
    }

    void verifyThat(RequestPatternBuilder requestPatternBuilder) {
        wireMock.verifyThat(requestPatternBuilder)
    }

    void verifyThat(int count, RequestPatternBuilder requestPatternBuilder) {
        wireMock.verifyThat(count, requestPatternBuilder)
    }

    void verifyThatNoRequestOccurred(RequestPatternBuilder requestPatternBuilder) {
        wireMock.verifyThat(0, requestPatternBuilder)
    }

    List<LoggedRequest> find(RequestPatternBuilder requestPatternBuilder) {
        wireMock.find(requestPatternBuilder)
    }

    List<LoggedRequest> findAll() {
        wireMock.find(WireMock.anyRequestedFor(WireMock.anyUrl()))
    }

    protected static Extension[] getExtensions() {
        return new Extension[0]
    }

}
