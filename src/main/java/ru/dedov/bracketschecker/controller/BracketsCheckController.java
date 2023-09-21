package ru.dedov.bracketschecker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dedov.bracketschecker.model.BracketsCheckRequest;
import ru.dedov.bracketschecker.model.BracketsCheckResponse;
import ru.dedov.bracketschecker.service.interfaces.BracketsCheckService;

@RestController
@RequestMapping("/api")
public class BracketsCheckController {
    private final BracketsCheckService bracketsCheckService;

    @Autowired
    public BracketsCheckController(BracketsCheckService bracketsCheckService) {
        this.bracketsCheckService = bracketsCheckService;
    }

    @Operation(summary = "Проверка корректного расположения скобок в тексте")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Проверка осуществлена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BracketsCheckResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Проверка не состоялась, текст пуст либо null",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"correct\": false }"))})})
    @PostMapping("/checkBrackets")
    public ResponseEntity<BracketsCheckResponse> checkBrackets(
            @Parameter(description = "Текст для проверки", required = true)
            @RequestBody BracketsCheckRequest request) {
        String text = request.getText();

        if (text == null || text.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new BracketsCheckResponse(false));
        }

        boolean isCorrect = bracketsCheckService.isBracketPlacementCorrect(text);
        return ResponseEntity.ok(new BracketsCheckResponse(isCorrect));
    }
}
