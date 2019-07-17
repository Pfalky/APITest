package ru.ifr.lcc.acceptance.test;

import io.swagger.client.ApiException;
import io.swagger.client.model.*;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.threeten.bp.OffsetDateTime;
import ru.ifr.lcc.acceptance.App;
import ru.ifr.lcc.acceptance.test.service.swaggerService.generatorServices.CounterPartyGenerator;
import ru.ifr.lcc.acceptance.test.service.swaggerService.generatorServices.OrgGenerator;
import ru.ifr.lcc.acceptance.test.service.swaggerService.generatorServices.ParticipantGenerator;
import ru.ifr.lcc.acceptance.test.service.swaggerService.generatorServices.UserGenerator;
import ru.ifr.lcc.acceptance.test.service.swaggerService.lkkServices.*;
import ru.ifr.lcc.acceptance.test.testData.CounterPartyTestData;
import ru.ifr.lcc.acceptance.test.testData.MetaInfoMother;
import ru.ifr.lcc.acceptance.test.testData.OrgTestData;
import ru.ifr.lcc.acceptance.test.testData.UserData;
import ru.ifr.lcc.acceptance.test.testData.participants.ParticipantData;
import ru.ifr.lcc.domain.dict.Status;
import ru.ifr.lcc.domain.doc.Application;
import ru.ifr.lcc.enums.AppMetaInfoEnum;
import ru.ifr.lcc.service.pers.ClientService;
import ru.ifr.lcc.service.sys.LkkUserService;
import ru.ifr.lcc.service.sys.VerificationCodeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)

