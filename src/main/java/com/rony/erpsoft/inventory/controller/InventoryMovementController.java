package com.rony.erpsoft.inventory.controller;

import com.rony.erpsoft.configuration.AppProperty;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static com.rony.erpsoft.utils.ApplicationConstants.INVENTORY_MOVEMENT_BASE_URL;
import static com.rony.erpsoft.utils.ApplicationConstants.VIEW;
import static com.rony.erpsoft.utils.ApplicationConstants.VIEW_PAGE;

@RestController
@RequestMapping(INVENTORY_MOVEMENT_BASE_URL)
@AllArgsConstructor
public class InventoryMovementController extends AppProperty {

    private final AppUtil appUtil;

    @RequestMapping(value = VIEW, method = RequestMethod.GET)
    public ModelAndView view() {
        appUtil.genToken();
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject(KEY.JSPVIEWKEY, appUtil.getToken());

        return modelAndView;
    }
}
