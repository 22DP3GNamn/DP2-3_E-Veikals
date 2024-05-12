package rvt;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Person {
    
    public Person(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    
    @Pattern(regexp = "^[A-ZĀĒĪŌŪČĢĶĻŅŠŽ][a-zāēīōūčģķļņšž]+$", message = "Vārds jāsākas ar lielo burtu un satur tikai burtus")
    private String name;
    
    @Pattern(regexp = "^[A-ZĀĒĪŌŪČĢĶĻŅŠŽ][a-zāēīōūčģķļņšž]+$", message = "Uzvārds jāsākas ar lielo burtu un satur tikai burtus")
    private String surname;
    
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Nederīgs e-pasta formāts")
    private String email;
    
    @Pattern(regexp = "^.{5,20}$", message = "Parolei jābūt garumā no 5 līdz 20 simboliem")
    private String password;
    
    private String confirmPassword;
    

}
