package ru.ifr.lcc.acceptance.test.testData.documents;

import lombok.Data;

@Data
public class DocumentValue {
    private String label;
  //временно удалено, т.к. не планируется использовать в ближайшее время, поле отвечает за массив входящих файлов в бинарном представлении
     //  private String files;
}
