package ru.ifr.lcc.acceptance.test.service.swaggerService.lkkServices;


import io.swagger.client.ApiException;
import io.swagger.client.api.ApplicationStatusApi;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStatusService {

    private ApplicationStatusApi applicationStatusApi;

    public ApplicationStatusService(ApplicationStatusApi applicationStatusApi) {
        this.applicationStatusApi = applicationStatusApi;
    }

    public void nextStep(String id) throws ApiException {

        applicationStatusApi.nextStatus(id);

    }
}
