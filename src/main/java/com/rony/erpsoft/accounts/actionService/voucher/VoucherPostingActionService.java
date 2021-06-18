package com.rony.erpsoft.accounts.actionService.voucher;

import com.rony.erpsoft.accounts.model.AccJournalMaster;
import com.rony.erpsoft.accounts.service.AccJournalMasterService;
import com.rony.erpsoft.configuration.AppResponse;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VoucherPostingActionService {

    @Autowired
    AccJournalMasterService accJournalMasterService;

    public AppResponse execute(Map<String, Object> request) {
        try {
            JSONArray voucherIds = new JSONArray( request.get("voucherIds").toString() );

            boolean isUpdateAll = accJournalMasterService.updateAllVouchersByIds(voucherIds);
            if (isUpdateAll) {
                return AppResponse.build(HttpStatus.OK).body(1);
            } else {
                return AppResponse.build(HttpStatus.NO_CONTENT).message("Voucher id not found");
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }


}
