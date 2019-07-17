package ru.ifr.lcc.acceptance.test.service.swaggerService.generatorServices;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ifr.lcc.acceptance.App;
import ru.ifr.lcc.acceptance.test.testData.UserData;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UserGeneratorTest {


    @Autowired
    private UserGenerator userGenerator;



    @Test
    public void test(){

        UserData userData = userGenerator.generateRandomUserData();

    }

}