package ru.ifr.lcc.acceptance.test.service.swaggerService.lkkServices;

import org.springframework.stereotype.Service;

@Service
public class SessionManager {

    private String session;


    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

}

