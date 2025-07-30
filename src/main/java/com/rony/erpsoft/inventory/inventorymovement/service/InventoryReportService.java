package com.rony.erpsoft.inventory.inventorymovement.service;

import com.rony.erpsoft.accounts.model.AccLedgerDto;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryMovementResponseDTO;
import com.rony.erpsoft.inventory.inventorymovement.dto.InventoryTransactionSearchDTO;
import com.rony.erpsoft.inventory.inventorymovement.dto.ItemLedgerProjection;
import com.rony.erpsoft.inventory.itemmaster.model.ItemMaster;
import com.rony.erpsoft.inventory.itemmaster.repository.ItemMasterRepository;
import com.rony.erpsoft.inventory.itemmaster.service.ItemMasterService;
import com.rony.erpsoft.user_auth.model.Organization;
import com.rony.erpsoft.user_auth.service.SessionService;
import com.rony.erpsoft.utils.AppUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class InventoryReportService {

    public static final String INVENTORY_REPORTS_DIR = "/view/inventory/report/jrxml/";

    private final SessionService sessionService;
    private final HttpServletRequest request;
    private final InventoryTransactionService inventoryTransactionService;
    private final InventoryMovementService inventoryMovementService;
    private final ItemMasterService itemMasterService;
    private final ItemMasterRepository itemMasterRepository;

    public ByteArrayResource itemLedger(InventoryTransactionSearchDTO searchDTO) {
        try {

            String fileName = "", report_title = "Item Ledger Report";
            List<ItemLedgerProjection> itemLedgerData;
            Organization organization = sessionService.getOrganization();
            if ("Summary".equalsIgnoreCase(searchDTO.getReportType())) {
                fileName = "inventoryItemLedgerSummary";
                itemLedgerData = inventoryTransactionService.findItemLedgerSummaryData(searchDTO);
                report_title = "Item Ledger Report(Summary)";
            } else {
                fileName = "inventoryItemLedger";
                itemLedgerData = inventoryTransactionService.findItemLedgerData(searchDTO);
            }
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(itemLedgerData);


            Map<String, Object> parameters = new HashMap<>();
            parameters.put("from_date", convertToDate(searchDTO.getFromDate()));
            parameters.put("to_date", convertToDate(searchDTO.getToDate()));
            parameters.put("warehouse", searchDTO.getWarehouse());
            parameters.put("report_type", searchDTO.getReportType());
            parameters.put("organization_name", organization.getName());
            parameters.put("organization_address", organization.getAddress1());
            parameters.put("report_title", report_title);

            return exportReportToPdf(dataSource, fileName, parameters);

        } catch (Exception e) {
            log.info("Item ledger preview report: {}", e.getMessage());
            return null;
        }
    }

    public ByteArrayResource rcvIssuePreview(long id) {
        try {

            String fileName = "inventoryRcvIssue";
            Organization organization = sessionService.getOrganization();
            InventoryMovementResponseDTO responseDTO = inventoryMovementService.findById(id);

            Map<String, String> itemCodeNameMap = itemMasterRepository.findAll().stream()
                    .collect(Collectors.toMap(ItemMaster::getItemCode, ItemMaster::getItemName));

            responseDTO.getDetails().forEach(item -> {
                String itemName = itemCodeNameMap.get(item.getItemCode());
                item.setItemNameCode(itemName + " [" + item.getItemCode() + "]");
            });

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(responseDTO.getDetails());

            String action = responseDTO.getAction().toString().toLowerCase();
            String verb = switch (action) {
                case "transfer" -> "Transferred";
                case "issue" -> "Issued";
                default -> "Received";
            };

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("organization_name", organization.getName());
            parameters.put("organization_address", organization.getAddress1());
            parameters.put("report_title", "Material Received Report".replace("Received", verb));
            parameters.put("warehouse", responseDTO.getWarehouse());
            parameters.put("transactionNo", responseDTO.getTransactionId());
            parameters.put("transactionDate", formatDate(responseDTO.getTransactionDate()));
            parameters.put("rcvIssueBy", "Received by........................".replace("Received", verb));
            parameters.put("rcvIssueDate", "Date Received....................".replace("Received", verb));
            parameters.put("action", responseDTO.getAction().toString());
            parameters.put("toWarehouse", responseDTO.getToWarehouse());
            parameters.put("fromWarehouse", responseDTO.getFromWarehouse());


            return exportReportToPdf(dataSource, fileName, parameters);

        } catch (Exception e) {
            log.info("Receive/Issue preview report: {}", e.getMessage());
            return null;
        }
    }

    private ByteArrayResource exportReportToPdf(JRBeanCollectionDataSource dataSource, String fileName, Map<String, Object> parameters) {

        try {
            URL resourceUrl = request.getSession().getServletContext().getResource(INVENTORY_REPORTS_DIR + fileName + ".jrxml");
            File file = new File(resourceUrl.toURI());

            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            byte[] reportContent = outputStream.toByteArray();

            return new ByteArrayResource(reportContent, fileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            log.info("REPORT: {}", ex.getMessage());
            return null;
        }
    }

    public Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public String formatDate(LocalDateTime dateTime) {
        return dateTime != null
                ? dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                : null;
    }

}
