package com.rony.erpsoft.sales.repository;

import com.rony.erpsoft.sales.dto.DailySalesReportDTO;
import com.rony.erpsoft.sales.model.Order;
import com.rony.erpsoft.sales.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {

    Optional<Order> findByTransactionId(String transactionId);

    List<Order> findByTransactionDateBetweenAndStatusInOrderByIdDesc(
            LocalDateTime startDate,
            LocalDateTime endDate,
            List<OrderStatus> statuses);

    @Query("SELECT new com.rony.erpsoft.sales.dto.DailySalesReportDTO(" +
            "o.paymentType, " +
            "COUNT(DISTINCT o.id), " +                                // distinct orders
            "COALESCE(SUM((SELECT SUM(i.quantity) FROM OrderItem i WHERE i.orderId = o.id)), 0), " + // total qty per order
            "SUM(o.grossAmount), " +
            "SUM(o.discountValue), " +
            "SUM(o.netAmount), " +
            "SUM(o.vatAmount), " +
            "SUM(o.totalAmount)) " +                                  // correct, once per order
            "FROM Order o " +
            "WHERE o.transactionDate BETWEEN :start AND :end " +
            "GROUP BY o.paymentType")
    List<DailySalesReportDTO> findDailySalesReport(
            LocalDateTime start,
            LocalDateTime end
    );
}
