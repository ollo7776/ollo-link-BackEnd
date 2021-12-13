package com.usterka.restapi.controller;

import com.usterka.restapi.model.*;
import com.usterka.restapi.utils.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

import java.lang.reflect.Executable;
import java.sql.SQLException;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
public class RepairController {

    private Db db;
    private DbDiensts dbDienst;

    public RepairController() throws Exception {
        db = new Db(Configuration.getDbFileName());
        dbDienst = new DbDiensts(Configuration.getDbFileName());
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/repair")
    public List<Repair> getRepairs(
            @RequestParam(defaultValue = "registrationNo") String sortBy,
            @RequestParam(defaultValue = "ascending") String sortDirection,
            @RequestParam(defaultValue = "all") String statusNo,
            @RequestParam(defaultValue = "all") String registrationNo
    ) throws Exception {
        return db.getRepairs(sortBy, sortDirection.equals("ascending"), statusNo, registrationNo);
    }

    @GetMapping("/repair/{id}")
    public Repair getRepair(@PathVariable Long id) throws Exception {
        Repair repair = db.getRepair(id); // +One

        if (repair != null) {
            return repair;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/remove/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public ResponseEntity delete(@PathVariable Long id) throws Exception {
        //String response = db.removeRepair(id);
        if (db.removeRepair(id)) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

//    @RequestMapping(value = "/remove/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
//    public String delete(@PathVariable Long id) throws Exception {
//       //String response = db.removeRepair(id);
//        db.removeRepair(id);
//        return "Well, well removed ";
//    }

//    @RequestMapping(value="/remove/{id}", method={DELETE, RequestMethod.GET})
//    public @ResponseBody String removeRepair(
//            @PathVariable long id) throws SQLException {
//        db.removeRepair(id);
//        //db.removeRepair2(id);
//        return "Maybe you've had something deleted with No." + id;
//    }

    @PostMapping(value = "/repair", consumes = "application/json")


    public void insertRepair(@RequestBody Repair repair) throws Exception {
        db.insert(repair);
    }

    @PostMapping(value = "/dienst", consumes = "application/json")
    public void insertDienst(@RequestBody Dienst dienst) throws SQLException {
        dbDienst.insert(dienst);
    }

    @GetMapping("/diensts")
    public List<Dienst> getDiensts(
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "ascending") String sortDirection
    ) throws SQLException {
        return dbDienst.getDiensts(sortBy, sortDirection.equals("ascending"));
    }

}

