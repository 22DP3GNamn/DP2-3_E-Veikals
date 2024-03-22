package rvt;

public class Person {
    
    public String name;
    public String surname;
    public String email;
    public String password;

    public Person(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
    public String getname(){
        return name;
    }
    public String getsurname(){
        return surname;
    }
    public String getemail(){
        return email;
    }
    public String getpassword(){
        return password;
    }
}
