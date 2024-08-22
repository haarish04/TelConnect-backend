package com.example.TelConnect.controller;

import com.example.TelConnect.model.Demo;
import com.example.TelConnect.service.DemoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DemoController {
    private final DemoService DemoService;
    DemoController(DemoService cs)
    {
        this.DemoService=cs;
    }
    @GetMapping("/")
    public List<Demo> getAll()
    {
        return DemoService.getAll();
    }
    @GetMapping("/{usn}")
    public Demo getByUsn(@PathVariable String usn){
        return DemoService.getByUsn(usn);
    }
    @PostMapping("/create")
    public Demo create(@RequestBody Demo d1)
    {
        return DemoService.create(d1);
    }
    @DeleteMapping("/delete/{usn}")
    public String delete(@PathVariable String usn){
        DemoService.delete(usn);
        return usn+" deleted";
    }
    @PostMapping("/update/{usn}")
    public Demo update(@RequestBody Demo d1,@PathVariable String usn)
    {
        return DemoService.update(d1,usn);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        DemoService.deleteAll();
        return "All Records deleted";

    }
}
