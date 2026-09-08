package com.codelens;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    private final RestClient client = RestClient.create();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String analyzeCode(String code, String language, String mode) {

        String task;

        switch (mode.toLowerCase()) {

            case "understand":
                task = """
                        Explain the code in simple terms.

                        Identify:
                        - The overall purpose of the code
                        - Important classes
                        - Important methods or functions
                        - How the code flows

                        Focus on helping a developer understand unfamiliar code.
                        """;
                break;

            case "review":
                task = """
                        Perform ONLY a code review.

                        Do NOT give a general explanation of what the code does.

                        Identify:
                        1. Code quality issues
                        2. Bad practices
                        3. Code smells
                        4. Maintainability problems
                        5. Performance issues
                        6. Readability issues

                        For every issue, provide:
                        - Issue
                        - Location
                        - Why it is a problem
                        - Suggested improvement

                        If there are no significant issues, clearly state that.

                        Focus entirely on reviewing the code.
                        """;
                break;

            case "debug":
                task = """
                        Analyze the code ONLY for potential bugs and logical errors.

                        For each possible bug, explain:
                        - What the problem is
                        - Where it occurs
                        - Why it can cause a problem
                        - How to fix it

                        Also identify possible:
                        - Runtime errors
                        - Incorrect conditions
                        - Invalid input handling
                        - Null-related problems
                        - Boundary or index errors

                        If no obvious bugs are found, clearly say so.

                        Do not provide a general explanation of the code.
                        Focus entirely on debugging.
                        """;
                break;

            case "test":
                task = """
                        Generate useful test cases ONLY.

                        Include:
                        - Normal cases
                        - Edge cases
                        - Failure cases
                        - Invalid input cases where applicable

                        For each test case, provide:
                        - Test name
                        - Input
                        - Expected result
                        - What the test verifies

                        Do not provide a general explanation or code review.
                        Focus entirely on testing the code.
                        """;
                break;

            default:
                task = """
                        Explain the code in simple terms and identify
                        important issues or improvements.
                        """;
        }

        String prompt = """
                You are CodeLens, an AI-powered developer productivity tool.

                Programming language: %s

                Task:
                %s

                Important instructions:
                - Follow ONLY the requested task.
                - Clearly separate AI-generated recommendations from verified facts.
                - Do not claim that an issue is definitely present if you are uncertain.
                - Be concise and useful to a developer.
                - Do not perform a different type of analysis than requested.

                Code:
                %s
                """.formatted(language, task, code);

        Map<String, Object> requestBody = Map.of(
                "model", "groq/compound-mini",
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );

        try {

            String json = objectMapper.writeValueAsString(requestBody);

            String response = client.post()
                    .uri("https://api.groq.com/openai/v1/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .body(json)
                    .retrieve()
                    .body(String.class);

            Map<String, Object> responseMap =
                    objectMapper.readValue(response, Map.class);

            List<Map<String, Object>> choices =
                    (List<Map<String, Object>>) responseMap.get("choices");

            Map<String, Object> message =
                    (Map<String, Object>) choices.get(0).get("message");

            return (String) message.get("content");

        } catch (Exception e) {

            throw new RuntimeException(
                    "Groq API error: " + e.getMessage()
            );
        }
    }
}