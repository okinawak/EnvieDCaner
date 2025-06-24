package com.projet.fip1.microsoft_store_back;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("/test")
    public String helloWord(){
        return "hello word ! ";
    }
  
}
