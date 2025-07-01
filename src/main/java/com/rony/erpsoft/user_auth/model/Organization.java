package com.rony.erpsoft.user_auth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "organization")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Organization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @NotNull(message = "Organization name cannot be null")
    @Size(min=1, max=150, message = "Area name should be 1 to 150 chars")
    @Column(name="name")
    String name;
    @Column(name="title")
    String title;
    @Column(name="shortName")
    String shortName;
    @Column(name="address1")
    String address1;
    @Column(name="address2")
    String address2;
    @Column(name="webUrl")
    String webUrl;
    @Column(name="email")
    String email;
    @Column(name="contactName")
    String contactName;
    @Column(name="contactPhone")
    String contactPhone;
    @Column(name="logo")
    String logo;
    @Column(name="isActive")
    boolean isActive;


    @Column(name="created_by")
    private Long createdBy;
    @Column(name="create_on")
    private Date createOn;
    @Column(name="updated_by")
    private Long updatedBy;
    @Column(name="updated_on")
    private Date updatedOn;

}
