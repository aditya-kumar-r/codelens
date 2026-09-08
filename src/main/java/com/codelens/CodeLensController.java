package com.codelens;

import org.springframework.web.bind.annotation.*;

@RestController
public class CodeLensController {

    private final GroqService groqService;

    public CodeLensController(GroqService groqService) {
        this.groqService = groqService;
    }

    @PostMapping("/api/analyze")
    public String analyze(@RequestBody AnalyzeRequest request) {

        return groqService.analyzeCode(
                request.getCode(),
                request.getLanguage(),
                request.getMode()
        );
    }
}