package com.rony.erpsoft.sales.uitls;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.rony.erpsoft.sales.controller.DottedCellBorder;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.awt.*;


@Component
public class ReportUtils {

    /* ---------- Excel Utilities ---------- */
    public CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        return headerStyle;
    }

    public CellStyle createTableHeaderStyle(Workbook workbook) {
        CellStyle tableHeaderStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font tableHeaderFont = workbook.createFont();
        tableHeaderFont.setBold(true);
        tableHeaderStyle.setFont(tableHeaderFont);
        tableHeaderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        tableHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return tableHeaderStyle;
    }

    /* ---------- PDF Utilities ---------- */
    private static final Color ASH_COLOR = new Color(169, 169, 169);

    public PdfPCell createPdfCell(String content, Font font, float fixedHeight) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(fixedHeight);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setCellEvent(new DottedCellBorder(ASH_COLOR));
        return cell;
    }

    public PdfPCell createPdfFooterCell(String content, Font font, Color bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(bgColor);
        cell.setFixedHeight(20f);
        cell.setCellEvent(new DottedCellBorder(ASH_COLOR));
        return cell;
    }

    public Paragraph createPdfTitle(String companyName, String reportName, String date) {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD);
        Paragraph title = new Paragraph(companyName + "\n" + reportName + " - " + date, titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        return title;
    }
}
