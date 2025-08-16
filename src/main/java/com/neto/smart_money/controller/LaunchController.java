package com.neto.smart_money.controller;

import com.neto.smart_money.dto.LaunchRequestDTO;
import com.neto.smart_money.dto.LaunchResponseDTO;
import com.neto.smart_money.dto.UpdateLaunchDTO;
import com.neto.smart_money.services.LaunchService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/launch")
@AllArgsConstructor
public class LaunchController {

    @Autowired
    private LaunchService service;

    @PostMapping("/create")
    public ResponseEntity<LaunchResponseDTO> createLaunch(@RequestBody LaunchRequestDTO body){
        return ResponseEntity.ok().body(service.createLaunch(body));
    }

    @GetMapping
    public ResponseEntity<List<LaunchResponseDTO>> getAllByClient(){
        return ResponseEntity.ok(service.getAllByClient());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaunchResponseDTO> editLaunch(@PathVariable UUID id, @RequestBody UpdateLaunchDTO body){
        return ResponseEntity.ok(service.editLauncherById(id, body));
    }
}
