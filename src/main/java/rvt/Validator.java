package rvt;

public class Validator {
    
    final private static String namePattern = "^[A-Z][a-z]+$";
    final private static String surnamePattern = "^[A-Z][a-z]+$";
    final private static String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    final private static String passwordPattern = "^[a-zA-Z0-9!@#$%&*]{5,20}$";
    final private static String addressPattern = "^[a-zA-Z0-9\\s,-.']+$";
    final private static String expirityDatePattern = "^\\d{2}/\\d{2}$";
    final private static String cardPattern = "^\\d{16}$";
    final private static String cvvPattern = "^\\d{3}$";
    
    
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

    public static boolean validateAddress(String address) {
        return address.matches(addressPattern);
    }

    public static boolean validateCard(String card) {
        return card.matches(cardPattern);
    }

    public static boolean validateExpirityDate(String expiryDate) {
        return expiryDate.matches(expirityDatePattern);
    }

    public static boolean validateCvv(String cvv) {
        return cvv.matches(cvvPattern);
    }

}