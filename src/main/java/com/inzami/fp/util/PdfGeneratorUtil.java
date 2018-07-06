package com.inzami.fp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import com.inzami.fp.config.ConfigurationProperties;
import com.inzami.fp.domain.Document;
import com.inzami.fp.rest.dto.view.DocumentPdfViewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;


@Component
public class PdfGeneratorUtil {

    @Autowired
    private TemplateEngine templateEngine;

    public File createPdf(String templateName, DocumentPdfViewDTO documentPdfViewDTO) throws Exception {
        Assert.notNull(templateName, "The templateName can not be null");
        Context ctx = new Context();
        Integer weeks = ConfigurationProperties.getIntegerProperty(ConfigurationProperties.VISIT_INTERVAL_LIMIT_WEEKS);
        ctx.setVariable("document", documentPdfViewDTO);
        ctx.setVariable("visitLimit", weeks);

        String processedHtml = templateEngine.process(templateName, ctx);
        FileOutputStream os = null;
        String fileName = documentPdfViewDTO.getNumber();
        final File outputFile = File.createTempFile(fileName, ".pdf");
        try {
            os = new FileOutputStream(outputFile);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processedHtml);
            renderer.layout();
            renderer.createPDF(os, false);
            renderer.finishPDF();
            return outputFile;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) { /*ignore*/ }
            }
        }
    }
}

