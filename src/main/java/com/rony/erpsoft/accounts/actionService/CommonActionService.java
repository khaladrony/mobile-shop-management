package com.rony.erpsoft.accounts.actionService;

import com.rony.erpsoft.accounts.model.enums.AccountsType;
import com.rony.erpsoft.accounts.model.enums.VoucherStatus;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonActionService {


    public List<AccountsType> getAccountsTypeList(){
        return AccountsType.list();
    }

    public List<VoucherStatus> getVoucherStatusList(){
        List<VoucherStatus> list = new ArrayList<>();
        list.add(VoucherStatus.DRAFT);
        list.add(VoucherStatus.POSTED);
        return list;
    }

    public ResponseEntity<Resource> reportHeader(String fileName, ByteArrayResource resource){
        HttpHeaders header = new HttpHeaders();
        String headerValue = "inline;filename=" + fileName + ".pdf";
        header.add(HttpHeaders.CONTENT_DISPOSITION, headerValue);

        return ResponseEntity.ok()
                .headers(header)
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}
