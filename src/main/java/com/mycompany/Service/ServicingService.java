package com.mycompany.Service;

import com.mycompany.Model.Part;
import com.mycompany.Model.wPart;
import com.mycompany.Model.wService;
import com.mycompany.Repository.ServiceRepository;
import com.mycompany.Repository.wServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mycompany.Model.Servicing;
import java.util.List;
import java.util.Optional;

@Service
public class ServicingService {
    @Autowired private ServiceRepository repo1;
    @Autowired private wServiceRepository repo2;
    public List<Servicing> allServices(){
        return (List<Servicing>) repo1.findAll();
    }
    public void save(wService service){
        repo2.save(service);
    }
    public Servicing findService(Integer id){
        Optional<Servicing> result=repo1.findById(id);
        return result.get();
    }
    public List<wService> findByWorkshop(Integer id){
        List<wService> result=repo2.findByService_id(id);
        return result;
    }
}
