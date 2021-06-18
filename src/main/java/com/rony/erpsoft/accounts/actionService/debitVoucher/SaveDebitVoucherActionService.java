package com.rony.erpsoft.accounts.actionService.debitVoucher;

import com.rony.erpsoft.accounts.actionService.IService;
import com.rony.erpsoft.accounts.model.AccJournalDetails;
import com.rony.erpsoft.accounts.model.AccJournalMaster;
import com.rony.erpsoft.accounts.model.enums.VoucherStatus;
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
import java.util.List;

@Service
public class SaveDebitVoucherActionService implements IService<AccJournalMaster> {
    @Autowired
    ModelValidator modelValidator;
    @Autowired
    SessionService sessionService;
    @Autowired
    AccJournalMasterService accJournalMasterService;

    @Override
    public AppResponse execute(AccJournalMaster journalMaster) {

        try {
            if (journalMaster.getId() == null) {
                journalMaster.setVoucherPrefix(VoucherType.DEBIT_VOUCHER.prefix());

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


}
