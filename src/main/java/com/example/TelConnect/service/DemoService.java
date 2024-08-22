package com.example.TelConnect.service;

import com.example.TelConnect.model.Demo;
import com.example.TelConnect.repository.DemoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DemoService {
    private final DemoRepository DemoRepo;
    public DemoService(DemoRepository demoRepo){
        this.DemoRepo=demoRepo;
    }
    public List<Demo> getAll()
    {
        return DemoRepo.findAll();
    }
    public Demo getByUsn(String usn){
        return DemoRepo.findById(usn).orElse(null);
    }
    public Demo create(Demo d1)
    {
        return DemoRepo.save(d1);
    }
    public void delete(String usn){
        DemoRepo.deleteById(usn);
    }
    public Demo update(Demo d1,String usn)
    {
        Demo demoObj=DemoRepo.findById(usn).get();
        demoObj.setName(d1.getName());
        demoObj.setSem(d1.getSem());
        demoObj.setSection(d1.getSection());
        return DemoRepo.save(demoObj);
    }
    public void deleteAll() {
        DemoRepo.deleteAll();
    }
}
