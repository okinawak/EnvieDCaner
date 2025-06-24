package com.projet.fip1.microsoft_store_back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projet.fip1.microsoft_store_back.entities.Label;
import com.projet.fip1.microsoft_store_back.repository.LabelRepository;
import com.projet.fip1.microsoft_store_back.repository.LabelisationRepository;

@RestController
@RequestMapping("/api/labelisation")
@CrossOrigin(origins = "http://localhost:5173") // <- autorise React à appeler cette API
public class LabelisationController {

    @Autowired
    LabelRepository labelrepo;

    @GetMapping("/getAllLabel")
    @CrossOrigin(origins = "http://localhost:5173")
    public List<Label> getAll(){
        return labelrepo.findAll();
    }
}
