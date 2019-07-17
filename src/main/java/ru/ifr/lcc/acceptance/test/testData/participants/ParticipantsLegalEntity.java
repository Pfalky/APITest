package ru.ifr.lcc.acceptance.test.testData.participants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantsLegalEntity implements ParticipantType {
   private String participantINN;
   private String participantName;

}
