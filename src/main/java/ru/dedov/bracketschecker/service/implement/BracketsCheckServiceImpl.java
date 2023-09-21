package ru.dedov.bracketschecker.service.implement;

import org.springframework.stereotype.Service;
import ru.dedov.bracketschecker.service.interfaces.BracketsCheckService;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
public class BracketsCheckServiceImpl implements BracketsCheckService {
    private final Map<Character, Character> bracketsPair = new HashMap<>() {{
        put('(', ')');
    }};

    @Override
    public boolean isBracketPlacementCorrect(String text) {
        Stack<BracketInfo> stack = new Stack<>();
        int currentPosition = 0;
        for (char currentChar : text.toCharArray()) {
            if (bracketsPair.containsKey(currentChar)) {
                stack.push(new BracketInfo(currentChar, currentPosition));
            } else if (bracketsPair.containsValue(currentChar)) {
                if (stack.isEmpty()) {
                    return false;
                }
                BracketInfo lastBracket = stack.pop();
                if (bracketsPair.get(lastBracket.bracket) != currentChar || currentPosition - lastBracket.position < 2) {
                    return false;
                }
            }
            currentPosition++;
        }
        return stack.isEmpty();
    }

    private record BracketInfo(char bracket, int position) {
    }
}
