package fr.unice.scale.latencyaware.e2e_analyzer.controller;

import fr.unice.scale.latencyaware.e2e_analyzer.entity.ModeState;
import fr.unice.scale.latencyaware.e2e_analyzer.service.E2EAnalyzerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/state")
public class StateController {
    private final E2EAnalyzerService e2eAnalyzerService;

    public StateController(E2EAnalyzerService e2eAnalyzerService) {
        this.e2eAnalyzerService = e2eAnalyzerService;
    }


    @PostMapping("/finish")
    public ResponseEntity<Void> triggerExport() {
        try {
            e2eAnalyzerService.changeMode(ModeState.EXPORT);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
