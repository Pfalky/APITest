package ru.ifr.lcc.acceptance.test.testData;

import lombok.Data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

/**Тестовый объект пользователя с набором полей**/

@Data
public class UserData {

    private String usrEmail;
    private String usrSmsCode = "1234"; /** cheat value by default**/
    private String usrName;
    private String usrSurName;
    private String usrParName;
    private String usrPromoCode;
    private String usrPhone;


}
