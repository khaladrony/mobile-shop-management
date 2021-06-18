package com.rony.erpsoft.accounts.actionService.voucher;

import com.rony.erpsoft.accounts.model.AccJournalDetails;
import com.rony.erpsoft.accounts.model.AccJournalMaster;
import com.rony.erpsoft.accounts.model.enums.VoucherType;
import com.rony.erpsoft.accounts.service.AccJournalMasterService;
import com.rony.erpsoft.configuration.AppResponse;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VoucherActionService {
    @Autowired
    ModelValidator modelValidator;
    @Autowired
    SessionService sessionService;
    @Autowired
    AccJournalMasterService accJournalMasterService;

    public AppResponse execute(AccJournalMaster journalMaster) {

        clientDataValidate(journalMaster);

        try {
            if (journalMaster.getId() == null) {

                accJournalMasterService.journalMasterPropertySet(journalMaster);

                String voucherNo = accJournalMasterService.voucherNoGeneration(journalMaster.getVoucherPrefix(), journalMaster.getYear(), journalMaster.getMonth());
                journalMaster.setVoucherNo(voucherNo);

            } else {
                journalMaster.setUpdatedBy(sessionService.getUserId());
                journalMaster.setUpdatedOn(new Date());
            }

            journalMaster.setDetails(accJournalMasterService.getJournalDetails(journalMaster.getDetails()));

            if (modelValidator.isValid(journalMaster)) {
                if (accJournalMasterService.save(journalMaster).getId() > 0) {
                    return AppResponse.build(HttpStatus.OK).body(journalMaster);
                } else {
                    return AppResponse.build(HttpStatus.EXPECTATION_FAILED).message("Not created");
                }
            } else {
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(modelValidator.validationMessage(journalMaster));
            }
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    public AppResponse clientDataValidate(AccJournalMaster journalMaster) {

        try {

            if(journalMaster.getDetails().size()==0){
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("Voucher details not found!");
            }

            double voucherAmount = 0;
            for (AccJournalDetails journalDetails : journalMaster.getDetails()) {
                voucherAmount = voucherAmount + journalDetails.getPrimeAmount();
            }

            if (voucherAmount != 0) {
                return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("Debit credit amount mismatch!");
            }

            return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message(modelValidator.validationMessage(journalMaster));
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }
}
