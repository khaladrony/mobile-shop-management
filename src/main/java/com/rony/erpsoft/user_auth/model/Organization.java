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
    private String name;

    @Column(name="title")
    private String title;

    @Column(name="short_name")
    private String shortName;

    @Column(name="address1")
    private String address1;

    @Column(name="address2")
    private String address2;

    @Column(name="web_url")
    private String webUrl;

    @Column(name="email")
    private String email;

    @Column(name="contact_name")
    private String contactName;

    @Column(name="contact_phone")
    private String contactPhone;

    @Column(name="logo")
    private String logo;

    @Column(name="active")
    private boolean active;


    @Column(name="created_by")
    private Long createdBy;
    @Column(name="create_on")
    private Date createOn;
    @Column(name="updated_by")
    private Long updatedBy;
    @Column(name="updated_on")
    private Date updatedOn;

}
