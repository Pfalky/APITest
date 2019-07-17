package ru.ifr.lcc.acceptance.test.testData.participants;

public interface ParticipantType {
    /**
     * интерфейс для владельцев организации: могут быть физлицами и юрлицами. Интерфейс хранит общие поля.
     */
        String typeStorage = null;
        String isPrimary = null;
        String individual = null;
        String legalEntity = null;
}
