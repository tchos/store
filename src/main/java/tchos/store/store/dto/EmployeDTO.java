package tchos.store.store.dto;


import tchos.store.store.model.TypePoste;

import java.util.UUID;

public record EmployeDTO(
        UUID id,
        String nom,
        String email,
        Integer anciennete,
        Integer salaire,
        TypePoste poste,
        String departement
) {
}
