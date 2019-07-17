package ru.ifr.lcc.acceptance.test.service.swaggerService.generatorServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.client.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.ifr.lcc.acceptance.test.testData.UserData;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
@Slf4j
public class UserGenerator {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("classpath:testData/userData.json")
    private Resource users;
    public UserData generateRandomUserData(){



        UserData userData = null;
        try {
            JSONObject object = new JSONObject(IOUtils.toString(users.getInputStream(), Charset.forName("UTF-8")));

            JSONArray lkkUsers = object.getJSONArray("LKKUser");
            int randomNum = 0 + (int)(Math.random() * lkkUsers.length());
            JSONObject jsonObject = lkkUsers.getJSONObject(randomNum);

            userData = objectMapper.readValue(jsonObject.toString(), UserData.class);

            log.debug(userData.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        return userData;
    }

    public UserData generateNumberedUserData(int i){

        UserData userData = null;
        try {
            JSONObject object = new JSONObject(IOUtils.toString(users.getInputStream(), Charset.forName("UTF-8")));

            JSONArray lkkUsers = object.getJSONArray("LKKUser");

            JSONObject jsonObject = lkkUsers.getJSONObject(i);

            userData = objectMapper.readValue(jsonObject.toString(), UserData.class);

            log.debug(userData.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        return userData;
    }
}
