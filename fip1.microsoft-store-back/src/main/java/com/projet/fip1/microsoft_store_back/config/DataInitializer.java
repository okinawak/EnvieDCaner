package com.projet.fip1.microsoft_store_back.config;

import com.projet.fip1.microsoft_store_back.entities.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(
            UtilisateurRepository utilisateurRepository, 
            ApplicationRepository applicationRepository,
            CommentaireRepository commentaireRepository,
            CommentaireAssociationRepository commentaireAssociationRepository) {
        return args -> {
            // Créer les utilisateurs
            Optional<Utilisateur> existing = utilisateurRepository.findByEmail("deez@gmail.com");

            Utilisateur deez;
            if (existing.isEmpty()) {
                deez = new Utilisateur();
                deez.setPseudo("deez");
                deez.setEmail("deez@gmail.com");
                deez.setMdp("deez");
                deez.setStatut("utilisateur");
                deez.setSolde(BigDecimal.ZERO);
                deez.setPhoto("UklGRoAbAABXRUJQVlA4WAoAAAAIAAAA2QEA6gEAVlA4ILgaAACwoQCdASraAesBPpFGn0ulo6KhodD5oLASCWlu/CiX4LIkcN15KimAe0AWKX87/ov+p/rPcV/Y/6//gP1t9x+ubuMYM7TP479xf239r9x/8/3y/KPUC/H/5n/w/7X+NvDHAB+sX/S/unr+e+f+D+2ep32P/33uA/rJ/1f6955Xhleg+wF/JP7f+xHvF/2n7U+nL6s9ID9f+2B6RYrsX752P3zsfvnY/fOx++dj987H70ODUTPnJ8RifpnBZGa4d40qPce94wg4P/2dB1jTfvZxUHVrCEiJi9UYIXMq+RUU3uX+XKzYaAsZXWWnb+4MiHsGOT5fJrhQqOM4VB1awhIiW1XfuSLKRhAK1hCOX/ihedB9ljwEdwnEToOrWEJETFFOmZzFkv0GTtIKXevy29T7tLbHm6sGIi1iHRfarWEJETFFPo6cwaECHv+Gmv//HcdQNOZTcvaFFelcIoS7N1R75NrXirJEbyckpyI6F6K1lyJi/fOx+9bM34SwqAE6ONDpDv8DQT2dYr1Efmn4wW4jZ4XUR4ohQRidXcSavFfrnWHdTUmiOTwQkRMX750nHIokhljXrBJpQDMjf3tEVVHuler6qNUonFm+9EfC+CkSa3eEcOSO851IQ4lrF9qtYQjsrFdeyFHhHpnxP5jh2W+Y5c1rDKWJAOpNP93L+4pwYhkTtNTN5+65y9AqIRRIXrmP0c7H752P3ppGuhZ1q9vNJMpSfI1PWr6jbq87YBB+lxUHVrCEcv9bOqfo7CEiJi/fLYdDygoxzLYnY/fOxCSRU/hm/MyZwqDq1g6fChK6d2/nY/fOx66m7UJaS2Jbh7VawhIiAdvp6ZCkx+xfvnY/WMh39Cw8doM8WcVB1awdOcD5LQI552P3zset/ZCvtQuzsfvnY/eh+pv0VEN87H750d8MW0SzT1GEzhUHVrCDbLa9BO7LKg6tYQbf+vkPnTvxUgixfarWEG1WnuJWZLImL986SJbFkwMAU0xUHVrCEiICBDggB0DB+ParWDrRa6pM1bpM8mcKg6tYOokyTKYZ+nEWL7VakjaFw+qCu88EfCQGvvzzfVa47nARKFg/Yv1bzKqqbcVCRExfr8f72AqJSkPJgjP/MKIYVLgpPTWizNq8lo7VNCF8JnK+iaSfJDZ8HQeA8r1YofWsISIgTQ3eXmiXx0Qlo1ac+vAEx1qPlGNQPRIGKETTnAAa07mDjnY9Q6+LYd0oY1vRb/xTgBxxiGrXRTSvUwuPB66J0m6gMZ4pHTJUVhZDzOWP1OASf+xL7tf5w1/5BUZYn356KWYx0IRo+bFg99sQRhnwsup370z3SS4rizXKc5T+fvBHVgYEn/z/bMRR4SHSBQf1sQW2jJDx5MEtp825qfkvkyPu2ZNnXYDxFhajuF3/Ji0MUVldsEbosKkFWi5/RmUCP2cN8QaqONV1/25xk47mVip3NcRJt7RncrCn+d02g6zvmcUkrdDGffQiAIp5Gjy3C12e8UEqhg4hWIn5C3vS/vNvYZ4H");

                deez = utilisateurRepository.save(deez);
                System.out.println("Utilisateur deez initialisé.");
            } else {
                deez = existing.get();
                System.out.println("Utilisateur deez déjà présent.");
            }

            Utilisateur createur = new Utilisateur();
            createur.setPseudo("createur");
            createur.setEmail("createur@gmail.com");
            createur.setMdp("createur");
            createur.setStatut("createur");
            createur.setSolde(BigDecimal.ZERO);
            createur.setPhoto("UklGRoAbAABXRUJQVlA4WAoAAAAIAAAA2QEA6gEAVlA4ILgaAACwoQCdASraAesBPpFGn0ulo6KhodD5oLASCWlu/CiX4LIkcN15KimAe0AWKX87/ov+p/rPcV/Y/6//gP1t9x+ubuMYM7TP479xf239r9x/8/3y/KPUC/H/5n/w/7X+NvDHAB+sX/S/unr+e+f+D+2ep32P/33uA/rJ/1f6955Xhleg+wF/JP7f+xHvF/2n7U+nL6s9ID9f+2B6RYrsX752P3zsfvnY/fOx++dj987H70ODUTPnJ8RifpnBZGa4d40qPce94wg4P/2dB1jTfvZxUHVrCEiJi9UYIXMq+RUU3uX+XKzYaAsZXWWnb+4MiHsGOT5fJrhQqOM4VB1awhIiW1XfuSLKRhAK1hCOX/ihedB9ljwEdwnEToOrWEJETFFOmZzFkv0GTtIKXevy29T7tLbHm6sGIi1iHRfarWEJETFFPo6cwaECHv+Gmv//HcdQNOZTcvaFFelcIoS7N1R75NrXirJEbyckpyI6F6K1lyJi/fOx+9bM34SwqAE6ONDpDv8DQT2dYr1Efmn4wW4jZ4XUR4ohQRidXcSavFfrnWHdTUmiOTwQkRMX750nHIokhljXrBJpQDMjf3tEVVHuler6qNUonFm+9EfC+CkSa3eEcOSO851IQ4lrF9qtYQjsrFdeyFHhHpnxP5jh2W+Y5c1rDKWJAOpNP93L+4pwYhkTtNTN5+65y9AqIRRIXrmP0c7H752P3ppGuhZ1q9vNJMpSfI1PWr6jbq87YBB+lxUHVrCEcv9bOqfo7CEiJi/fLYdDygoxzLYnY/fOxCSRU/hm/MyZwqDq1g6fChK6d2/nY/fOx66m7UJaS2Jbh7VawhIiAdvp6ZCkx+xfvnY/WMh39Cw8doM8WcVB1awdOcD5LQI552P3zset/ZCvtQuzsfvnY/eh+pv0VEN87H750d8MW0SzT1GEzhUHVrCDbLa9BO7LKg6tYQbf+vkPnTvxUgixfarWEG1WnuJWZLImL986SJbFkwMAU0xUHVrCEiICBDggB0DB+ParWDrRa6pM1bpM8mcKg6tYOokyTKYZ+nEWL7VakjaFw+qCu88EfCQGvvzzfVa47nARKFg/Yv1bzKqqbcVCRExfr8f72AqJSkPJgjP/MKIYVLgpPTWizNq8lo7VNCF8JnK+iaSfJDZ8HQeA8r1YofWsISIgTQ3eXmiXx0Qlo1ac+vAEx1qPlGNQPRIGKETTnAAa07mDjnY9Q6+LYd0oY1vRb/xTgBxxiGrXRTSvUwuPB66J0m6gMZ4pHTJUVhZDzOWP1OASf+xL7tf5w1/5BUZYn356KWYx0IRo+bFg99sQRhnwsup370z3SS4rizXKc5T+fvBHVgYEn/z/bMRR4SHSBQf1sQW2jJDx5MEtp825qfkvkyPu2ZNnXYDxFhajuF3/Ji0MUVldsEbosKkFWi5/RmUCP2cN8QaqONV1/25xk47mVip3NcRJt7RncrCn+d02g6zvmcUkrdDGffQiAIp5Gjy3C12e8UEqhg4hWIn5C3vS/vNvYZ4H");

            createur = utilisateurRepository.save(createur);

            Utilisateur admin = new Utilisateur();
            admin.setPseudo("admin");
            admin.setEmail("admin@gmail.com");
            admin.setMdp("admin");
            admin.setStatut("admin");
            admin.setSolde(BigDecimal.ZERO);
            admin.setPhoto("UklGRoAbAABXRUJQVlA4WAoAAAAIAAAA2QEA6gEAVlA4ILgaAACwoQCdASraAesBPpFGn0ulo6KhodD5oLASCWlu/CiX4LIkcN15KimAe0AWKX87/ov+p/rPcV/Y/6//gP1t9x+ubuMYM7TP479xf239r9x/8/3y/KPUC/H/5n/w/7X+NvDHAB+sX/S/unr+e+f+D+2ep32P/33uA/rJ/1f6955Xhleg+wF/JP7f+xHvF/2n7U+nL6s9ID9f+2B6RYrsX752P3zsfvnY/fOx++dj987H70ODUTPnJ8RifpnBZGa4d40qPce94wg4P/2dB1jTfvZxUHVrCEiJi9UYIXMq+RUU3uX+XKzYaAsZXWWnb+4MiHsGOT5fJrhQqOM4VB1awhIiW1XfuSLKRhAK1hCOX/ihedB9ljwEdwnEToOrWEJETFFOmZzFkv0GTtIKXevy29T7tLbHm6sGIi1iHRfarWEJETFFPo6cwaECHv+Gmv//HcdQNOZTcvaFFelcIoS7N1R75NrXirJEbyckpyI6F6K1lyJi/fOx+9bM34SwqAE6ONDpDv8DQT2dYr1Efmn4wW4jZ4XUR4ohQRidXcSavFfrnWHdTUmiOTwQkRMX750nHIokhljXrBJpQDMjf3tEVVHuler6qNUonFm+9EfC+CkSa3eEcOSO851IQ4lrF9qtYQjsrFdeyFHhHpnxP5jh2W+Y5c1rDKWJAOpNP93L+4pwYhkTtNTN5+65y9AqIRRIXrmP0c7H752P3ppGuhZ1q9vNJMpSfI1PWr6jbq87YBB+lxUHVrCEcv9bOqfo7CEiJi/fLYdDygoxzLYnY/fOxCSRU/hm/MyZwqDq1g6fChK6d2/nY/fOx66m7UJaS2Jbh7VawhIiAdvp6ZCkx+xfvnY/WMh39Cw8doM8WcVB1awdOcD5LQI552P3zset/ZCvtQuzsfvnY/eh+pv0VEN87H750d8MW0SzT1GEzhUHVrCDbLa9BO7LKg6tYQbf+vkPnTvxUgixfarWEG1WnuJWZLImL986SJbFkwMAU0xUHVrCEiICBDggB0DB+ParWDrRa6pM1bpM8mcKg6tYOokyTKYZ+nEWL7VakjaFw+qCu88EfCQGvvzzfVa47nARKFg/Yv1bzKqqbcVCRExfr8f72AqJSkPJgjP/MKIYVLgpPTWizNq8lo7VNCF8JnK+iaSfJDZ8HQeA8r1YofWsISIgTQ3eXmiXx0Qlo1ac+vAEx1qPlGNQPRIGKETTnAAa07mDjnY9Q6+LYd0oY1vRb/xTgBxxiGrXRTSvUwuPB66J0m6gMZ4pHTJUVhZDzOWP1OASf+xL7tf5w1/5BUZYn356KWYx0IRo+bFg99sQRhnwsup370z3SS4rizXKc5T+fvBHVgYEn/z/bMRR4SHSBQf1sQW2jJDx5MEtp825qfkvkyPu2ZNnXYDxFhajuF3/Ji0MUVldsEbosKkFWi5/RmUCP2cN8QaqONV1/25xk47mVip3NcRJt7RncrCn+d02g6zvmcUkrdDGffQiAIp5Gjy3C12e8UEqhg4hWIn5C3vS/vNvYZ4H");

            admin = utilisateurRepository.save(admin);

            // Créer les applications
            Application amogus = new Application();
            amogus.setDescription("Tum tum tum tum tum tum tum tududum");
            amogus.setNom("Amogus");
            amogus.setPrix(BigDecimal.ZERO);
            amogus.setVersion("1.0");
            amogus.setNote_de_mise_a_jour("Impostor!");
            amogus.setLogo("UklGRoAbAABXRUJQVlA4WAoAAAAIAAAA2QEA6gEAVlA4ILgaAACwoQCdASraAesBPpFGn0ulo6KhodD5oLASCWlu/CiX4LIkcN15KimAe0AWKX87/ov+p/rPcV/Y/6//gP1t9x+ubuMYM7TP479xf239r9x/8/3y/KPUC/H/5n/w/7X+NvDHAB+sX/S/unr+e+f+D+2ep32P/33uA/rJ/1f6955Xhleg+wF/JP7f+xHvF/2n7U+nL6s9ID9f+2B6RYrsX752P3zsfvnY/fOx++dj987H70ODUTPnJ8RifpnBZGa4d40qPce94wg4P/2dB1jTfvZxUHVrCEiJi9UYIXMq+RUU3uX+XKzYaAsZXWWnb+4MiHsGOT5fJrhQqOM4VB1awhIiW1XfuSLKRhAK1hCOX/ihedB9ljwEdwnEToOrWEJETFFOmZzFkv0GTtIKXevy29T7tLbHm6sGIi1iHRfarWEJETFFPo6cwaECHv+Gmv//HcdQNOZTcvaFFelcIoS7N1R75NrXirJEbyckpyI6F6K1lyJi/fOx+9bM34SwqAE6ONDpDv8DQT2dYr1Efmn4wW4jZ4XUR4ohQRidXcSavFfrnWHdTUmiOTwQkRMX750nHIokhljXrBJpQDMjf3tEVVHuler6qNUonFm+9EfC+CkSa3eEcOSO851IQ4lrF9qtYQjsrFdeyFHhHpnxP5jh2W+Y5c1rDKWJAOpNP93L+4pwYhkTtNTN5+65y9AqIRRIXrmP0c7H752P3ppGuhZ1q9vNJMpSfI1PWr6jbq87YBB+lxUHVrCEcv9bOqfo7CEiJi/fLYdDygoxzLYnY/fOxCSRU/hm/MyZwqDq1g6fChK6d2/nY/fOx66m7UJaS2Jbh7VawhIiAdvp6ZCkx+xfvnY/WMh39Cw8doM8WcVB1awdOcD5LQI552P3zset/ZCvtQuzsfvnY/eh+pv0VEN87H750d8MW0SzT1GEzhUHVrCDbLa9BO7LKg6tYQbf+vkPnTvxUgixfarWEG1WnuJWZLImL986SJbFkwMAU0xUHVrCEiICBDggB0DB+ParWDrRa6pM1bpM8mcKg6tYOokyTKYZ+nEWL7VakjaFw+qCu88EfCQGvvzzfVa47nARKFg/Yv1bzKqqbcVCRExfr8f72AqJSkPJgjP/MKIYVLgpPTWizNq8lo7VNCF8JnK+iaSfJDZ8HQeA8r1YofWsISIgTQ3eXmiXx0Qlo1ac+vAEx1qPlGNQPRIGKETTnAAa07mDjnY9Q6+LYd0oY1vRb/xTgBxxiGrXRTSvUwuPB66J0m6gMZ4pHTJUVhZDzOWP1OASf+xL7tf5w1/5BUZYn356KWYx0IRo+bFg99sQRhnwsup370z3SS4rizXKc5T+fvBHVgYEn/z/bMRR4SHSBQf1sQW2jJDx5MEtp825qfkvkyPu2ZNnXYDxFhajuF3/Ji0MUVldsEbosKkFWi5/RmUCP2cN8QaqONV1/25xk47mVip3NcRJt7RncrCn+d02g6zvmcUkrdDGffQiAIp5Gjy3C12e8UEqhg4hWIn5C3vS/vNvYZ4H");
            amogus.setId_auteur(createur.getId_u());
            amogus = applicationRepository.save(amogus);

            Application ligma = new Application();
            ligma.setDescription("Ligma balls");
            ligma.setNom("Ligma");
            ligma.setPrix(BigDecimal.valueOf(9.99));
            ligma.setVersion("1.0");
            ligma.setNote_de_mise_a_jour("Squalala");
            ligma.setLogo("/9j/4AAQSkZJRgABAQEAYABgAAD/2wCEAAUDBAQEAwUEBAQFBQUGBwwIBwcHBw8LCwkMEQ8SEhEPERETFhwXExQaFRERGCEYGh0dHx8fExciJCIeJBweHx4BBQUFBwYHDggIDh4UERQeHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHv/CABEIAPoA4wMBIgACEQEDEQH/");
            ligma.setId_auteur(createur.getId_u());
            ligma = applicationRepository.save(ligma);

            // Créer des commentaires de test
            // Commentaires pour Amogus
            if (commentaireRepository.count() == 0) {
                System.out.println("Ajout de commentaires de test...");

                // Commentaire 1 pour Amogus
                Commentaire comment1 = new Commentaire();
                comment1.setContenu("Excellent jeu ! J'adore le concept et le gameplay. Les graphiques sont vraiment bien faits et l'ambiance est parfaite.");
                comment1.setNote(5);
                comment1.setId_u(deez.getId_u());
                comment1 = commentaireRepository.save(comment1);

                CommentaireAssociation assoc1 = new CommentaireAssociation();
                CommentaireAssociationId assocId1 = new CommentaireAssociationId();
                assocId1.setId_a(amogus.getId_a());
                assocId1.setId_c(comment1.getId_c());
                assoc1.setId(assocId1);
                commentaireAssociationRepository.save(assoc1);

                // Commentaire 2 pour Amogus
                Commentaire comment2 = new Commentaire();
                comment2.setContenu("Pas mal mais pourrait être amélioré. Le jeu manque un peu de contenu mais c'est un bon début.");
                comment2.setNote(3);
                comment2.setId_u(createur.getId_u());
                comment2 = commentaireRepository.save(comment2);

                CommentaireAssociation assoc2 = new CommentaireAssociation();
                CommentaireAssociationId assocId2 = new CommentaireAssociationId();
                assocId2.setId_a(amogus.getId_a());
                assocId2.setId_c(comment2.getId_c());
                assoc2.setId(assocId2);
                commentaireAssociationRepository.save(assoc2);

                // Commentaire 3 pour Amogus
                Commentaire comment3 = new Commentaire();
                comment3.setContenu("Très bon jeu addictif ! Je le recommande à tous mes amis. L'interface est intuitive et les mécaniques sont bien pensées.");
                comment3.setNote(4);
                comment3.setId_u(admin.getId_u());
                comment3 = commentaireRepository.save(comment3);

                CommentaireAssociation assoc3 = new CommentaireAssociation();
                CommentaireAssociationId assocId3 = new CommentaireAssociationId();
                assocId3.setId_a(amogus.getId_a());
                assocId3.setId_c(comment3.getId_c());
                assoc3.setId(assocId3);
                commentaireAssociationRepository.save(assoc3);

                // Commentaires pour Ligma
                // Commentaire 1 pour Ligma
                Commentaire comment4 = new Commentaire();
                comment4.setContenu("Application décevante... Ne vaut pas le prix demandé. J'attendais mieux compte tenu des descriptions.");
                comment4.setNote(2);
                comment4.setId_u(deez.getId_u());
                comment4 = commentaireRepository.save(comment4);

                CommentaireAssociation assoc4 = new CommentaireAssociation();
                CommentaireAssociationId assocId4 = new CommentaireAssociationId();
                assocId4.setId_a(ligma.getId_a());
                assocId4.setId_c(comment4.getId_c());
                assoc4.setId(assocId4);
                commentaireAssociationRepository.save(assoc4);

                // Commentaire 2 pour Ligma
                Commentaire comment5 = new Commentaire();
                comment5.setContenu("Surprenant ! Au début j'étais sceptique mais finalement c'est plutôt divertissant. Bonne surprise.");
                comment5.setNote(4);
                comment5.setId_u(admin.getId_u());
                comment5 = commentaireRepository.save(comment5);

                CommentaireAssociation assoc5 = new CommentaireAssociation();
                CommentaireAssociationId assocId5 = new CommentaireAssociationId();
                assocId5.setId_a(ligma.getId_a());
                assocId5.setId_c(comment5.getId_c());
                assoc5.setId(assocId5);
                commentaireAssociationRepository.save(assoc5);

                System.out.println("Commentaires de test ajoutés avec succès !");
            } else {
                System.out.println("Commentaires déjà présents dans la base.");
            }
        };
    }
}