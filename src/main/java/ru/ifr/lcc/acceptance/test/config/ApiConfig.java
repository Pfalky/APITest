package ru.ifr.lcc.acceptance.test.config;

import io.swagger.client.api.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.ifr.lcc.acceptance.test.service.swaggerService.lkkServices.SessionHandler;

@Configuration
@EnableJpaRepositories("ru.ifr")
@ComponentScan("ru.ifr")
@EntityScan("ru.ifr")
public class ApiConfig {

    public static final String BASE_PATH = "https://serverName/api";

    @Bean
    public SessionApi sessionApi(SessionHandler sessionHandler) {
        SessionApi sessionApi = new SessionApi();
        sessionApi.getApiClient().setBasePath(BASE_PATH);
        sessionApi.getApiClient().getHttpClient().setCookieHandler(sessionHandler);
        return sessionApi;
    }


    @Bean
    public UserApi userApi(SessionHandler sessionHandler) {
        UserApi userApi = new UserApi();
        userApi.getApiClient().setBasePath(BASE_PATH);
        userApi.getApiClient().getHttpClient().setCookieHandler(sessionHandler);
        return userApi;
    }

    @Bean
    public ApplicationApi applicationApi(SessionHandler sessionHandler) {
        ApplicationApi applicationApi = new ApplicationApi();
        applicationApi.getApiClient().setBasePath(BASE_PATH);
        applicationApi.getApiClient().getHttpClient().setCookieHandler(sessionHandler);
        return applicationApi;
    }

    @Bean
    public DocumentApi documentApi(SessionHandler sessionHandler) {
        DocumentApi documentApi = new DocumentApi();
        documentApi.getApiClient().setBasePath(BASE_PATH);
        documentApi.getApiClient().getHttpClient().setCookieHandler(sessionHandler);
        return documentApi;
    }

    @Bean
    public CounterpartyApi counterpartyApi(SessionHandler sessionHandler) {
        CounterpartyApi counterpartyApi = new CounterpartyApi();
        counterpartyApi.getApiClient().setBasePath(BASE_PATH);
        counterpartyApi.getApiClient().getHttpClient().setCookieHandler(sessionHandler);
        return counterpartyApi;
    }

    @Bean
    public ApplicationStatusApi applicationStatusApi(SessionHandler sessionHandler) {
        ApplicationStatusApi applicationStatusApi = new ApplicationStatusApi();
        applicationStatusApi.getApiClient().setBasePath(BASE_PATH);
        applicationStatusApi.getApiClient().getHttpClient().setCookieHandler(sessionHandler);
        return applicationStatusApi;
    }
}
