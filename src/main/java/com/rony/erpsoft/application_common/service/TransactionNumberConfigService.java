package com.rony.erpsoft.application_common.service;

import com.rony.erpsoft.application_common.model.TransactionNumberConfig;
import com.rony.erpsoft.application_common.repo.GenericLastNumberRepository;
import com.rony.erpsoft.application_common.repo.TransactionNumberConfigRepository;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TransactionNumberConfigService {

    private final TransactionNumberConfigRepository configRepository;
    private final GenericLastNumberRepository genericRepository;
    private final SessionService sessionService;

    @Transactional
    public String generateTransactionNumber(String module, String transactionType, Date transactionDate) {

        TransactionNumberConfig config = configRepository
                .findByOrganizationIdAndModuleAndTransactionType(sessionService.getOrganizationId(), module, transactionType)
                .orElseThrow(() -> new IllegalStateException(
                        "Config not found for module: " + module + " type: " + transactionType));

        String lastTransactionNo = genericRepository.findLastNumber(config, transactionType);

        int nextSeq = 1;
        if (lastTransactionNo != null && lastTransactionNo.length() >= config.getSeqLength()) {
            String seqPart = lastTransactionNo.substring(lastTransactionNo.length() - config.getSeqLength());
            nextSeq = Integer.parseInt(seqPart) + 1;
        }

        return formatNumber(config, nextSeq, transactionDate);
    }

    private String formatNumber(TransactionNumberConfig config, int seq, Date now) {
        String seqStr = String.format("%0" + config.getSeqLength() + "d", seq);

        String pattern = config.getFormatPattern();

        // Replace placeholders if present
        if (pattern.contains("{PREFIX}")) {
            pattern = pattern.replace("{PREFIX}", config.getPrefix());
        }
        if (pattern.contains("{YYYY}")) {
            pattern = pattern.replace("{YYYY}", String.valueOf(AppUtil.getYear(now)));
        }
        if (pattern.contains("{YY}")) {
            pattern = pattern.replace("{YY}", String.valueOf(AppUtil.getYear(now)).substring(2)); // last 2 digits
        }
        if (pattern.contains("{MM}")) {
            pattern = pattern.replace("{MM}", String.format("%02d", AppUtil.getMonth(now)));
        }
        if (pattern.contains("{SEQ}")) {
            pattern = pattern.replace("{SEQ}", seqStr);
        }

        return pattern;
    }

}
