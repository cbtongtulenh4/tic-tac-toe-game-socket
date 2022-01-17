package javakit.minhphuc.util;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    // This Regular Expression for Validation of Non-Latin or Unicode Characters - all languages
    // OR use RFC 5322 ( doesn't allow the pipe character(|) and single quote(') as these present
    // a potential SQL injection risk when passed from the client site to the server. )
    // Read More: https://www.baeldung.com/java-email-validation-regex
    private static final String regexEmail = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
    private static final String regexName = "^[a-zA-Z0-9](([._-](?![._-])|[a-zA-Z0-9]){3,18})[a-zA-Z0-9]$";
    private static String regexPassword = null;

    //check Email
    public static boolean validateEmail(String email){
        //Way 1: use matches() method of String
        //email.matches(regex);

        //Way 2: use Pattern -> Matcher
        return validateExecute(email, regexEmail);
    }

    //check Password
    public static boolean validatePassword(String password){
        regexPassword = buildValidatorPassword(
                false,
                false,
                false,
                false,
                6,
                30);
        return validateExecute(password, regexPassword);
    }


    private static boolean validateExecute(String value,String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    // build regex Password
    public static String buildValidatorPassword(
            boolean forceChar,
            boolean forceSpecialChar,
            boolean forceCapitalLetter,
            boolean forceNumber,
            int minLength,
            int maxLength)
    {
        StringBuilder str = new StringBuilder("(");
        if(forceChar){
            str.append("(?=.*[a-z])");
        }
        if (forceSpecialChar){
            str.append("(?=.*[@#$%])");
        }
        if (forceNumber){
            str.append("(?=.*d)");
        }
        if (forceCapitalLetter){
           str.append("(?=.*[A-Z])");
        }
        //limit length of password
        str.append(".{" + minLength + "," + maxLength + "})");
        return str.toString();
    }

    public static boolean validateName(String name){
        return validateExecute(name, regexName);
    }

    public static boolean validateYear(int year){
        return ( year > 0 && year < 2020);
    }

    // remove Vietnamese accents
    public static String removeAccent(String s) {
        //or Normalizer.Form.NFKD for a more "compatible" deconstruction
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
        return pattern.matcher(temp).replaceAll("");

    }


    public static void validateIntegerForm(JTextField input){
        Runnable doAssist = new Runnable() {
            @Override
            public void run() {
                String value = input.getText();

                // do something

                input.setText(value);

            }
        };
        SwingUtilities.invokeLater(doAssist);
    }



}
