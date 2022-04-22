package com.example.centrum_dobrej_terapii.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @Column(unique = true)
    private String path;
    @NotBlank
    @Column(unique = true)
    private String name;
    @ManyToOne
    AppUser doctor;
    @ManyToOne
    AppUser patient;

    public Document(String path, String filename, AppUser doctor, AppUser patient) {
        this.path = path;
        this.name = filename;
        this.doctor = doctor;
        this.patient = patient;
    }
}
