package com.assignment2.report;
import java.io.IOException;


public interface ReportService {

    String export() throws IOException;

    ReportType getType();
}
