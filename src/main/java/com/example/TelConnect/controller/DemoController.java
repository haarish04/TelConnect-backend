package com.example.TelConnect.controller;

import com.example.TelConnect.model.Demo;
import com.example.TelConnect.service.DemoService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private final DemoService demoService;

    @Autowired
    public DemoController( DemoService demoService) {
        this.demoService= demoService;
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Demo>> demo(){
        List<Demo> demo = demoService.getAll();
        return ResponseEntity.ok(demo);
    }

    @PostMapping("/{usn}")
    public ResponseEntity<Demo> getDemo(@PathVariable("usn") String usn){
        Demo demo = demoService.getByUsn(usn);
        return ResponseEntity.ok(demo);
    }
}
