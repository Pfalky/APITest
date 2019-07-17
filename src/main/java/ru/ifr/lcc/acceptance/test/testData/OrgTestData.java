package ru.ifr.lcc.acceptance.test.testData;

import io.swagger.client.model.Counterparty;
import io.swagger.client.model.Participant;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**Тестовый объект пользователя с набором полей**/
@Data
public class OrgTestData {

    String orgClientStatus;
    String orgINN;
    String orgKpp;
    String orgOgrn;
    String orgOkpo;
    String orgOkvedName;
    String orgFullName;
    String orgShortName;
    String orgOwnershipForm;
    String orgRegAdress;
    String orgPromoCode;
    String orgRegdate;

    Counterparty counterparty;
    List<Counterparty> counterparties = new ArrayList<>(); /**Список контрагентов, подставляемый в пейлоад (запрос при создании заявки)**/

    Participant participant;
    List<Participant> participants = new ArrayList<>(); /**Список подписантов, подставляемый в пейлоад (запрос при создании заявки)**/
}
