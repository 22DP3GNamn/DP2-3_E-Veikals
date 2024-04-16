package rvt;

public class Validator {
    
    final private static String namePattern = "^[A-Z][a-z]+$";
    final private static String surnamePattern = "^[A-Z][a-z]+$";
    final private static String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    final private static String passwordPattern = "^.{5,20}$";

    public static boolean validateName(String name) {
        return name.matches(namePattern);
    }

    public static boolean validateSurname(String surname) {
        return surname.matches(surnamePattern);
    }

    public static boolean validateEmail(String email) {
        return email.matches(emailPattern);
    }

    public static boolean validatePassword(String password) {
        return password.matches(passwordPattern);
    }
}
