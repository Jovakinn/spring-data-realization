package com.spark.jovakinn.unsafe_starter.utils;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class WordsMatcher {

    public static final String REGEX_FOR_GETTING_WORDS_BY_JAVA_CONVENTION = "(?=\\p{Upper})";

    public static String findAndRemoveMatchingPiecesIfExists(Set<String> options, List<String> pieces) {
        StringBuilder match = new StringBuilder(pieces.remove(0));
        List<String> remainingOptions = new ArrayList<>(options.stream()
                .filter(option -> option.toLowerCase().startsWith(match.toString().toLowerCase())).toList());
        if (remainingOptions.isEmpty()) {
            return "";
        }
        while (remainingOptions.size() > 1) {
            match.append(pieces.remove(0));
            remainingOptions.removeIf(option -> !option.toLowerCase().startsWith(match.toString()));
        }
        while (!remainingOptions.get(0).equalsIgnoreCase(match.toString())) {
            match.append(pieces.remove(0));
        }
        return Introspector.decapitalize(match.toString());
    }

    public static List<String> toWordsByJavaConvention(String naming) {
        return List.of(naming.split(REGEX_FOR_GETTING_WORDS_BY_JAVA_CONVENTION));
    }
}
