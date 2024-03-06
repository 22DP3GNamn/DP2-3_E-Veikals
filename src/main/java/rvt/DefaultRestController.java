package rvt;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class DefaultRestController {

    @RequestMapping(value = "/Person", produces = MediaType.APPLICATION_JSON_VALUE)
    String getAllPersons() {
        Person person = 
        new Person("Jānis",
        "Smith",
        "j@s.lv",
        "DP2-1");


        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(person);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
