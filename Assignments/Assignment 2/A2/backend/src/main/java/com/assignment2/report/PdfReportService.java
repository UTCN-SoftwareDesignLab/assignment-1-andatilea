package com.assignment2.report;


import com.assignment2.books.BookService;
import com.assignment2.books.model.dto.BookDTO;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.stereotype.Service;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

@Service
public class PdfReportService implements ReportService {

    private final BookService bookService;

    public PdfReportService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String export() throws IOException {

        PDDocument doc = new PDDocument();
        createPDFData(doc);
        return "I am a PDF reporter.";
    }


    public void createPDFData(PDDocument doc) throws IOException{

        try {
            PDPage page = new PDPage(PDRectangle.A3);
            doc.addPage(page);

            PDFont fontTitle = PDType1Font.HELVETICA_BOLD;
            PDFont fontContent = PDType1Font.HELVETICA;

            PDPageContentStream contents = new PDPageContentStream(doc, page);

            contents.beginText();
            // computing the title of the document
            contents.setLeading(30);
            contents.newLineAtOffset(300, 1100);
            contents.setFont(fontTitle, 15);
            contents.showText("BOOKS OUT OF STOCK");
            contents.newLineAtOffset(-240, -90);

            Integer line = 0;
            // then compute the data
            for (BookDTO book: bookService.findAll()) {
                if (book.getQuantity() == 0) {
                    contents.setFont(fontContent, 15);
                    contents.showText("Record " + line);
                    contents.newLine();
                    contents.showText("Book Title: " + book.getTitle() + ", Book Author: " + book.getAuthor() + ", Book Price: " + book.getPrice());
                    contents.newLine();
                    line++;
                }
            }
            contents.endText();
            contents.close();
            doc.save("report.pdf");
        }
        finally {
            doc.close();
        }
    }

    @Override
    public ReportType getType() {
        return ReportType.PDF;
    }
}


