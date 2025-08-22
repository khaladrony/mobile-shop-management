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
            // Extract sequence part
            String seqPart = lastTransactionNo.substring(lastTransactionNo.length() - config.getSeqLength());
            int lastSeq = Integer.parseInt(seqPart);

            // Build current prefix based on pattern (excluding sequence)
            String currentPrefix = buildPrefix(config, transactionDate);

            if (lastTransactionNo.startsWith(currentPrefix)) {
                // Same period → increment
                nextSeq = lastSeq + 1;
            } else {
                // New day/month/year → reset
                nextSeq = 1;
            }
        }

        return formatNumber(config, nextSeq, transactionDate);
    }

    private String formatNumber(TransactionNumberConfig config, int seq, Date now) {
        return resolvePattern(config, now, seq, false);
    }

    private String buildPrefix(TransactionNumberConfig config, Date now) {
        return resolvePattern(config, now, 0, true); // seq ignored
    }

    /*private String formatNumber(TransactionNumberConfig config, int seq, Date now) {
        String seqStr = String.format("%0" + config.getSeqLength() + "d", seq);

        String pattern = config.getFormatPattern();

        // Replace placeholders if present
        if (pattern.contains("{PREFIX}")) {
            pattern = pattern.replace("{PREFIX}", config.getPrefix());
        }

        if (pattern.contains("{STOREID}")) {
            pattern = pattern.replace("{STOREID}", String.format("%02d", sessionService.getOrganizationId()));
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
        if (pattern.contains("{DD}")) {
            pattern = pattern.replace("{DD}", String.format("%02d", AppUtil.getDay(now)));
        }
        if (pattern.contains("{SEQ}")) {
            pattern = pattern.replace("{SEQ}", seqStr);
        }

        return pattern;
    }*/


    /**
     * Replaces placeholders in format pattern.
     *
     * @param config    the transaction config
     * @param date      transaction date
     * @param seq       sequence number (ignored if excludeSeq=true)
     * @param excludeSeq if true, removes {SEQ} instead of replacing
     */
    private String resolvePattern(TransactionNumberConfig config, Date date, int seq, boolean excludeSeq) {
        String pattern = config.getFormatPattern();

        // Replace placeholders
        pattern = pattern.replace("{PREFIX}", config.getPrefix() != null ? config.getPrefix() : "");
        pattern = pattern.replace("{STOREID}", String.format("%02d", sessionService.getOrganizationId()));
        pattern = pattern.replace("{YYYY}", String.valueOf(AppUtil.getYear(date)));
        pattern = pattern.replace("{YY}", String.valueOf(AppUtil.getYear(date)).substring(2));
        pattern = pattern.replace("{MM}", String.format("%02d", AppUtil.getMonth(date)));
        pattern = pattern.replace("{DD}", String.format("%02d", AppUtil.getDay(date)));

        if (pattern.contains("{SEQ}")) {
            if (excludeSeq) {
                pattern = pattern.replace("{SEQ}", "");
            } else {
                String seqStr = String.format("%0" + config.getSeqLength() + "d", seq);
                pattern = pattern.replace("{SEQ}", seqStr);
            }
        }

        return pattern;
    }

}
