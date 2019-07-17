package ru.ifr.lcc.acceptance.test.testData.documents;

import lombok.Data;

import java.util.List;

@Data
public class DocumentsData {

   private int doctypeid;
   private String name;
   private String description;
   private int filecount;
   boolean required;
   private List<DocumentValue> value;

}
