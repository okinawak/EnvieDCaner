package com.projet.fip1.microsoft_store_back.controller;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projet.fip1.microsoft_store_back.entities.Application;
import com.projet.fip1.microsoft_store_back.entities.ApplicationWithGitDTO;
import com.projet.fip1.microsoft_store_back.repository.ApplicationRepository;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:5173")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // Obtenir les applications d’un auteur spécifique
    @GetMapping("/auteur/{idAuteur}")
    public ResponseEntity<List<Application>> getApplicationsByAuteur(@PathVariable Long idAuteur) {
        List<Application> apps = applicationRepository.findByIdAuteur(idAuteur);
        return ResponseEntity.ok(apps);
    }

    // Ajouter une nouvelle application
    @PostMapping
    public ResponseEntity<String> addApplication(@RequestBody ApplicationWithGitDTO dto) {
        try {
            Application application = new Application();
            application.setNom(dto.nom);
            application.setDescription(dto.description);
            application.setVersion(dto.version);
            application.setNote_de_mise_a_jour(dto.note_de_mise_a_jour);
            application.setLogo(dto.logo);
            application.setPrix(new BigDecimal(dto.prix));
            application.setId_auteur(dto.id_auteur);
            application.setGitUsername(dto.gitUsername);
            application.setGitPassword(dto.gitPassword);

            applicationRepository.save(application);

            // Clonage Git si fourni
            if (dto.gitUrl != null && !dto.gitUrl.isEmpty()) {
                File targetDir = new File("clones/" + application.getNom().replaceAll("\\s+", "_"));

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


    @GetMapping("/recommandation/{id}")
    @CrossOrigin(origins = "http://localhost:5173")
    public List<Application> getAll(@PathVariable Long id){
        return applicationRepository.getAppRecommandation(id);
    }

    // Modifier une application existante
    @PutMapping("/{id_a}")
    public ResponseEntity<String> updateApplication(@PathVariable Long id_a, @RequestBody ApplicationWithGitDTO dto) {
        Optional<Application> optionalApp = applicationRepository.findById(id_a);

        if (optionalApp.isEmpty()) {
            return ResponseEntity.status(404).body("Application non trouvée");
        }

        try {
            Application app = optionalApp.get();
            System.out.println("bal1");
            // Mise à jour des champs
            app.setNom(dto.nom);
            app.setDescription(dto.description);
            app.setVersion(dto.version);
            app.setNote_de_mise_a_jour(dto.note_de_mise_a_jour);
            app.setLogo(dto.logo);
            app.setPrix(new BigDecimal(dto.prix));
            app.setId_auteur(dto.id_auteur);

            applicationRepository.save(app);
            System.out.println("bal2");
            // Effectuer un pull Git dans le dossier cloné
            File repoDir = new File("clones/" + app.getNom().replaceAll("\\s+", "_"));
            System.out.println("bal3");
            if (repoDir.exists() && repoDir.isDirectory()) {
                System.out.println("bal4");
                try (Git git = Git.open(repoDir)) {
                    var pullCommand = git.pull();
                    String gitUser = dto.gitUsername != null && !dto.gitUsername.isEmpty()
                            ? dto.gitUsername
                            : app.getGitUsername();

                    String gitPass = dto.gitPassword != null && !dto.gitPassword.isEmpty()
                            ? dto.gitPassword
                            : app.getGitPassword();

                    if (gitUser != null && !gitUser.isEmpty()) {
                        pullCommand.setCredentialsProvider(
                                new UsernamePasswordCredentialsProvider(gitUser, gitPass)
                        );
                    }
                    if (dto.gitUsername != null && !dto.gitUsername.isEmpty()) {
                        app.setGitUsername(dto.gitUsername);
                        app.setGitPassword(dto.gitPassword);
                    }
                    pullCommand.call();
                }
            } else {
                System.out.println("Répertoire git local non trouvé, impossible de faire un pull");
            }

            return ResponseEntity.ok("Application mise à jour avec succès, dépôt git pull effectué");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    @PostMapping("/{id_a}/install")
    public ResponseEntity<String> installApplication(@PathVariable Long id_a) {
        Optional<Application> optionalApp = applicationRepository.findById(id_a);

        if (optionalApp.isEmpty()) {
            return ResponseEntity.status(404).body("Application non trouvée");
        }

        try {
            Application app = optionalApp.get();
            String appNameSanitized = app.getNom().replaceAll("\\s+", "_");

            File cloneDir = new File("clones/" + appNameSanitized);
            File installDir = new File("applications_client/" + appNameSanitized);

            // Étape 1 : Git Pull
            if (cloneDir.exists() && cloneDir.isDirectory()) {
                try (Git git = Git.open(cloneDir)) {
                    var pullCommand = git.pull();

                    if (app.getGitUsername() != null && !app.getGitUsername().isEmpty()) {
                        pullCommand.setCredentialsProvider(
                                new UsernamePasswordCredentialsProvider(app.getGitUsername(), app.getGitPassword())
                        );
                    }

                    pullCommand.call();
                    System.out.println("Git pull effectué pour : " + appNameSanitized);
                }
            } else {
                return ResponseEntity.status(500).body("Le dossier du dépôt local n'existe pas pour : " + appNameSanitized);
            }

            // Étape 2 : Copier vers /applications_client/
            if (installDir.exists()) {
                FileUtils.deleteDirectory(installDir); // Nettoyage ancien dossier
            }

            FileUtils.copyDirectory(cloneDir, installDir);
            System.out.println("Copie vers applications_client réussie");

            return ResponseEntity.ok("Installation réussie : pull + copie");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de l'installation : " + e.getMessage());
        }
    }


    public static void copyDirectory(File source, File destination) throws java.io.IOException {
        if (destination.exists()) {
            deleteDirectory(destination); // supprime l'existant pour éviter les conflits
        }
        org.apache.commons.io.FileUtils.copyDirectory(source, destination);
    }

    public static void deleteDirectory(File dir) throws java.io.IOException {
        if (dir.exists()) {
            org.apache.commons.io.FileUtils.deleteDirectory(dir);
        }
    }

}
package com.projet.fip1.microsoft_store_back.controller;

import com.projet.fip1.microsoft_store_back.entities.Application;
import com.projet.fip1.microsoft_store_back.entities.ApplicationRepository;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.projet.fip1.microsoft_store_back.entities.Application;
import com.projet.fip1.microsoft_store_back.entities.ApplicationWithGitDTO;
import com.projet.fip1.microsoft_store_back.repository.ApplicationRepository;

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
