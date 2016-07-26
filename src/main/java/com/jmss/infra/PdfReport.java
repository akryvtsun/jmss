package com.jmss.infra;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

// TODO styles.css doesn't have correct font for Linux
public final class PdfReport {
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfReport.class);

    private final File output;

    public PdfReport(File output) {
        this.output = output;
    }

    // TODO add Exception(???) to method signature
    // TODO rethink whether content should be save or ctor param?
    public void save(String content) {
        LOGGER.info("Storing PDF into '{}'...", output.getAbsolutePath());

        try (OutputStream os = new FileOutputStream(output)) {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, os);
            InputStream is = new ByteArrayInputStream(content.getBytes());

            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, getStylesStream());
            document.close();
        } catch (Exception e) {
            LOGGER.error("Error while PDF file generation", e);
        }
    }

    private InputStream getStylesStream() {
        // TODO move to some common constants area???
        return Utils.getResource("/reports/styles.css");
    }
}
