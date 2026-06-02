package service;

import java.text.Normalizer;
import java.util.Locale;

public class StringNormalizer {

    public static String removeDiacritics(String input) {
        if (input == null) {
            return null;
        }
        // Normalize to decomposed form (NFD), then remove diacritical marks
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase(Locale.forLanguageTag("vi"));
    }

    public static boolean fuzzyMatch(String source, String target) {
        String normalizedSource = removeDiacritics(source);
        String normalizedTarget = removeDiacritics(target);
        return normalizedSource.contains(normalizedTarget);
    }
}