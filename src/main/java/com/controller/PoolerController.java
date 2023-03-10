package com.controller;

import com.model.Pooler;
import com.payload.LoginPayload;
import com.payload.PoolerUpdatePayload;
import com.services.PoolerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/pooler")
public class PoolerController {

    @Autowired
    PoolerService poolerService;
    @GetMapping("/getAllPoolers")
     List<Pooler> getAllPoolers() {
        return poolerService.getAllPooler();
    }

    @PostMapping("/create")
    public Pooler createPooler(@RequestBody Pooler pooler) {
        return poolerService.createPooler(pooler);
    }

    @GetMapping("/pooler:{id}")
    public Pooler getPoolerById(@PathVariable("id") int id) {
        return poolerService.getPoolerById(id);
    }

    @PutMapping("/update")
    public Pooler updatePooler(@RequestBody Pooler pooler) {return poolerService.updatePooler(pooler);}

    @DeleteMapping("/{id}")
    public Pooler deletePoolerById(@PathVariable("id") int id) {return poolerService.deletePoolerById(id);}

    @PostMapping("/loginpooler")
    Pooler isPooler(@RequestBody LoginPayload loginPayload) {
        return poolerService.isPooler(loginPayload);
    }

    @PostMapping("/updatepooler/{id}")
    Pooler updatePooler(@RequestBody PoolerUpdatePayload poolerUpdatePayload, @PathVariable int id) {
        return poolerService.updatePooler(poolerUpdatePayload, id);
    }

}
