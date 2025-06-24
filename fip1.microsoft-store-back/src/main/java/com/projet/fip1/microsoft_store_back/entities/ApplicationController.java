package com.projet.fip1.microsoft_store_back.entities;

import com.projet.fip1.microsoft_store_back.entities.Application;
import com.projet.fip1.microsoft_store_back.entities.ApplicationRepository;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:5173")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<String> addApplication(@RequestBody ApplicationWithGitDTO dto) {
        try {
            // Création de l'objet Application
            Application application = new Application();
            application.setNom(dto.nom);
            application.setDescription(dto.description);
            application.setVersion(dto.version);
            application.setNote_de_mise_a_jour(dto.note_de_mise_a_jour);
            application.setLogo(dto.logo);
            application.setPrix(new BigDecimal(dto.prix));

            applicationRepository.save(application);

            // Clonage Git si URL fournie
            if (dto.gitUrl != null && !dto.gitUrl.isEmpty()) {
                File targetDir = new File("clones/" + application.getNom().replaceAll("\\s+", "_") + "_" + System.currentTimeMillis());

                CloneCommand clone = Git.cloneRepository()
                        .setURI(dto.gitUrl)
                        .setDirectory(targetDir);

                if (dto.gitUsername != null && !dto.gitUsername.isEmpty()) {
                    clone.setCredentialsProvider(new UsernamePasswordCredentialsProvider(dto.gitUsername, dto.gitPassword));
                }

                clone.call();
            }

            return ResponseEntity.ok("Application ajoutée et dépôt cloné (si URL fournie)");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }
}
