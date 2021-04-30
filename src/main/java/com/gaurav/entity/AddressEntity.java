package com.gaurav.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ADDRESS")
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = 2818149570085127166L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column
    @Size(max = 250)
    private String street;

    @Column
    @Size(max = 250)
    private String city;

    @Column
    @Size(max = 250)
    private String state;

    @Column
    @Size(max = 11)
    private String zip;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NAME_ID", nullable = false)
    @JsonBackReference
    private ContactsEntity contactAddress;
}
