package com.rony.erpsoft.sales.service;

import com.rony.erpsoft.sales.dto.DailySalesReportDTO;
import com.rony.erpsoft.sales.repository.OrderRepository;
import com.rony.erpsoft.sales.uitls.ReportUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelReportService {

    private final OrderRepository orderRepository;
    private final ReportUtils reportUtils;

    public void generateDailySalesExcel(String date, HttpServletResponse response) throws IOException {
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(23, 59, 59);

        List<DailySalesReportDTO> report = orderRepository.findDailySalesReport(startOfDay, endOfDay);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "inline; filename=daily-sales-report-" + date + ".xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Daily Sales");

            // Title
            Row headerRow = sheet.createRow(0);
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("My Company Ltd. - Daily Sales Report (" + date + ")");
            headerCell.setCellStyle(reportUtils.createHeaderStyle(workbook));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

            // Table header
            Row tableHeader = sheet.createRow(2);
            String[] columns = {"Payment Type", "Invoices", "Total Quantity", "Total Sales"};
            CellStyle tableHeaderStyle = reportUtils.createTableHeaderStyle(workbook);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = tableHeader.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(tableHeaderStyle);
            }

            // Data rows
            int rowIdx = 3;
            for (DailySalesReportDTO dto : report) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(dto.getPaymentType().toString());
                row.createCell(1).setCellValue(dto.getNumberOfInvoices());
                row.createCell(2).setCellValue(dto.getTotalQuantity());
                row.createCell(3).setCellValue(dto.getTotalSales().doubleValue());
            }

            // Auto size
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
        }
    }
}
