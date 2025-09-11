package com.rony.erpsoft.sales.controller;

import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.sales.model.enums.OrderStatus;
import com.rony.erpsoft.sales.service.DashboardService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static com.rony.erpsoft.utils.ApplicationConstants.POS_DASHBOARD_BASE_URL;
import static com.rony.erpsoft.utils.ApplicationConstants.SALES_VIEW_PAGE;
import static com.rony.erpsoft.utils.ApplicationConstants.VIEW;

@RestController
@RequestMapping(POS_DASHBOARD_BASE_URL)
@AllArgsConstructor
public class PosDashboardController extends AppProperty {

    private final AppUtil appUtil;
    private final DashboardService dashboardService;

    @GetMapping(value = VIEW)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView(SALES_VIEW_PAGE);
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());

        return modelAndView;
    }

    @GetMapping("/daily")
    public AppResponse getDailySummary() {
        return AppResponse.build(HttpStatus.OK)
                .body(dashboardService.buildDailySummary());
    }

    @GetMapping("/monthly")
    public AppResponse getMonthlySummary() {
        return AppResponse.build(HttpStatus.OK)
                .body(dashboardService.buildMonthlySummary());
    }

}
