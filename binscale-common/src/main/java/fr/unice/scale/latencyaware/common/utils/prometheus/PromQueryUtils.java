package fr.unice.scale.latencyaware.common.utils.prometheus;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PromQueryUtils {
    private final static Pattern namedFormatPattern = Pattern.compile("#\\{(?<key>.*?)}");

    private final static Logger logger = LoggerFactory.getLogger(PromQueryUtils.class);

    public static String namedFormat(final String format, Map<String, ? extends Object> kvs) {
        final StringBuilder buffer = new StringBuilder();
        final Matcher match = namedFormatPattern.matcher(format);
        while (match.find()) {
            final String key = match.group("key");
            final Object value = kvs.get(key);
            if (value != null)
                match.appendReplacement(buffer, value.toString());
            else if (kvs.containsKey(key))
                match.appendReplacement(buffer, "null");
            else
                match.appendReplacement(buffer, "");
        }
        match.appendTail(buffer);
        return buffer.toString();
    }
}
