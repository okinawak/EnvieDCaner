package com.projet.fip1.microsoft_store_back.entities;


import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Map;

@RestController
@RequestMapping("/api/git")
@CrossOrigin(origins = "http://localhost:5173")
public class GitController {

    @PostMapping("/clone")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<String> cloneRepo(@RequestBody Map<String, String> payload) {
        String repoUrl = payload.get("url");
        String username = payload.get("username");
        String password = payload.get("password");

        try {
            File targetDir = new File("clones/" + System.currentTimeMillis()); // exemple : clones/1685638494
            CloneCommand clone = Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(targetDir);

            if (username != null && !username.isEmpty()) {
                clone.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
            }

            clone.call();
            return ResponseEntity.ok("Clonage réussi !");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Échec du clonage : " + e.getMessage());
        }
    }
}