package com.gaurav.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PHONES")
public class PhonesEntity implements Serializable {

    private static final long serialVersionUID = 6950261129193445471L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column
    @NotBlank
    @Size(max = 20)
    private String number;

    @Column
    @Size(max = 6)
    @Pattern(regexp = "home|mobile|work")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NAME_ID", nullable = false)
    @JsonBackReference
    private ContactsEntity contactPhones;
}
