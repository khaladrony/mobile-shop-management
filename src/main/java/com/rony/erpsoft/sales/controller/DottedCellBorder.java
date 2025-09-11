package com.rony.erpsoft.sales.controller;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;

import java.awt.*;

public class DottedCellBorder implements PdfPCellEvent {
    private Color lineColor;

    // Constructor to set custom color
    public DottedCellBorder(Color lineColor) {
        this.lineColor = lineColor;
    }
    @Override
    public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvases) {
        PdfContentByte cb = canvases[PdfPTable.LINECANVAS];
        cb.setLineWidth(0.5f);
        cb.setLineDash(2f, 2f); // dotted line
        cb.setColorStroke(lineColor); // set line color

        // Draw all 4 sides
        cb.moveTo(rect.getLeft(), rect.getBottom()); // bottom
        cb.lineTo(rect.getRight(), rect.getBottom());
        cb.stroke();

        cb.moveTo(rect.getLeft(), rect.getTop()); // top
        cb.lineTo(rect.getRight(), rect.getTop());
        cb.stroke();

        cb.moveTo(rect.getLeft(), rect.getBottom()); // left
        cb.lineTo(rect.getLeft(), rect.getTop());
        cb.stroke();

        cb.moveTo(rect.getRight(), rect.getBottom()); // right
        cb.lineTo(rect.getRight(), rect.getTop());
        cb.stroke();
    }
}