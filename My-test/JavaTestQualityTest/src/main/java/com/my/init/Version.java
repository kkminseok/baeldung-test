package com.my.init;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Version(int major, int minor) {
    private static final Pattern REGEX = Pattern.compile("(\\d+\\.(\\d+))");

    static Version parse(String version) {
        Matcher matcher = REGEX.matcher(version);
        if(!matcher.matches()) {
            throw new IllegalArgumentException();
        }

        int major = Integer.parseInt(matcher.group(1));
        int minor = Integer.parseInt(matcher.group(2));

        return new Version(major, minor);
    }
}
