package rvt;

public class Person {
    
    public String name;
    public String surname;
    public String email;
    public String group;
    public String age;


    public Person(String name, String surname, String email, String group) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.group = group;
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
    
    public String getgroup(){
        return group;
    }
    
    public String getage(){
        return age;
    }
    }
