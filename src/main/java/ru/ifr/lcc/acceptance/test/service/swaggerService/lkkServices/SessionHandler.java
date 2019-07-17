package ru.ifr.lcc.acceptance.test.service.swaggerService.lkkServices;

import org.springframework.stereotype.Service;

import java.net.CookieHandler;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SessionHandler extends CookieHandler {

    private SessionManager sessionManager;

    public SessionHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /*set api cookie method overriding */

    @Override
    public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) {
        System.out.println(requestHeaders);

        if (sessionManager.getSession() != null) {
            requestHeaders = new HashMap<>(requestHeaders);
            requestHeaders.put("Cookie", Collections.singletonList(sessionManager.getSession()));
        }
        return requestHeaders;
    }

    @Override
    public void put(URI uri, Map<String, List<String>> responseHeaders) {

        if (responseHeaders.containsKey("Set-Cookie"))
            sessionManager.setSession(String.valueOf(responseHeaders.get("Set-Cookie").get(0)));


    }
}
