package ru.ifr.lcc.acceptance.test.service.swaggerService.generatorServices;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ifr.lcc.acceptance.App;
import ru.ifr.lcc.acceptance.test.testData.participants.ParticipantData;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class ParticipantGeneratorTest {


    @Autowired
    private ParticipantGenerator participantGenerator;



    @Test
    public void test() throws IOException, JSONException {

        ParticipantData participantData = participantGenerator.generateParticipantData("9731009774");

    }

}