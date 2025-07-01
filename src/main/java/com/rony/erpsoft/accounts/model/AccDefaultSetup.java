package com.rony.erpsoft.accounts.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;


@Entity
@Data
@Table(name = "acc_default_setup")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AccDefaultSetup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Organization ID cannot be null")
    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "voucher_print_view")
    private String voucherPrintView;        //A4, A5
}
