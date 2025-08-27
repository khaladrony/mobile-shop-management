package com.rony.erpsoft.sales.controller;

import com.lowagie.text.DocumentException;
import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.sales.repository.OrderRepository;
import com.rony.erpsoft.sales.service.ExcelReportService;
import com.rony.erpsoft.sales.service.OrderService;
import com.rony.erpsoft.sales.service.PdfReportService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.rony.erpsoft.utils.ApplicationConstants.POS_REPORT_BASE_URL;
import static com.rony.erpsoft.utils.ApplicationConstants.SALES_VIEW_PAGE;
import static com.rony.erpsoft.utils.ApplicationConstants.VIEW;

@RestController
@RequestMapping(POS_REPORT_BASE_URL)
@AllArgsConstructor
public class PosReportController extends AppProperty {

    private final AppUtil appUtil;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final ExcelReportService excelReportService;
    private final PdfReportService pdfReportService;

    @GetMapping(value = VIEW)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView(SALES_VIEW_PAGE);
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());

        return modelAndView;
    }

    @GetMapping(value = "/daily-sales")
    public AppResponse<Object> getDailySalesReport(
            @RequestParam("date") String date // format: yyyy-MM-dd
    ) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(23, 59, 59);

        return AppResponse.build(HttpStatus.OK).body(orderRepository.findDailySalesReport(startOfDay, endOfDay));
    }

    @GetMapping("/daily-sales/excel")
    public void exportDailySalesExcel(
            @RequestParam String date,
            HttpServletResponse response
    ) throws IOException {

        excelReportService.generateDailySalesExcel(date, response);
    }

    @GetMapping("/daily-sales/pdf")
    public void exportDailySalesPdf(
            @RequestParam String date,
            HttpServletResponse response
    ) throws IOException, DocumentException {

        pdfReportService.generateDailySalesPdf(date, response);
    }

    @GetMapping("/sales-by-item")
    public AppResponse<Object> getSalesByItemReport(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return AppResponse.build(HttpStatus.OK).body(orderService.getSalesByItem(startDate, endDate));
    }

    @GetMapping("/sales-by-item/pdf")
    public void exportSalesByItemPdf(
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpServletResponse response
    ) throws IOException, DocumentException {

        pdfReportService.generateSalesByItemPdf(startDate, endDate, response);
    }
}