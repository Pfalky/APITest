package ru.ifr.lcc.acceptance.test.service.swaggerService.lkkServices;


import io.swagger.client.ApiException;
import io.swagger.client.api.ApplicationApi;
import io.swagger.client.api.ApplicationStatusApi;
import io.swagger.client.api.UserApi;
import io.swagger.client.model.*;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    private ApplicationApi applicationApi;

    public ApplicationService(ApplicationApi applicationApi) {
        this.applicationApi = applicationApi;
    }


    public ApplicationData getAppData(String inn, String promo) throws ApiException {
        ApplicationCreateRequest applicationCreateRequest = new ApplicationCreateRequest();
        applicationCreateRequest.setInn(inn);
        applicationCreateRequest.setPromo(promo);
        return applicationApi.createApplication(applicationCreateRequest).getData();
    }

    public void saveAppData(String id, AppPayload appPayload) throws ApiException {

        applicationApi.save(id,appPayload);
    }
}
