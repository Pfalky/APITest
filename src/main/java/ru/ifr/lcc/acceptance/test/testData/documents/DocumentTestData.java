package ru.ifr.lcc.acceptance.test.testData.documents;

import lombok.Data;

import java.util.List;

@Data
public class DocumentTestData {

    private String doctype;
    private List<DocumentsData> data;
}
