package com.example.centrum_dobrej_terapii.entities;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Document {
    @Id
    @NotNull
    @NotEmpty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String path;
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
