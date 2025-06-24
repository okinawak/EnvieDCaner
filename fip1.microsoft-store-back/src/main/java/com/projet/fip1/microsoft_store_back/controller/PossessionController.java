package com.projet.fip1.microsoft_store_back.controller;

import com.projet.fip1.microsoft_store_back.entities.Application;
import com.projet.fip1.microsoft_store_back.entities.Possession;
import com.projet.fip1.microsoft_store_back.entities.PossessionId;
import com.projet.fip1.microsoft_store_back.entities.Utilisateur;
import com.projet.fip1.microsoft_store_back.repository.ApplicationRepository;
import com.projet.fip1.microsoft_store_back.repository.PossessionRepository;
import com.projet.fip1.microsoft_store_back.repository.UtilisateurRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/possessions")
@CrossOrigin(origins = "http://localhost:5173")
public class PossessionController {

    @Autowired
    private PossessionRepository possessionRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> ajouterPossession(@RequestBody Possession possession) {        
        if (possession == null || possession.getId() == null ||
                possession.getId().getId_u() == null || possession.getId().getId_a() == null) {
            return ResponseEntity.badRequest().body("ID utilisateur ou application manquant.");
        }


        //Mise à jour du solde 

        Optional<Utilisateur> optionalUser = utilisateurRepository.findById(possession.getId().getId_u());
        Utilisateur user = optionalUser.get();

        Optional<Application> optionalApp = applicationRepository.findById(possession.getId().getId_a());
        Application app = optionalApp.get();

        user.setSolde(user.getSolde().subtract(app.getPrix()));

        // enregistrement de la posssesion

        PossessionId possessionId = possession.getId();

        if (possessionRepository.existsById(possessionId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("L'utilisateur possède déjà cette application.");
        }

        try {
            Possession saved = possessionRepository.save(possession);
            utilisateurRepository.save(user);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'enregistrement : " + e.getMessage());
        }
    }


    @GetMapping("/{userId}/{appId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<Void> verifierPossession(@PathVariable Long userId, @PathVariable Long appId) {
        PossessionId id = new PossessionId();
        id.setId_u(userId);
        id.setId_a(appId);
        boolean exists = possessionRepository.existsById(id);
        if (exists) {
            return ResponseEntity.ok().build();  // 200 OK sans corps (ou on peux renvoyer un corps si on veux)
        } else {
            return ResponseEntity.notFound().build();  // 404 Not Found
        }
    }

    // DELETE - Désinstallation
    @DeleteMapping("/{userId}/{appId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> supprimerPossession(@PathVariable Long userId, @PathVariable Long appId) {
        PossessionId id = new PossessionId();
        id.setId_u(userId);
        id.setId_a(appId);

        if (!possessionRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La possession n'existe pas.");
        }

        try {
            possessionRepository.deleteById(id);
            return ResponseEntity.ok("Possession supprimée avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression.");
        }
    }

    @GetMapping("/{userId}/applications")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<List<Application>> getApplicationsByUser(@PathVariable Long userId) {
        // 1. Récupérer les IDs des applications possédées par l'utilisateur
        List<Long> appIds = possessionRepository.findAppIdsByUserId(userId);

        if (appIds.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        // 2. Récupérer les applications correspondantes aux IDs récupérés
        List<Application> apps = applicationRepository.findByIdAIn(appIds);

        // 3. Retourner la liste
        return ResponseEntity.ok(apps);
    }

}
