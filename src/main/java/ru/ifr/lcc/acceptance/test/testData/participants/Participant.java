package ru.ifr.lcc.acceptance.test.testData.participants;

import lombok.Data;

@Data
public class Participant {

    private String typeStorage;
    private boolean isPrimary;
    private ParticipantsIndividual individual;
    private ParticipantsLegalEntity legalentity;

}
