package ru.ifr.lcc.acceptance.test.testData.participants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantsIndividual implements ParticipantType {
    String participantFio;
    String participantPosition;
    String participantIssuedate;
    String participantUnitcode;
    String participantDocnumber;
    String participantBirthdate;


}
