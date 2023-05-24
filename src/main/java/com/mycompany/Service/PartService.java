package com.mycompany.Service;

import com.mycompany.Model.Part;
import com.mycompany.Model.wPart;
import com.mycompany.Repository.PartRepository;
import com.mycompany.Repository.wPartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartService {
    @Autowired private PartRepository repo1;
    @Autowired private wPartRepository repo2;

    public List<Part> allParts(){
        return (List<Part>) repo1.findAll();
    }
    public void save(wPart part){
        repo2.save(part);
    }
    public Part findPart(Integer id){
          Optional<Part> result=repo1.findById(id);
          return result.get();
    }

    public Part findByName(String name){
        return repo1.findByName(name);
    }
    public List<wPart> findwPart(Integer id){
        List<wPart> result=repo2.findByPart_id(id);
        return result;
    }
    public List<wPart> findByWorkshop(Integer id){
        List<wPart> result=repo2.findByPart_id(id);
        return result;
    }

    public void deleteById(Integer partId) {
        repo2.deleteById(partId);
    }
}
