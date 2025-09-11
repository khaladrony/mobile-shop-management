package com.rony.erpsoft.sales.service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.rony.erpsoft.sales.controller.HeaderFooterHandler;
import com.rony.erpsoft.sales.dto.DailySalesReportDTO;
import com.rony.erpsoft.sales.dto.OrderItemSummaryDTO;
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
    private final OrderService orderService;
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
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 2, 2, 3, 3, 3, 3, 3});

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
        Stream.of("Payment Type", "Invoices", "Quantity", "Gross", "Discount","Net Amount", "VAT", "Total Sales")
                .forEach(col -> table.addCell(reportUtils.createPdfCell(col, headFont, 25f)));

        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
        int totalInvoices = 0;
        int totalQuantity = 0;
        BigDecimal grossAmount = BigDecimal.ZERO;
        BigDecimal discountValue = BigDecimal.ZERO;
        BigDecimal natAmount = BigDecimal.ZERO;
        BigDecimal vatAmount = BigDecimal.ZERO;
        BigDecimal totalSales = BigDecimal.ZERO;

        for (DailySalesReportDTO dto : report) {
            table.addCell(reportUtils.createPdfCell(dto.getPaymentType().toString(), bodyFont, 20f));
            table.addCell(reportUtils.createPdfCell(String.valueOf(dto.getNumberOfInvoices()), bodyFont, 20f));
            table.addCell(reportUtils.createPdfCell(String.valueOf(dto.getTotalQuantity()), bodyFont, 20f));
            table.addCell(reportUtils.createPdfCell(dto.getGrossAmount().toString(), bodyFont, 20f));
            table.addCell(reportUtils.createPdfCell(dto.getDiscountValue().toString(), bodyFont, 20f));
            table.addCell(reportUtils.createPdfCell(dto.getNetAmount().toString(), bodyFont, 20f));
            table.addCell(reportUtils.createPdfCell(dto.getVatAmount().toString(), bodyFont, 20f));
            table.addCell(reportUtils.createPdfCell(dto.getTotalSales().toString(), bodyFont, 20f));

            totalInvoices += dto.getNumberOfInvoices();
            totalQuantity += dto.getTotalQuantity();
            grossAmount = grossAmount.add(dto.getGrossAmount());
            discountValue = discountValue.add(dto.getDiscountValue());
            natAmount = natAmount.add(dto.getNetAmount());
            vatAmount = vatAmount.add(dto.getVatAmount());
            totalSales = totalSales.add(dto.getTotalSales());
        }

        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
        table.addCell(reportUtils.createPdfFooterCell("TOTAL", footerFont, Color.white));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(totalInvoices), footerFont, Color.white));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(totalQuantity), footerFont, Color.white));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(grossAmount), footerFont, Color.white));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(discountValue), footerFont, Color.white));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(natAmount), footerFont, Color.white));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(vatAmount), footerFont, Color.white));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(totalSales), footerFont, Color.white));

        document.add(table);
        document.close();
    }

    public void generateSalesByItemPdf(String startDate, String endDate, HttpServletResponse response) throws IOException, DocumentException {

        List<OrderItemSummaryDTO> report = orderService.getSalesByItem(startDate, endDate);

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
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 2, 3, 3, 3, 3, 3});

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
        Stream.of("Item Name", "Quantity", "Gross", "Discount", "Net Amount", "VAT", "Total Sales")
                .forEach(col -> table.addCell(reportUtils.createPdfCell(col, headFont, 25f)));

        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
        int totalQuantity = 0;
        BigDecimal totalGross = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;
        BigDecimal totalNet = BigDecimal.ZERO;
        BigDecimal totalVat = BigDecimal.ZERO;
        BigDecimal totalSales = BigDecimal.ZERO;

        for (OrderItemSummaryDTO dto : report) {
            table.addCell(reportUtils.createPdfCell(String.valueOf(dto.getItemName()), bodyFont, 20f, Element.ALIGN_LEFT));
            table.addCell(reportUtils.createPdfCell(String.valueOf(dto.getTotalQuantity()), bodyFont, 20f));
            table.addCell(reportUtils.createPdfCell(dto.getTotalGrossAmount().toString(), bodyFont, 20f, Element.ALIGN_RIGHT));
            table.addCell(reportUtils.createPdfCell(dto.getTotalDiscountAmount().toString(), bodyFont, 20f, Element.ALIGN_RIGHT));
            table.addCell(reportUtils.createPdfCell(dto.getTotalNetAmount().toString(), bodyFont, 20f, Element.ALIGN_RIGHT));
            table.addCell(reportUtils.createPdfCell(dto.getTotalVatAmount().toString(), bodyFont, 20f, Element.ALIGN_RIGHT));
            table.addCell(reportUtils.createPdfCell(dto.getTotalAmount().toString(), bodyFont, 20f, Element.ALIGN_RIGHT));

            totalQuantity += dto.getTotalQuantity();
            totalGross = totalGross.add(dto.getTotalGrossAmount());
            totalDiscount = totalDiscount.add(dto.getTotalDiscountAmount());
            totalNet = totalNet.add(dto.getTotalNetAmount());
            totalVat = totalVat.add(dto.getTotalVatAmount());
            totalSales = totalSales.add(dto.getTotalAmount());
        }

        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
        PdfPCell totalCell = reportUtils.createPdfFooterCell("TOTAL", footerFont, Color.white);
        totalCell.setColspan(1); // merge first two columns
        table.addCell(totalCell);

        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(totalQuantity), footerFont, Color.white));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(totalGross), footerFont, Color.white, Element.ALIGN_RIGHT));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(totalDiscount), footerFont, Color.white, Element.ALIGN_RIGHT));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(totalNet), footerFont, Color.white, Element.ALIGN_RIGHT));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(totalVat), footerFont, Color.white, Element.ALIGN_RIGHT));
        table.addCell(reportUtils.createPdfFooterCell(String.valueOf(totalSales), footerFont, Color.white, Element.ALIGN_RIGHT));

        document.add(table);
        document.close();
    }
}
