package ru.ifr.lcc.acceptance.test.service.swaggerService.lkkServices;

import io.swagger.client.ApiException;
import io.swagger.client.api.SessionApi;
import io.swagger.client.api.UserApi;
import io.swagger.client.model.SmsCode;
import io.swagger.client.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserApi userApi;

    public UserService(UserApi userApi) {
        this.userApi = userApi;
    }

    //phone number sending
    public User getUserInfo() throws ApiException {
       return userApi.getUser();
    }


}
