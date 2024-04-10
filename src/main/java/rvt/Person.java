package rvt;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Person {

    @NotEmpty(message = "Lauks nevar būt tukšs")
    private String name;

    @NotEmpty(message = "Lauks nevar būt tukšs")
    private String surname;

    @NotEmpty(message = "Lauks nevar būt tukšs")
    private String email;

    @NotEmpty(message = "Lauks nevar būt tukšs")
    private String password;

    @NotEmpty(message = "Lauks nevar būt tukšs")
    private String confirmPassword;
    
    public Person(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