public class LoginServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * swagger services ..services.swaggerServices  info: https://lkk.dev.ifrbanking.ru/api/swagger-ui.html
     **/
    @Autowired
    private ClientService clientService;
    @Autowired
    private LkkUserService lkkUserService;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private ApplicationStatusService applicationStatusService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private CounterPartyService counterPartyService;


    /**
     * data generators ..testData
     */
    @Autowired
    private UserGenerator userGenerator; //генерация пользователей
    @Autowired
    private OrgGenerator orgGenerator; //генератор данных по организациям
    @Autowired
    private CounterPartyGenerator counterPartyGenerator; //генератор контрагентов
    @Autowired
    private ParticipantGenerator participantGenerator; //генератор собственников

    /**
     * DTO services
     */
    @Autowired
    private ru.ifr.lcc.service.doc.ApplicationService applicationServiceDto;

    /**
     * binary testData resourses
     */
    @Value("classpath:12.jpg")
    private Resource testFile;

    /**
     * Tests methods
     */

    @Test //первичная попытка регистрации и последующая авторизация
    public void firstRegistration() throws ApiException, InterruptedException {
        UserData userData = userGenerator.generateRandomUserData();
        lkkUserService.getByUsername(userData.getUsrPhone()).ifPresent(lkkUser -> {
            clientService.deleteById(lkkUser.getId());
            lkkUserService.deleteById(lkkUser.getId());
            verificationCodeService.deleteByKey(userData.getUsrPhone());
        });

        loginService.PhoneNumberSending(userData.getUsrPhone());
        loginService.SmsCodeSending(userData.getUsrPhone(), userData.getUsrSmsCode());
        loginService.EmailCodeSending(userData.getUsrEmail());
        loginService.RegRequest(userData.getUsrSmsCode(), userData.getUsrEmail(), userData.getUsrName(), userData.getUsrSurName(), userData.getUsrParName());
        assertEquals("Е-мэйл некорректен", userData.getUsrEmail(), userService.getUserInfo().getEmail());
        assertEquals("Имя некорректно", userData.getUsrName(), userService.getUserInfo().getName());
        assertEquals("Фамилия некорректна", userData.getUsrSurName(), userService.getUserInfo().getSurname());
        assertEquals("Отчество некорректно", userData.getUsrParName(), userService.getUserInfo().getParname());
        assertEquals("Номер телефона некорректен", "+" + userData.getUsrPhone(), userService.getUserInfo().getPhone());

        loginService.logOut();

        //авторизация под созданным пользователем
        loginService.PhoneNumberSending(userData.getUsrPhone());
        loginService.SmsCodeSending(userData.getUsrPhone(), userData.getUsrSmsCode());
        assertEquals("Е-мэйл некорректен", userData.getUsrEmail(), userService.getUserInfo().getEmail());
        assertEquals("Имя некорректно", userData.getUsrName(), userService.getUserInfo().getName());
        assertEquals("Фамилия некорректна", userData.getUsrSurName(), userService.getUserInfo().getSurname());
        assertEquals("Отчество некорректно", userData.getUsrParName(), userService.getUserInfo().getParname());
        assertEquals("Номер телефона некорректен", "+" + userData.getUsrPhone(), userService.getUserInfo().getPhone());
    }

    @Test //повторная попытка регистрации
    public void secondTimeRegistration() throws ApiException {
        UserData userData = userGenerator.generateRandomUserData();

        lkkUserService.getByUsername(userData.getUsrPhone()).ifPresent(lkkUser -> {
            clientService.deleteById(lkkUser.getId());
            lkkUserService.deleteById(lkkUser.getId());
            verificationCodeService.deleteByKey(userData.getUsrPhone());
        });

        loginService.PhoneNumberSending(userData.getUsrPhone());
        loginService.SmsCodeSending(userData.getUsrPhone(), "1234");
        loginService.EmailCodeSending("TestEmail@test.ru");
        loginService.RegRequest("1234", "TestEmail@test.ru", "Автотестовый", "Тест", "Тестович");
        loginService.logOut();

        lkkUserService.getByUsername(userData.getUsrPhone()).ifPresent(lkkUser -> {
            verificationCodeService.deleteByKey(userData.getUsrPhone());
        });

        loginService.PhoneNumberSending(userData.getUsrPhone());
        loginService.SmsCodeSending(userData.getUsrPhone(), userData.getUsrSmsCode());

        try {
            loginService.EmailCodeSending("TestEmail@test.ru");
            Assert.fail("Expected IOException");
        } catch (ApiException thrown) {
            assertEquals("Код ответа не соответстветсвует ожидаемому", 403, thrown.getCode());
        } //ожидаем что сработает ошибка доступа

        try {
            loginService.RegRequest("1234", "TestEmail@test.ru", "Автотестовый", "Тест", "Тестович");
            Assert.fail("Expected IOException");
        } catch (ApiException thrown) {
            assertEquals("Код ответа не соответстветсвует ожидаемому", 403, thrown.getCode());
        }//ожидаем что сработает ошибка доступа
        loginService.logOut();
    }

    @Test //создание заявки
    public void createApp() throws ApiException, InterruptedException, IOException {
        UserData userData = userGenerator.generateRandomUserData();
        lkkUserService.getByUsername(userData.getUsrPhone()).ifPresent(lkkUser -> {
            clientService.deleteById(lkkUser.getId());
            lkkUserService.deleteById(lkkUser.getId());
            verificationCodeService.deleteByKey(userData.getUsrPhone());
        });


        loginService.PhoneNumberSending(userData.getUsrPhone());
        loginService.SmsCodeSending(userData.getUsrPhone(), userData.getUsrSmsCode());
        loginService.EmailCodeSending(userData.getUsrEmail());
        loginService.RegRequest(userData.getUsrSmsCode(), userData.getUsrEmail(), userData.getUsrName(), userData.getUsrSurName(), userData.getUsrParName());
        assertEquals("Е-мэйл некорректен", userData.getUsrEmail(), userService.getUserInfo().getEmail());
        assertEquals("Имя некорректно", userData.getUsrName(), userService.getUserInfo().getName());
        assertEquals("Фамилия некорректна", userData.getUsrSurName(), userService.getUserInfo().getSurname());
        assertEquals("Отчество некорректно", userData.getUsrParName(), userService.getUserInfo().getParname());
        assertEquals("Номер телефона некорректен", "+" + userData.getUsrPhone(), userService.getUserInfo().getPhone());

        //создание заявки
        ApplicationData appData = applicationService.getAppData("9731009774", "Автотестовый промокод");

        assertEquals("Статус организации некорректен", "Действующая", appData.getClientStatus());
        assertEquals("Некорректный ИНН", "9731009774", appData.getInn());
        assertEquals("Некорректный КПП", "773101001", appData.getKpp());
        assertEquals("Некорректный ОГРН", "1187746790604", appData.getOgrn());
        assertEquals("Некорректный ОКПО", "32689049", appData.getOkpo());
        assertEquals("Некорректные данные ОКВЕД", "64.99.5 Предоставление факторинговых услуг", appData.getOkvedname());
        assertEquals("Некорректное полное наименование организации", "Общество с Ограниченной Ответственностью \"Первая Факторинговая Компания\"", appData.getOrgnamefull());
        assertEquals("Некорректное краткое наименование организации", "ООО \"ПФК\"", appData.getOrgnameshort());
        assertEquals("Некорректныйтип собственности", "Частная собственность", appData.getOwnershipform());
        assertEquals("Некорректный адрес регистрации", "121357, город Москва, улица Артамонова, дом 6, корпус 3, помещение 24", appData.getRegaddress());
        assertEquals("Значение офиса должно быть пустым", null, appData.getOffice());

        //сохранение заявки

        AppPayload appPayload = new AppPayload();

        //контрагент
        Counterparty counterparty = new Counterparty();
        counterparty.setFactaddress("119049, город Москва, улица Мытная, дом 1, строение 1");
        counterparty.setInn("9715316168");
        counterparty.setName("ООО \"ИФР\"");

        List<Counterparty> counterparties = new ArrayList<>();
        counterparties.add(counterparty);
        appPayload.setCounterparties(counterparties);

        //данные заявки
        AppData data = new AppData();
        data.setOfficeId(2);
        data.setActualaddress(appData.getRegaddress());
        appPayload.setData(data);

        //уполномоченные лица
        Individual individual = new Individual();
        //todo написать нормальный модуль преобразования даты
        individual.setBirthdate(OffsetDateTime.now());
        individual.setIssuedate(OffsetDateTime.now());
        individual.setFio("Соколов Аркадий Васильевич");
        individual.setDocnumber("1111 111111");
        individual.setPosition("Генеральный Директор");
        individual.setUnitcode("1111");

        Participant participant = new Participant();
        participant.setIsPrimary(true);
        participant.setIndividual(individual);

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        appPayload.setParticipants(new ArrayList<>());

        //метаинформация
        List<MetaInfo> meta = new ArrayList<>();
        meta.add(MetaInfoMother.create(AppMetaInfoEnum.INVITE_MANAGER, "true"));
        meta.add(MetaInfoMother.create(AppMetaInfoEnum.ACTUAL_ADDRESS_MATCHES, "false"));
        meta.add(MetaInfoMother.create(AppMetaInfoEnum.NOTIFY_BY_EMAIL, "true"));
        meta.add(MetaInfoMother.create(AppMetaInfoEnum.NOTIFY_BY_PHONE, "true"));
        appPayload.setMetainfo(meta);

        //сохранение данных
        applicationService.saveAppData(appData.getId(), appPayload);

        Application application = applicationServiceDto.getById(UUID.fromString(appData.getId()));
        Status actualStatus = application.getActualStatus();
        Assert.assertEquals(2, actualStatus.getId().intValue());

        //получение списка документов
        UploadedDocuments documentsData = documentService.getDocumentsData(appData.getId());
        for (int i = 0; i < documentsData.size(); i++) {
            documentService.sendDoc(appData.getId(), documentsData.get(i).getData().getDoctypeid(), testFile.getFile(), documentsData.get(i).getData().getName() + ".jpg", "12.jpg");
            System.out.println(documentsData.get(i).getData().getName());
        }

        applicationStatusService.nextStep(appData.getId());
    }

    //todo разнести дублирующийся код
    @Test //создание заявки со случайно сгенерированными данными
    public void createRandApp() throws ApiException, InterruptedException, IOException {
        UserData userData = userGenerator.generateRandomUserData();
        OrgTestData orgTestData = orgGenerator.generateOrgData();
        CounterPartyTestData counterPartyTestData = counterPartyGenerator.generateRandomCounterPartyData();
        ParticipantData participantData = participantGenerator.generateParticipantData(orgTestData.getOrgINN());

        lkkUserService.getByUsername(userData.getUsrPhone()).ifPresent(lkkUser -> {
            clientService.deleteById(lkkUser.getId());
            lkkUserService.deleteById(lkkUser.getId());
            verificationCodeService.deleteByKey(userData.getUsrPhone());
        });


        loginService.PhoneNumberSending(userData.getUsrPhone());
        loginService.SmsCodeSending(userData.getUsrPhone(), userData.getUsrSmsCode());
        loginService.EmailCodeSending(userData.getUsrEmail());
        loginService.RegRequest(userData.getUsrSmsCode(), userData.getUsrEmail(), userData.getUsrName(), userData.getUsrSurName(), userData.getUsrParName());
        assertEquals("Е-мэйл некорректен", userData.getUsrEmail(), userService.getUserInfo().getEmail());
        assertEquals("Имя некорректно", userData.getUsrName(), userService.getUserInfo().getName());
        assertEquals("Фамилия некорректна", userData.getUsrSurName(), userService.getUserInfo().getSurname());
        assertEquals("Отчество некорректно", userData.getUsrParName(), userService.getUserInfo().getParname());
        assertEquals("Номер телефона некорректен", "+" + userData.getUsrPhone(), userService.getUserInfo().getPhone());

        //создание заявки
        ApplicationData appData = applicationService.getAppData(orgTestData.getOrgINN(), orgTestData.getOrgPromoCode());

        assertEquals("Статус организации некорректен", orgTestData.getOrgClientStatus(), appData.getClientStatus());
        assertEquals("Некорректный ИНН", orgTestData.getOrgINN(), appData.getInn());
        assertEquals("Некорректный КПП", orgTestData.getOrgKpp(), appData.getKpp());
        assertEquals("Некорректный ОГРН", orgTestData.getOrgOgrn(), appData.getOgrn());
        assertEquals("Некорректный ОКПО", orgTestData.getOrgOkpo(), appData.getOkpo());
        assertEquals("Некорректные данные ОКВЕД", orgTestData.getOrgOkvedName(), appData.getOkvedname());
        assertEquals("Некорректное полное наименование организации", orgTestData.getOrgFullName(), appData.getOrgnamefull());
        assertEquals("Некорректное краткое наименование организации", orgTestData.getOrgShortName(), appData.getOrgnameshort());
        assertEquals("Некорректный тип собственности", orgTestData.getOrgOwnershipForm(), appData.getOwnershipform());
        assertEquals("Некорректный адрес регистрации", orgTestData.getOrgRegAdress(), appData.getRegaddress());
        assertEquals("Значение офиса должно быть пустым", null, appData.getOffice());

        //сохранение заявки
        AppPayload appPayload = new AppPayload();

        //Todo дописать метод генерации случайного количества контрагентов MINOR
        Counterparty counterparty = new Counterparty();
        counterparty.setFactaddress(counterPartyTestData.getCounterpartyFactAdress());
        counterparty.setInn(counterPartyTestData.getCounterpartyINN());
        counterparty.setName(counterPartyTestData.getCounterpartyShortName());
        List<Counterparty> counterparties = new ArrayList<>();
        counterparties.add(counterparty);
        appPayload.setCounterparties(counterparties);

        //данные заявки
        AppData data = new AppData();
        data.setOfficeId(2); //todo убрать дичь из офисов, сделать генератором. Пока не реализовано, не понятно как оно сейчас работает, верменный костыль
        //todo написать метод запроса списка офисов, дернуть оттуда рандомный, подставить значение
        data.setActualaddress(appData.getRegaddress());
        appPayload.setData(data);

        //уполномоченные лица
        List<Participant> participants = new ArrayList<>();
        Participant participant = new Participant();
        for (int i = 0; i < participantData.getParticipants().size(); i++) {

            if (participantData.getParticipants().get(i).getTypeStorage().equals("INDIVIDUAL")) {
                Individual individual = new Individual();
                participant.setIndividual(individual);
                participant.setIsPrimary(participantData.getParticipants().get(i).isPrimary());
                //todo написать нормальный модуль преобразования даты
                individual.setBirthdate(OffsetDateTime.now());
                individual.setIssuedate(OffsetDateTime.now());
                individual.setFio(participantData.getParticipants().get(i).getIndividual().getParticipantFio());
                individual.setDocnumber(participantData.getParticipants().get(i).getIndividual().getParticipantDocnumber());
                individual.setPosition(participantData.getParticipants().get(i).getIndividual().getParticipantPosition());
                individual.setUnitcode(participantData.getParticipants().get(i).getIndividual().getParticipantUnitcode());
                participants.add(participant);
            } else {
                LegalEntity legalEntity = new LegalEntity();
                participant.setLegalentity(legalEntity);
                participant.setIsPrimary(participantData.getParticipants().get(i).isPrimary());
                legalEntity.setInn(participantData.getParticipants().get(i).getLegalentity().getParticipantINN());
                legalEntity.setName(participantData.getParticipants().get(i).getLegalentity().getParticipantName());
                participants.add(participant);
            }
        }
        appPayload.setParticipants(new ArrayList<>());

        // метаинформация
        List<MetaInfo> meta = new ArrayList<>();
        meta.add(MetaInfoMother.create(AppMetaInfoEnum.INVITE_MANAGER, "true"));
        meta.add(MetaInfoMother.create(AppMetaInfoEnum.ACTUAL_ADDRESS_MATCHES, "false"));
        meta.add(MetaInfoMother.create(AppMetaInfoEnum.NOTIFY_BY_EMAIL, "true"));
        meta.add(MetaInfoMother.create(AppMetaInfoEnum.NOTIFY_BY_PHONE, "true"));
        appPayload.setMetainfo(meta);

        //сохранение данных заявки
        applicationService.saveAppData(appData.getId(), appPayload);
        Application application = applicationServiceDto.getById(UUID.fromString(appData.getId()));
        Status actualStatus = application.getActualStatus();
        Assert.assertEquals(2, actualStatus.getId().intValue());

        //получение списка документов
        UploadedDocuments documentsData = documentService.getDocumentsData(appData.getId());

        for (int i = 0; i < documentsData.size(); i++) {
            documentService.sendDoc(appData.getId(), documentsData.get(i).getData().getDoctypeid(), testFile.getFile(), documentsData.get(i).getData().getName() + ".jpg", "12.jpg");
            System.out.println(documentsData.get(i).getData().getName());
        }

        //переход на 4 страницу
        applicationStatusService.nextStep(appData.getId());
    }

    @Test //создание заявки со случайно сгенерированными данными
    public void checkLegalEntityDocumentsTypesArray() throws ApiException, InterruptedException, IOException {

        //todo написать тест на проверку типов данных загружаемых документов для ООО
    }

    @Test //создание заявки со случайно сгенерированными данными
    public void checkIndividualDocumentsTypesArray() throws ApiException, InterruptedException, IOException {

        //todo написать тест на проверку типов данных загружаемых документов для ИП
        UserData userData = userGenerator.generateRandomUserData();
        OrgTestData orgTestData = orgGenerator.generateOrgData();
        CounterPartyTestData counterPartyTestData = counterPartyGenerator.generateRandomCounterPartyData();
        ParticipantData participantData = participantGenerator.generateParticipantData(orgTestData.getOrgINN());

        lkkUserService.getByUsername(userData.getUsrPhone()).ifPresent(lkkUser -> {
            clientService.deleteById(lkkUser.getId());
            lkkUserService.deleteById(lkkUser.getId());
            verificationCodeService.deleteByKey(userData.getUsrPhone());
        });

        loginService.PhoneNumberSending(userData.getUsrPhone());
        loginService.SmsCodeSending(userData.getUsrPhone(), userData.getUsrSmsCode());
        loginService.EmailCodeSending(userData.getUsrEmail());
        loginService.RegRequest(userData.getUsrSmsCode(), userData.getUsrEmail(), userData.getUsrName(), userData.getUsrSurName(), userData.getUsrParName());

        ApplicationData appData = applicationService.getAppData(orgTestData.getOrgINN(), orgTestData.getOrgPromoCode());
        AppPayload appPayload = new AppPayload();

        Counterparty counterparty = new Counterparty();
        counterparty.setFactaddress(counterPartyTestData.getCounterpartyFactAdress());
        counterparty.setInn(counterPartyTestData.getCounterpartyINN());
        counterparty.setName(counterPartyTestData.getCounterpartyShortName());
        List<Counterparty> counterparties = new ArrayList<>();
        counterparties.add(counterparty);
        appPayload.setCounterparties(counterparties);

        AppData data = new AppData();
        data.setOfficeId(2);
        data.setActualaddress(appData.getRegaddress());
        appPayload.setData(data);

        List<MetaInfo> meta = new ArrayList<>();
        meta.add(MetaInfoMother.create(AppMetaInfoEnum.INVITE_MANAGER, "true"));
        meta.add(MetaInfoMother.create(AppMetaInfoEnum.ACTUAL_ADDRESS_MATCHES, "false"));
        meta.add(MetaInfoMother.create(AppMetaInfoEnum.NOTIFY_BY_EMAIL, "true"));
        meta.add(MetaInfoMother.create(AppMetaInfoEnum.NOTIFY_BY_PHONE, "true"));
        appPayload.setMetainfo(meta);

        applicationService.saveAppData(appData.getId(), appPayload);
    }
}
