package com.rony.erpsoft.sales.service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.rony.erpsoft.sales.controller.HeaderFooterHandler;
import com.rony.erpsoft.sales.dto.DailySalesReportDTO;
import com.rony.erpsoft.sales.dto.SalesByItemReportDTO;
import com.rony.erpsoft.sales.repository.OrderRepository;
import com.rony.erpsoft.sales.uitls.ReportUtils;
import com.rony.erpsoft.user_auth.service.SessionService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PdfReportService {

    private final OrderRepository orderRepository;
    private final ReportUtils reportUtils;
    private final SessionService sessionService;

    public void generateDailySalesPdf(String date, HttpServletResponse response) throws IOException, DocumentException {
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(23, 59, 59);

        List<DailySalesReportDTO> report = orderRepository.findDailySalesReport(startOfDay, endOfDay);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=daily-sales-report-" + date + ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        writer.setPageEvent(new HeaderFooterHandler(null));
        document.open();

        // Title
        document.add(reportUtils.createPdfTitle(sessionService.getOrganization().getName(), "Daily Sales Report", date));
        document.add(new Paragraph(" "));

        // Table
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 2, 2, 3});

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
        Stream.of("Payment Type", "Invoices", "Total Quantity", "Total Sales")
                .forEach(col -> table.addCell(reportUtils.createPdfCell(col, headFont, 25f)));

        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
        int totalInvoices = 0;
        int totalQuantity = 0;
        BigDecimal totalSales = BigDecimal.ZERO;

        for (DailySalesReportDTO dto : report) {
            table.addCell(reportUtils.createPdfCell(dto.getPaymentType().toString(), bodyFont, 20f));
            table.addCell(reportUtils.createPdfCell(String.valueOf(dto.getNumberOfInvoices()), bodyFont, 20f));
            table.addCell(reportUtils.createPdfCell(String.valueOf(dto.getTotalQuantity()), bodyFont, 20f));
            table.addCell(reportUtils.createPdfCell(dto.getTotalSales().toString(), bodyFont, 20f));

            totalInvoices += dto.getNumberOfInvoices();
            totalQuantity += dto.getTotalQuantity();
            totalSales = totalSales.add(dto.getTotalSales());
        }

        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        table.addCell(reportUtils.createPdfFooterCell("TOTAL", footerFont, Color.white));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(totalInvoices), footerFont, Color.white));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(totalQuantity), footerFont, Color.white));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(totalSales), footerFont, Color.white));

        document.add(table);
        document.close();
    }

    public void generateSalesByItemPdf(String startDate, String endDate, HttpServletResponse response) throws IOException, DocumentException {
        LocalDateTime from = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime to = LocalDate.parse(endDate).atTime(23, 59, 59);

        List<SalesByItemReportDTO> report = orderRepository.getSalesByItemReport(from, to);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=sales-by-item-report-" + startDate + ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        writer.setPageEvent(new HeaderFooterHandler(null));
        document.open();

        // Title
        document.add(reportUtils.createPdfTitle(sessionService.getOrganization().getName(), "Sales By Item Report", startDate));
        document.add(new Paragraph(" "));

        // Table
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 2, 2, 3});

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
        Stream.of("Item Code", "Item Name", "Total Quantity", "Total Sales")
                .forEach(col -> table.addCell(reportUtils.createPdfCell(col, headFont, 25f)));

        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
        int totalQuantity = 0;
        BigDecimal totalSales = BigDecimal.ZERO;

        for (SalesByItemReportDTO dto : report) {
            table.addCell(reportUtils.createPdfCell(dto.getItemCode().toString(), bodyFont, 20f));
            table.addCell(reportUtils.createPdfCell(String.valueOf(dto.getItemName()), bodyFont, 20f));
            table.addCell(reportUtils.createPdfCell(String.valueOf(dto.getTotalQuantity()), bodyFont, 20f));
            table.addCell(reportUtils.createPdfCell(dto.getTotalSales().toString(), bodyFont, 20f));

            totalQuantity += dto.getTotalQuantity();
            totalSales = totalSales.add(dto.getTotalSales());
        }

        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        PdfPCell totalCell = reportUtils.createPdfFooterCell("TOTAL", footerFont, Color.white);
        totalCell.setColspan(2); // merge first two columns
        table.addCell(totalCell);

        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(totalQuantity), footerFont, Color.white));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(totalSales), footerFont, Color.white));

        document.add(table);
        document.close();
    }
}
