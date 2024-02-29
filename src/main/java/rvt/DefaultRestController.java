package rvt;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class DefaultRestController {

    @RequestMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    String getAllStudents() {
        Student student = 
        new Student("Jānis",
        "Smith",
        "j@s.lv",
        "DP2-1");


        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(student);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
