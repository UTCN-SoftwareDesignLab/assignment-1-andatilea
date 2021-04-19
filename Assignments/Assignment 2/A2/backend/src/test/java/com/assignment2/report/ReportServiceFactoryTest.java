package com.assignment2.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Test
    void getReportService() throws IOException {
        ReportService csvReportService = reportServiceFactory.getReportService(ReportType.CSV);
        Assertions.assertEquals("I am a CSV reporter.", csvReportService.export());

        ReportService pdfReportService = reportServiceFactory.getReportService(ReportType.PDF);
        Assertions.assertEquals("I am a PDF reporter.", pdfReportService.export());
    }
}