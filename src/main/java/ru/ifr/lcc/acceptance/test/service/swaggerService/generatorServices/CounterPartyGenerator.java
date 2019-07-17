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
import ru.ifr.lcc.acceptance.test.testData.CounterPartyTestData;
import ru.ifr.lcc.acceptance.test.testData.UserData;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
@Slf4j
public class CounterPartyGenerator {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("classpath:testData/counterparties.json")
    private Resource users;
    public CounterPartyTestData generateRandomCounterPartyData(){

        CounterPartyTestData counterPartyTestData = null;
        try {
            JSONObject object = new JSONObject(IOUtils.toString(users.getInputStream(), Charset.forName("UTF-8")));

            JSONArray lkkUsers = object.getJSONArray("counterParty");
            int randomNum = 0 + (int)(Math.random() * lkkUsers.length());
            JSONObject jsonObject = lkkUsers.getJSONObject(randomNum);

            counterPartyTestData = objectMapper.readValue(jsonObject.toString(), CounterPartyTestData.class);

            log.debug(counterPartyTestData.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        return counterPartyTestData;
    }

    public CounterPartyTestData  generateNumberedCounterPartyData(int i){
        CounterPartyTestData counterPartyTestData = null;
        try {
            JSONObject object = new JSONObject(IOUtils.toString(users.getInputStream(), Charset.forName("UTF-8")));

            JSONArray lkkUsers = object.getJSONArray("counterParty");

            JSONObject jsonObject = lkkUsers.getJSONObject(i);

            counterPartyTestData = objectMapper.readValue(jsonObject.toString(), CounterPartyTestData.class);

            log.debug(counterPartyTestData.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return counterPartyTestData;
    }
}
