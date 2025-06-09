package tchos.store.store.repository;

import tchos.store.store.model.Poste;
import tchos.store.store.model.TypePoste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// JpaRepository<Poste, UUID> Poste = Nom entite et UUID = type de la clef primaire de l'entité Poste

@Repository
public interface PosteRepository extends JpaRepository<Poste, UUID> {
    boolean existsDistinctByLibellePoste(TypePoste libellePoste);
}


