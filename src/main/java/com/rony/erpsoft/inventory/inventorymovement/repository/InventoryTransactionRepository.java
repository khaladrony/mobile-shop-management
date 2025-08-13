package com.rony.erpsoft.inventory.inventorymovement.repository;

import com.rony.erpsoft.inventory.inventorymovement.dto.ItemLedgerProjection;
import com.rony.erpsoft.inventory.inventorymovement.model.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long>,
        JpaSpecificationExecutor<InventoryTransaction> {

    @Query("SELECT max(transactionId) FROM InventoryTransaction WHERE sign=?1")
    String findLastTransactionId(int sign);

    @Query(value = """
            WITH params AS (
                SELECT
                    DATE(:fromDate) AS from_date,
                    DATE(:toDate) AS to_date
            ),
            
            transaction_data AS (
                SELECT
                    inventory.transaction_date,
                    inventory.document_no,
                    inventory.item_code,
                    inventory.warehouse,
                    item.item_name,
                    inventory.quantity,
                    inventory.sign,
                    DATE(inventory.transaction_date) AS trx_date,
            
                    CASE
                        WHEN inventory.sign > 0 
                            AND inventory.transaction_date >= (SELECT from_date FROM params)
                            AND inventory.transaction_date < (SELECT to_date FROM params) + INTERVAL 1 DAY
                                THEN inventory.quantity
                        ELSE 0
                    END AS receive_qty,
            
                    CASE
                        WHEN inventory.sign < 0 
                            AND inventory.transaction_date >= (SELECT from_date FROM params)
                            AND inventory.transaction_date < (SELECT to_date FROM params) + INTERVAL 1 DAY
                                THEN inventory.quantity
                        ELSE 0
                    END AS issue_qty,
            
                    (inventory.quantity * inventory.sign) AS net_qty
                FROM inventory_transaction inventory
                JOIN item_master item ON item.item_code = inventory.item_code
                WHERE
                    inventory.transaction_date < (SELECT to_date FROM params) + INTERVAL 1 DAY
                    AND inventory.warehouse = :warehouse
                    AND (:itemCode IS NULL OR inventory.item_code = :itemCode)
            ),
            
            cumulative_qty AS (
                SELECT
                    *,
                    SUM(net_qty) OVER (
                        PARTITION BY item_code, warehouse
                        ORDER BY transaction_date
                        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                    ) AS closing_qty
                FROM transaction_data
            ),
            
            opening_balance AS (
                SELECT
                    im.item_name,
                    it.item_code,
                    it.warehouse,
                    SUM(it.quantity * it.sign) AS opening_qty
                FROM inventory_transaction it
                JOIN item_master im ON im.item_code = it.item_code
                WHERE it.transaction_date < (SELECT from_date FROM params)
                    AND it.warehouse = :warehouse
                    AND (:itemCode IS NULL OR it.item_code = :itemCode)
                GROUP BY it.item_code, it.warehouse, im.item_name
            )
            
            -- Final union: synthetic opening + actual transactions in date range
            SELECT
                CONCAT(ob.item_name, ' [', ob.item_code, ']') AS item_name_code,
                (SELECT from_date FROM params) AS transaction_date,
                'Opening' AS document_no,
                ob.opening_qty AS opening_qty,
                0 AS receive_qty,
                0 AS issue_qty,
                ob.opening_qty AS closing_qty
            FROM opening_balance ob
            
            UNION ALL
            
            SELECT
                CONCAT(c.item_name, ' [', c.item_code, ']') AS item_name_code,
                c.transaction_date,
                c.document_no,
            
                COALESCE(
                    LAG(c.closing_qty) OVER (
                        PARTITION BY c.item_code, c.warehouse
                        ORDER BY c.transaction_date
                    ),
                    ob.opening_qty,
                    0
                ) AS opening_qty,
            
                c.receive_qty,
                c.issue_qty,
                c.closing_qty
            
            FROM cumulative_qty c
            LEFT JOIN opening_balance ob
                ON c.item_code = ob.item_code AND c.warehouse = ob.warehouse
            WHERE c.transaction_date >= (SELECT from_date FROM params)
                  AND c.transaction_date <  (SELECT to_date FROM params) + INTERVAL 1 DAY
            
            ORDER BY item_name_code, transaction_date;
            
            """, nativeQuery = true)
    List<ItemLedgerProjection> findItemLedgerData(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            @Param("warehouse") String warehouse,
            @Param("itemCode") String itemCode
    );

    @Query(value = """
        SELECT
            CONCAT(i.item_name, ' [', t.item_code, ']') AS item_name_code,
            IFNULL(SUM(CASE
                        WHEN t.transaction_date < :fromDate THEN t.quantity * t.sign
                        ELSE 0
                    END),
                    0) AS opening_qty,
            IFNULL(SUM(CASE
                        WHEN
                            t.sign > 0
                                AND t.transaction_date BETWEEN :fromDate AND :toDate
                        THEN
                            t.quantity
                        ELSE 0
                    END),
                    0) AS receive_qty,
            IFNULL(SUM(CASE
                        WHEN
                            t.sign < 0
                                AND t.transaction_date BETWEEN :fromDate AND :toDate
                        THEN
                            t.quantity
                        ELSE 0
                    END),
                    0) AS issue_qty,
            IFNULL(SUM(CASE
                        WHEN t.transaction_date <= '2025-07-30' THEN t.quantity * t.sign
                        ELSE 0
                    END),
                    0) AS closing_qty
        FROM
            inventory_transaction t
                JOIN
            item_master i ON i.item_code = t.item_code
        WHERE
            t.transaction_date <= :toDate
                AND t.warehouse = :warehouse
                AND (:itemCode IS NULL OR t.item_code = :itemCode)
        GROUP BY t.item_code , i.item_name
        ORDER BY i.item_name;
    """, nativeQuery = true)
    List<ItemLedgerProjection> findItemLedgerSummaryData(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            @Param("warehouse") String warehouse,
            @Param("itemCode") String itemCode
    );
}
