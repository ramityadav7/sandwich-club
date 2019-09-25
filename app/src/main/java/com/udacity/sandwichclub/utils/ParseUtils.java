package com.udacity.sandwichclub.utils;

import java.util.List;

public class ParseUtils {

    public static String parseStringArray(List<String> text) {
        StringBuilder sb = new StringBuilder();
        if(text != null && !text.isEmpty()) {
            for(int ite = 0; ite < text.size(); ite++) {
                sb.append(text.get(ite));
                if(ite < text.size()-1)
                    sb.append("\n");
            }
        }
        return sb.toString();
    }
}
