package ru.ifr.lcc.acceptance.test.testData;


import io.swagger.client.model.MetaInfo;
import ru.ifr.lcc.enums.AppMetaInfoEnum;
    /** metainfo request key-value mapper**/
public class MetaInfoMother {
    public static MetaInfo create(AppMetaInfoEnum key, String value){
        MetaInfo appMetaInfo = new MetaInfo();
        appMetaInfo.setKey(key.getId());
        appMetaInfo.setValue(value);
        return appMetaInfo;
    }
}
