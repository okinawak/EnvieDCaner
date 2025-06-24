package com.projet.fip1.microsoft_store_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.projet.fip1.microsoft_store_back.entities.Utilisateur;
import com.projet.fip1.microsoft_store_back.repository.UtilisateurRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "http://localhost:5173") // <- autorise React à appeler cette API
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/signup")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> inscrireUtilisateur(
            @RequestParam("username") String pseudo,
            @RequestParam("email") String email,
            @RequestParam("password") String mdp,
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture
    ) {
        if (utilisateurRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email déjà utilisé.");
        }

        Utilisateur u = new Utilisateur();
        u.setPseudo(pseudo);
        u.setEmail(email);
        u.setMdp(mdp); // À hacher plus tard
        u.setStatut("Utilisateur");
        u.setSolde(BigDecimal.ZERO);

        try {
            if (profilePicture != null && !profilePicture.isEmpty()) {
                String base64 = Base64.getEncoder().encodeToString(profilePicture.getBytes());
                u.setPhoto(base64);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur traitement image.");
        }

        utilisateurRepository.save(u);
        return ResponseEntity.ok("Inscription réussie !");
    }


    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String mdp = credentials.get("password");

        Optional<Utilisateur> user = utilisateurRepository.findByEmail(email);

        if (user.isPresent() && user.get().getMdp().equals(mdp)) {
            Utilisateur u = user.get();
            return ResponseEntity.ok(Map.of(
                    "id", u.getId_u(),
                    "pseudo", u.getPseudo(),
                    "email", u.getEmail(),
                    "profilePicture", u.getPhoto(),
                    "ownedGames", new ArrayList<>(), // à adapter plus tard
                    "statut", u.getStatut(),
                    "solde",u.getSolde()
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<Utilisateur> updateUtilisateur(
            @PathVariable Long id,
            @RequestBody Utilisateur updatedUser
    ) {
        
        Optional<Utilisateur> optionalUser = utilisateurRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Utilisateur user = optionalUser.get();
        user.setPseudo(updatedUser.getPseudo());
        user.setEmail(updatedUser.getEmail());
        // user.setSolde(u);

        utilisateurRepository.save(user);

        return ResponseEntity.ok(user);
    }

    // (Optionnel) Pour tester : récupérer un utilisateur par ID
    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable Long id) {
        System.out.println("je passe la ");
        return utilisateurRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}