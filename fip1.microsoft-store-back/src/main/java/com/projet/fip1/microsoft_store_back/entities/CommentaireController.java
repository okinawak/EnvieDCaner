package com.projet.fip1.microsoft_store_back.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/commentaires")
@CrossOrigin(origins = "http://localhost:5173")
public class CommentaireController {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private CommentaireAssociationRepository commentaireAssociationRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // GET - Obtenir les statistiques de rating d'une application
    @GetMapping("/application/{appId}/stats")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<Map<String, Object>> getRatingStats(@PathVariable Long appId) {
        try {
            Double averageRating = commentaireRepository.findAverageRatingByApplicationId(appId);
            Long commentCount = commentaireRepository.countCommentairesByApplicationId(appId);
            
            Map<String, Object> stats = Map.of(
                "applicationId", appId,
                "averageRating", averageRating != null ? averageRating : 0.0,
                "commentCount", commentCount != null ? commentCount : 0L
            );
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET - Récupérer tous les commentaires d'une application
    @GetMapping("/application/{appId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<List<CommentaireDTO>> getCommentairesByApplication(@PathVariable Long appId) {
        try {
            List<CommentaireDTO> commentaires = commentaireRepository.findCommentairesByApplicationId(appId);
            return ResponseEntity.ok(commentaires);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST - Ajouter un commentaire à une application
    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> ajouterCommentaire(@RequestBody Map<String, Object> request) {
        try {
            // Extraire les données de la requête
            String contenu = (String) request.get("contenu");
            Integer note = (Integer) request.get("note");
            Long idU = Long.valueOf(request.get("id_u").toString());
            Long idA = Long.valueOf(request.get("id_a").toString());

            // Vérifier que l'utilisateur et l'application existent
            if (!utilisateurRepository.existsById(idU) || !applicationRepository.existsById(idA)) {
                return ResponseEntity.badRequest().body("Utilisateur ou application introuvable");
            }

            // Créer le commentaire
            Commentaire commentaire = new Commentaire();
            commentaire.setContenu(contenu);
            commentaire.setNote(note);
            commentaire.setId_u(idU);
            
            Commentaire savedCommentaire = commentaireRepository.save(commentaire);

            // Créer l'association commentaire-application
            CommentaireAssociation association = new CommentaireAssociation();
            CommentaireAssociationId associationId = new CommentaireAssociationId();
            associationId.setId_a(idA);
            associationId.setId_c(savedCommentaire.getId_c());
            association.setId(associationId);
            
            commentaireAssociationRepository.save(association);

            return ResponseEntity.ok("Commentaire ajouté avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'ajout du commentaire : " + e.getMessage());
        }
    }

    // PUT - Modifier un commentaire
    @PutMapping("/{commentaireId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> modifierCommentaire(@PathVariable Long commentaireId, 
                                                @RequestBody Map<String, Object> request) {
        try {
            Optional<Commentaire> optionalCommentaire = commentaireRepository.findById(commentaireId);
            
            if (optionalCommentaire.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Commentaire commentaire = optionalCommentaire.get();
            
            // Extraire les données
            String contenu = (String) request.get("contenu");
            Integer note = (Integer) request.get("note");
            Long idU = Long.valueOf(request.get("id_u").toString());
            
            // Vérifier que l'utilisateur est le propriétaire du commentaire
            if (!commentaire.getId_u().equals(idU)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Vous ne pouvez modifier que vos propres commentaires");
            }

            commentaire.setContenu(contenu);
            commentaire.setNote(note);
            
            commentaireRepository.save(commentaire);

            return ResponseEntity.ok("Commentaire modifié avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la modification : " + e.getMessage());
        }
    }

    // DELETE - Supprimer un commentaire
    @DeleteMapping("/{commentaireId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> supprimerCommentaire(@PathVariable Long commentaireId,
                                                 @RequestParam Long userId) {
        try {
            Optional<Commentaire> optionalCommentaire = commentaireRepository.findById(commentaireId);
            
            if (optionalCommentaire.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Commentaire commentaire = optionalCommentaire.get();
            
            // Vérifier que l'utilisateur est le propriétaire du commentaire
            if (!commentaire.getId_u().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Vous ne pouvez supprimer que vos propres commentaires");
            }

            // Supprimer les associations
            commentaireAssociationRepository.deleteByIdIdC(commentaireId);
            
            // Supprimer le commentaire
            commentaireRepository.deleteById(commentaireId);

            return ResponseEntity.ok("Commentaire supprimé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    // GET - Récupérer les commentaires d'un utilisateur
    @GetMapping("/utilisateur/{userId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<List<Commentaire>> getCommentairesByUser(@PathVariable Long userId) {
        try {
            List<Commentaire> commentaires = commentaireRepository.findByIdU(userId);
            return ResponseEntity.ok(commentaires);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}