package ru.ifr.lcc.acceptance.test.testData.participants;

import lombok.Data;

import java.util.List;

@Data
public class ParticipantData {

    private String inn;
    private List<Participant> participants;
}
