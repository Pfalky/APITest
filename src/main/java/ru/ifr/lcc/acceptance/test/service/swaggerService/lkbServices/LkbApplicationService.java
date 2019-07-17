/*
package ru.ifr.lcc.acceptance.test.service.swaggerService.lkbServices;


import io.swagger.client.ApiException;
import io.swagger.client.api.ApplicationApi;
import io.swagger.client.model.AppPayload;
import io.swagger.client.model.ApplicationCreateRequest;
import io.swagger.client.model.ApplicationData;
import org.springframework.stereotype.Service;

@Service
public class LkbApplicationService {

    private ApplicationApi applicationApi;

    public LkbApplicationService(ApplicationApi applicationApi) {
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
*/
