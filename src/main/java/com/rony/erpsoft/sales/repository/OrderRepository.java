package com.rony.erpsoft.sales.repository;

import com.rony.erpsoft.sales.dto.DailySalesReportDTO;
import com.rony.erpsoft.sales.dto.SalesByItemReportDTO;
import com.rony.erpsoft.sales.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {

    Optional<Order> findByTransactionId(String transactionId);

    @Query("SELECT new com.rony.erpsoft.sales.dto.DailySalesReportDTO(" +
            "o.paymentType, " +
            "COUNT(DISTINCT o.id), " +                                // distinct orders
            "COALESCE(SUM((SELECT SUM(i.quantity) FROM OrderItem i WHERE i.orderId = o.id)), 0), " + // total qty per order
            "SUM(o.totalAmount)) " +                                  // correct, once per order
            "FROM Order o " +
            "WHERE o.transactionDate BETWEEN :start AND :end " +
            "GROUP BY o.paymentType")
    List<DailySalesReportDTO> findDailySalesReport(
            LocalDateTime start,
            LocalDateTime end
    );

    @Query("SELECT new com.rony.erpsoft.sales.dto.SalesByItemReportDTO(" +
            "oi.itemCode, oi.itemName, SUM(oi.quantity), SUM(oi.total)) " +
            "FROM OrderItem oi " +
            "JOIN Order o ON oi.orderId = o.id " +
            "WHERE o.transactionDate BETWEEN :startDate AND :endDate " +
            "GROUP BY oi.itemCode, oi.itemName " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<SalesByItemReportDTO> getSalesByItemReport(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
