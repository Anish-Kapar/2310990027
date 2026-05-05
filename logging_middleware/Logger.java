import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Logger {

    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJNYXBDbGFpbXMiOnsiYXVkIjoiaHR0cDovLzIwLjI0NC41Ni4xNDQvZXZhbHVhdGlvbi1zZXJ2aWNlIiwiZW1haWwiOiJhbmlzaDAwMjcuYmUyM0BjaGl0a2FyYS5lZHUuaW4iLCJleHAiOjE3Nzc5NjEwODMsImlhdCI6MTc3Nzk2MDE4MywiaXNzIjoiQWZmb3JkIE1lZGljYWwgVGVjaG5vbG9naWVzIFByaXZhdGUgTGltaXRlZCIsImp0aSI6ImYzYWI2MTMzLWFjZjQtNDhjYy04MDI2LTg0Zjg0MDc3OTI3NCIsImxvY2FsZSI6ImVuLUlOIiwibmFtZSI6ImFuaXNoIGthcGFyIiwic3ViIjoiMmRhZGUwYTktNTAwZC00YzRkLWIwMzAtNGQ0YWIyY2EyYWZmIn0sImVtYWlsIjoiYW5pc2gwMDI3LmJlMjNAY2hpdGthcmEuZWR1LmluIiwibmFtZSI6ImFuaXNoIGthcGFyIiwicm9sbE5vIjoiMjMxMDk5MDAyNyIsImFjY2Vzc0NvZGUiOiJFWGZ2RHAiLCJjbGllbnRJRCI6IjJkYWRlMGE5LTUwMGQtNGM0ZC1iMDMwLTRkNGFiMmNhMmFmZiIsImNsaWVudFNlY3JldCI6IlVWdEJrdXhDeHlZWFRmQ0QifQ.aZzdAqADAVw1Tz6OjGrn0zIlXgCDIn2o_0nbWMQEhxI";
    private static final String LOG_URL = "http://20.207.122.201/evaluation-service/logs";

    private static final HttpClient client = HttpClient.newHttpClient();

    public static void Log(String stack, String level, String pkg, String message) {
        try {
            String body = String.format(
                    "{\"stack\":\"%s\",\"level\":\"%s\",\"package\":\"%s\",\"message\":\"%s\"}",
                    stack, level, pkg, message
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(LOG_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + TOKEN)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("[LOG] " + level + " | " + pkg + " | " + message
                    + " -> " + response.statusCode() + " " + response.body());

        } catch (Exception e) {
            System.err.println("[LOG FAILED] " + e.getMessage());
        }
    }

    // Quick test - run this to verify it works
    public static void main(String[] args) {
        Log("backend", "info", "service", "Logger initialized successfully");
        Log("backend", "debug", "handler", "Test log from logging middleware");
    }
}