package tchos.store.store.repository;

import tchos.store.store.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// JpaRepository<Departement, UUID> Departement = Nom entite et UUID = type de la clef primaire de l'entité Departement

@Repository
public interface DepartementRepository extends JpaRepository<Departement, UUID> {
    boolean existsDistinctByLibelleDepartement(String libelleDepartement);
}
