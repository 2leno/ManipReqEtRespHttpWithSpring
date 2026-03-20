package school.hei.manipreqetresphttpwithspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "L'application fonctionne !";
    }

    @GetMapping("/api/status")
    public Map<String, Object> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Serveur démarré avec succès");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }
}
