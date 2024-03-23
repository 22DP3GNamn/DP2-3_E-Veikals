package rvt;

import jakarta.validation.constraints.NotEmpty;
// import jakarta.validation.constraints.Pattern;
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
    @NotEmpty(message = "Vārds nevar būt tukšs")
    private String name;

    @NotEmpty(message = "Vārds nevar būt tukšs")
    private String surname;

    @NotEmpty(message = "E-pasts nedrīkst būt tukšs")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "E-pastam jābūt derīgam!")
    private String email;

    @NotEmpty(message = "Vārds nevar būt tukšs")
    private String password;

    @NotEmpty(message = "Vārds nevar būt tukšs")
    private String confirmPassword;
    
    public String toString() {
        return name + " " + surname + " " + email + " " + password + " " + confirmPassword;
    }
}
