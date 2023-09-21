package ru.dedov.bracketschecker.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dedov.bracketschecker.service.implement.BracketsCheckServiceImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BracketsCheckServiceTest {

    @InjectMocks
    private BracketsCheckServiceImpl bracketsCheckService;

    @Test
    public void testBracketPlacementCorrect_ValidText() {
        String validText = "This is (a valid) text with (brackets).";

        boolean result = bracketsCheckService.isBracketPlacementCorrect(validText);

        assertTrue(result);
    }

    @Test
    public void testBracketPlacementCorrect_InvalidText() {
        String invalidText = "This is an (invalid) text with closed () brackets without text.";

        boolean result = bracketsCheckService.isBracketPlacementCorrect(invalidText);

        assertFalse(result);
    }

    @Test
    public void testBracketPlacementCorrect_EmptyText() {
        String emptyText = "";

        boolean result = bracketsCheckService.isBracketPlacementCorrect(emptyText);

        assertTrue(result);
    }

    @Test
    public void testBracketPlacementCorrect_OnlyOpenBrackets() {
        String text = "This is (an invalid text with only open brackets (.";

        boolean result = bracketsCheckService.isBracketPlacementCorrect(text);

        assertFalse(result);
    }

    @Test
    public void testBracketPlacementCorrect_OnlyCloseBrackets() {
        String text = "This is )an invalid text with only close brackets ).";

        boolean result = bracketsCheckService.isBracketPlacementCorrect(text);

        assertFalse(result);
    }

    @Test
    public void testBracketPlacementCorrect_ValidTextWithNestedBrackets() {
        String validText = "This is (a (nested) valid) text with (brackets).";

        boolean result = bracketsCheckService.isBracketPlacementCorrect(validText);

        assertTrue(result);
    }
}
