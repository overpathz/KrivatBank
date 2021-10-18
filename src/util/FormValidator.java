package util;

public final class FormValidator {

    public static boolean checkRegForm(String username, String firstPswd, String secondPswd) {
        return checkPasswords(firstPswd, secondPswd) && checkUsername(username);
    }

    public static boolean checkLogForm(String username, String password) {
        return checkUsername(username) && checkPassword(password);
    }

    public static boolean checkPasswords(String firstPswd, String secondPswd) {

        if (firstPswd.equals(secondPswd)) {
            if (firstPswd.length() > 5 && secondPswd.length() > 5) {
                return true;
            }
        }

        return false;
    }

    public static boolean checkPassword(String password) {
        return ((!password.isBlank()) && (password.length() > 5));
    }

    public static boolean checkUsername(String username) {
        return username.length() > 5 && !username.isBlank();
    }
}
