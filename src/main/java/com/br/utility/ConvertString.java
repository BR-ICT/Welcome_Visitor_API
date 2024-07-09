package com.br.utility;

public class ConvertString {

    public static String convertApostrophe(String text) {
	text = text.replace("'", "''");
	text = text.replace("‘", "''");
	text = text.replace("’", "''");
	text = text.replace("\"", "\"");
	text = text.replace("“", "\"");
	text = text.replace("”", "\"");
	
	return text;
    }

}
