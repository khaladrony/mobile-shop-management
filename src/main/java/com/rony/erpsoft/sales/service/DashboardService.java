package com.rony.erpsoft.sales.service;

import com.rony.erpsoft.sales.dto.DashboardItemDTO;
import com.rony.erpsoft.sales.dto.SalesProfitReportDTO;
import com.rony.erpsoft.utils.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final OrderService orderService;

    public List<DashboardItemDTO> buildDailySummary() {
        String currentDate = AppUtil.getCurrentDateString();
        SalesProfitReportDTO dto = orderService.calculateProfitReport(currentDate, currentDate);
        return List.of(
                new DashboardItemDTO("TODAY SOLD", dto.getTotalSales(), "sold"),
                new DashboardItemDTO("TODAY PURCHASE COST", dto.getPurchaseCost(), "purchase"),
                new DashboardItemDTO("TODAY EXPENSE", dto.getExpenses(), "expense"),
                new DashboardItemDTO("TODAY RETURN", dto.getSalesReturns(), "month-return"),
                new DashboardItemDTO("TODAY PROFIT", dto.getNetProfit(), "profit")
        );
    }

    public List<DashboardItemDTO> buildMonthlySummary() {
        String monthLabel = YearMonth.now().getMonth().name() + " " + Year.now().getValue();
        String fromDate = AppUtil.getFirstDayOfCurrentMonth();
        String toDate = AppUtil.getLastDayOfCurrentMonth();
        SalesProfitReportDTO dto = orderService.calculateProfitReport(fromDate, toDate);
        return List.of(
                new DashboardItemDTO("SOLD IN " + monthLabel, dto.getTotalSales(), "month-sold"),
                new DashboardItemDTO("PURCHASED COST IN " + monthLabel, dto.getPurchaseCost(), "month-purchase"),
                new DashboardItemDTO("EXPENSE IN " + monthLabel, dto.getExpenses(), "month-expense"),
                new DashboardItemDTO("RETURNED IN " + monthLabel, dto.getSalesReturns(), "month-return"),
                new DashboardItemDTO("PROFIT " + monthLabel, dto.getNetProfit(), "month-profit")
        );
    }
}
