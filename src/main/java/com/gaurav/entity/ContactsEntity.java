package com.gaurav.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "NAMES")
@JsonIgnoreProperties({"first","middle","last"})
public class ContactsEntity implements Serializable {
    private static final long serialVersionUID = 1049701934752652319L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String first;

    @Column
    private String middle;

    @Column
    private String last;

    @Transient
    @Valid
    private Name name;

    @PrePersist
    public void populateFields(){
        this.first = this.name.first;
        this.middle = this.name.middle;
        this.last = this.name.last;
    }

    @PostLoad
    public void populateName(){
        this.name = new Name(this.first, this.middle,  this.last);
    }

    @OneToOne(mappedBy = "contactAddress", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    @Valid
    private AddressEntity address;

    @OneToMany(mappedBy = "contactPhones", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<@Valid PhonesEntity> phone;

    @Column
    @Email
    @NotNull
    @Size(max = 250)
    private String email;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private class Name implements Serializable{

        private static final long serialVersionUID = -3548888657750155105L;

        @NotNull
        @Size(max = 250)
        private String first;

        @NotNull
        @Size(max = 250)
        private String middle;

        @NotNull
        @Size(max = 250)
        private String last;
    }
}
