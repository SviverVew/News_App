package com.example.newsapp;

import java.util.Base64;

public class UtilsEncode {
        //Mã hóa từ email sang dãy kí tự
        public static   String encodeEmailToNumber(String email) {
            byte[] encodedBytes = Base64.getEncoder().encode(email.getBytes());
            return new String(encodedBytes);
        }
       // mã hóa ngược lại từ dãy kí tự sang email
        public static String decodeNumberToEmail(String encodedNumber) {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedNumber.getBytes());
            return new String(decodedBytes);
        }
}

