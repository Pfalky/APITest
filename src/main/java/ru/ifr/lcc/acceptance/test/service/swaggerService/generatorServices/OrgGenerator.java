package ru.ifr.lcc.acceptance.test.service.swaggerService.generatorServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.ifr.lcc.acceptance.test.testData.OrgTestData;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
@Slf4j
public class OrgGenerator {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("classpath:testData/organisation.json")
    private Resource users;

    public OrgTestData generateOrgData(){

        /**Генерация Случайной организации*/
        OrgTestData orgData = null;
        try {
            JSONObject object = new JSONObject(IOUtils.toString(users.getInputStream(), Charset.forName("UTF-8")));

            JSONArray organisation = object.getJSONArray("Organisation");
            int randomNum = 0 + (int)(Math.random() * organisation.length());
            JSONObject jsonObject = organisation.getJSONObject(randomNum);
            orgData = objectMapper.readValue(jsonObject.toString(), OrgTestData.class);

            log.debug(orgData.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return orgData;
    }

    /**Генерация организации для ИП*/
    public OrgTestData generateIPOrgData(){

        OrgTestData orgData = null;
        try {
            JSONObject object = new JSONObject(IOUtils.toString(users.getInputStream(), Charset.forName("UTF-8")));

            JSONArray organisation = object.getJSONArray("Organisation");
            JSONObject jsonObject = organisation.getJSONObject(2);
            orgData = objectMapper.readValue(jsonObject.toString(), OrgTestData.class);

            log.debug(orgData.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return orgData;
    }
    /**Генерация организации для ООО*/
    public OrgTestData generateOrgDataOOO(){

        OrgTestData orgData = null;
        try {
            JSONObject object = new JSONObject(IOUtils.toString(users.getInputStream(), Charset.forName("UTF-8")));

            JSONArray organisation = object.getJSONArray("Organisation");
            JSONObject jsonObject = organisation.getJSONObject(0);
            orgData = objectMapper.readValue(jsonObject.toString(), OrgTestData.class);

            log.debug(orgData.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return orgData;
    }
}
