package ru.ifr.lcc.acceptance.test.service.swaggerService.generatorServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.ifr.lcc.acceptance.test.testData.participants.ParticipantData;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
@Slf4j
public class ParticipantGenerator {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("classpath:testData/participants.json")
    private Resource users;


    public ParticipantData generateParticipantData(String inn) {


        try {
            JSONArray object = new JSONArray(IOUtils.toString(users.getInputStream(), Charset.forName("UTF-8")));

            for (int i = 0; i < object.length(); i++) {
                ParticipantData participantData = objectMapper.readValue(object.getString(i), ParticipantData.class);
                if (participantData.getInn().equals(inn)) {
                    return participantData;
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Json not found");
    }
}
