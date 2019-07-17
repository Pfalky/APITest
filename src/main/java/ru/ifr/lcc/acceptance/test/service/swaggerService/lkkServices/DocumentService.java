package ru.ifr.lcc.acceptance.test.service.swaggerService.lkkServices;

import io.swagger.client.ApiException;
import io.swagger.client.api.DocumentApi;
import io.swagger.client.model.Document;
import io.swagger.client.model.UploadedDocuments;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DocumentService {

    private DocumentApi documentApi;

    public DocumentService(DocumentApi documentApi) {
        this.documentApi = documentApi;
    }

    public UploadedDocuments getDocumentsData(String id) throws ApiException {
        UploadedDocuments descriptors = documentApi.documentTypeDescriptions(id);


        return descriptors;
    }

    public Document sendDoc(String id, Integer doctype , File file,String name, String origName ) throws ApiException {
        return documentApi.saveDocument(id, doctype, file, name, origName);
    }

    public void getDOcumentsTypes (){

    }
}
