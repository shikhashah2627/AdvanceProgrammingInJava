package edu.pdx.cs410J.shikha2;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    public static String formatWordCount(int count )
    {
        return String.format("Phone Bill on server contains %d calls", count);
    }

    public static String formatDictionaryEntry(String word, String definition )
    {
        return String.format("  %s : %s", word, definition);
    }

    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    /**
     * @param new_call_information - new call details
     * @return prints the new call information that has been added.
     */

    public static String new_call_information(String new_call_information)
    {
        return "New call information is " + new_call_information;
    }

    public static String allDictionaryEntriesDeleted() {
        return "All Phone Call entries have been deleted";
    }

    public static Map.Entry<String, String> parseDictionaryEntry(String content) {
        Pattern pattern = Pattern.compile("\\s*(.*) : (.*)");
        Matcher matcher = pattern.matcher(content);

        if (!matcher.find()) {
            return null;
        }

        return new Map.Entry<>() {
            @Override
            public String getKey() {
                return matcher.group(1);
            }

            @Override
            public String getValue() {
                String value = matcher.group(2);
                if ("null".equals(value)) {
                    value = null;
                }
                return value;
            }

            @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException("This method is not implemented yet");
            }
        };
    }

    public static void formatDictionaryEntries(PrintWriter pw, Map<String, List<String>> dictionary) {
        pw.println(Messages.formatWordCount(dictionary.size()));

        for (Map.Entry<String, List<String>> entry : dictionary.entrySet()) {
            pw.println(Messages.formatDictionaryEntry(entry.getKey(), entry.getValue().toString()));
        }
    }

    public static Map<String, String> parseDictionary(String content) {
        Map<String, String> map = new HashMap<>();

        String[] lines = content.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            Map.Entry<String, String> entry = parseDictionaryEntry(line);
            map.put(entry.getKey(), entry.getValue());
        }

        return map;
    }

}
