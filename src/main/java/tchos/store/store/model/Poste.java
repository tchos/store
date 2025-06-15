package tchos.store.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "postes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "employes")
public class Poste {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TypePoste libellePoste;

    @Column(nullable = false)
    @Min(value = 42000, message = "{Min.poste.salaireMin}")
    private Integer salaireMin;

    @Column(nullable = false)
    @Max(value = 1000000, message = "{Max.poste.salaireMax}")
    private Integer salaireMax;

    @OneToMany(mappedBy = "poste", cascade = CascadeType.ALL)
    private List<Employe> employes;

    @AssertTrue(message = "Le salaire maximum doit être supérieur au salire minimum")
    public boolean isSalaireValide() {
        if (salaireMin == null || salaireMax == null) return true; // pour éviter NullPointerException
        return salaireMax > salaireMin;
    }
}


