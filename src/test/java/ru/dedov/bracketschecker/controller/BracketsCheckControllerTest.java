package ru.dedov.bracketschecker.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.dedov.bracketschecker.model.BracketsCheckRequest;
import ru.dedov.bracketschecker.model.BracketsCheckResponse;
import ru.dedov.bracketschecker.service.interfaces.BracketsCheckService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BracketsCheckControllerTest {

    @InjectMocks
    private BracketsCheckController bracketsCheckController;

    @Mock
    private BracketsCheckService bracketsCheckService;

    @Test
    public void testCheckBrackets_ValidText() {
        String validText = "This is (a valid) text with (brackets).";
        BracketsCheckRequest request = new BracketsCheckRequest(validText);
        when(bracketsCheckService.isBracketPlacementCorrect(validText)).thenReturn(true);

        ResponseEntity<BracketsCheckResponse> response = bracketsCheckController.checkBrackets(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isCorrect());
    }

    @Test
    public void testCheckBrackets_InvalidText() {
        String invalidText = "This is an (invalid) text with missing ) bracket.";
        BracketsCheckRequest request = new BracketsCheckRequest(invalidText);
        when(bracketsCheckService.isBracketPlacementCorrect(invalidText)).thenReturn(false);

        ResponseEntity<BracketsCheckResponse> response = bracketsCheckController.checkBrackets(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isCorrect());
    }

    @Test
    public void testCheckBrackets_EmptyText() {
        String emptyText = "";
        BracketsCheckRequest request = new BracketsCheckRequest(emptyText);

        ResponseEntity<BracketsCheckResponse> response = bracketsCheckController.checkBrackets(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isCorrect());
    }

    @Test
    public void testCheckBrackets_NullText() {
        BracketsCheckRequest request = new BracketsCheckRequest(null);

        ResponseEntity<BracketsCheckResponse> response = bracketsCheckController.checkBrackets(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isCorrect());
    }
}
