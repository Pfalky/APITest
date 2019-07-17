package ru.ifr.lcc.acceptance.test.testData;

import lombok.Data;

/**Тестовый объект контрагентов с набором полей**/

@Data

public class CounterPartyTestData {

    private String counterpartyINN;
    private String counterpartyShortName;
    private String counterpartyFactAdress;

}
