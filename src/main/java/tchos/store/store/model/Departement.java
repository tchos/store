package tchos.store.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "departements",
        uniqueConstraints = {@UniqueConstraint(columnNames = "libelleDepartement")}
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "employes")
public class Departement {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "{NotBlank.departement.libelleDepartement}")
    private String libelleDepartement;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL)
    private List<Employe> employes;
}
