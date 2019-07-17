package ru.ifr.lcc.acceptance.test.service.swaggerService.lkkServices;


import io.swagger.client.ApiException;
import io.swagger.client.api.SessionApi;


import io.swagger.client.model.EmailCode;
import io.swagger.client.model.LoginRequest;
import io.swagger.client.model.SmsCode;
import io.swagger.client.model.RegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private SessionApi sessionApi;


    public LoginService(SessionApi sessionApi) {
        this.sessionApi = sessionApi;
    }

    //phone number sending
    public void PhoneNumberSending(String phoneNumber) throws ApiException {

        SmsCode phoneRequest = new SmsCode();
        phoneRequest.setPhone(phoneNumber);
        sessionApi.sendSmsCode(phoneRequest);
    }

    //sms-code sending
    public void SmsCodeSending(String phoneNumber, String smsCode) throws ApiException {

        LoginRequest login = new LoginRequest();
        login.setPhone(phoneNumber);
        login.setCode(smsCode);
        sessionApi.login(login);
    }


    //email sending
    public void EmailCodeSending(String emailCode) throws ApiException {
        EmailCode email = new EmailCode();
        email.setEmail(emailCode);
        sessionApi.sendEmailCode(email);
    }


    //registration sending
    public void RegRequest(String smsCode, String email, String name, String sName, String pName) throws ApiException {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setCode(smsCode);
        registrationRequest.setEmail(email);
        registrationRequest.setName(name);
        registrationRequest.setParname(pName);
        registrationRequest.setSurname(sName);
        sessionApi.registration(registrationRequest);
    }

    public void logOut() throws ApiException {
        sessionApi.logout();
    }


}


