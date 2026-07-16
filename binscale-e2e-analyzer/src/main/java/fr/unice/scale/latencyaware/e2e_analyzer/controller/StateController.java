package fr.unice.scale.latencyaware.e2e_analyzer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/state")
public class StateController {

    public StateController() {

    }
    
    @Deprecated
    @PostMapping("/finish")
    public ResponseEntity<Void> triggerExport() {
        return ResponseEntity.accepted().build();
    }
}
