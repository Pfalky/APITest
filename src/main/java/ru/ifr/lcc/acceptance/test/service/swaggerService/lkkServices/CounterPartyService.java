package ru.ifr.lcc.acceptance.test.service.swaggerService.lkkServices;

import io.swagger.client.ApiException;
import io.swagger.client.api.CounterpartyApi;
import io.swagger.client.api.DocumentApi;
import io.swagger.client.model.Counterparty;
import io.swagger.client.model.CounterpartyList;
import org.springframework.stereotype.Service;

@Service
public class CounterPartyService {

    private CounterpartyApi counterpartyApi;

    public CounterPartyService(CounterpartyApi counterpartyApi) {
        this.counterpartyApi = counterpartyApi;
    }
    public CounterpartyList getCounterpartyList(String id) throws ApiException {
        return counterpartyApi.getCounterparties(id);
    }
}
