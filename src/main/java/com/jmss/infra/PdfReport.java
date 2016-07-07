package com.jmss.infra;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;

public final class PdfReport {

    private final File output;

    public PdfReport(File output) {
        this.output = output;
    }

    // TODO add Exception(???) to method signature
    public void save(String content) {
        try {
            OutputStream file = new FileOutputStream(output);
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
            InputStream is = new ByteArrayInputStream(content.getBytes());
            com.itextpdf.tool.xml.XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
            document.close();
            writer.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
