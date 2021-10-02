package com.company;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Constants {

    static final String promptReady = "\r\n\r\nregMatch> ";
    static final String cmd = "cmd";
    static final String arg1 = "arg1";
    static final String arg2 = "arg2";
    static final String fallbackMessage = "Invalid command. Did you forget single-quotes?";

    enum _2Args {
        group("Scans and captures first group occurrence.","'[regex]' group '[value-string]'"),
        matches("Tests if string matches regex pattern.","'[regex]' matches '[value-string]'"),
        contains("Tests if string contains substring","'[main-string]' contains '[substring]'"),
        indexOf("Returns index of substring if present, or -1 if absent.","'[main-string]' indexOf '[substring]'");

        public final String synopsis;
        public final String description;
        _2Args(String description, String synopsis){
            this.description = description;
            this.synopsis = synopsis;
        }
    }

    public static final Pattern _2arg =
            Pattern.compile("'(?<"+arg1+">.+)' +(?<"+cmd+">("+
                    Arrays.stream(_2Args.values()).map(Enum::name).reduce((s, s2) -> s + "|" + s2).orElse("")
                    +")) +'(?<"+arg2+">.+)'");

    enum _1Args {
        grep("Prints lines from regex-javadoc that contains specified substring.", "grep '[substring]'");

        public final String synopsis;
        public final String description;
        _1Args(String description, String synopsis){
            this.description = description;
            this.synopsis = synopsis;
        }
    }

    public static final Pattern _1arg =
            Pattern.compile("(?<"+cmd+">("+
                    Arrays.stream(_1Args.values()).map(Enum::name).reduce((s, s2) -> s + "|" + s2).orElse("")
                    +")) +'(?<"+arg1+">.+)'");

    enum _0Args {
        doc("Prints regex-compiler javadoc. Refer to this for syntax guidance."),
        cmd("Prints all available commands."),
        releases("Prints release notes with security-patches/feature updates."),
        help("Prints welcoming message."),
        quit("Closes this session.");

        public final String description;
        _0Args(String description){
            this.description = description;
        }
    }

    public static final Pattern _0arg =
            Pattern.compile("(?<"+cmd+">("+
                    Arrays.stream(_0Args.values()).map(Enum::name).reduce((s, s2) -> s + "|" + s2).orElse("")
                    +"))");

    public static final String welcomeMessage = "Welcome to RegMatch 1.0.1 (openjdk16 build, Java 16)\r\n" +
            "Summary of commands:\r\n" +
            Arrays.stream(_0Args.values())
                    .map(args -> args.name() + " - " + args.description)
                    .reduce((s1, s2) -> s1 + "\r\n" + s2)
                    .orElse("");

    public static final String releaseNote =
            "1.0.0 - Official release.\r\n" +
            "1.0.1 - Security patch. Regexes and non-regexes limited to 50 and 1000 characters respectively " +
                    "to restrict resource-usage.";

    public static final String commands =
            "PLEASE NOTE: All regexes or strings in general require single quote delimiters to be valid!\r\n" +
                    "This applies to literals only, and not commands/operators. Example:\r\n\r\n" +
                    "Valid: '(a*)' group 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbb'\r\n" +
                    "Invalid: (a*) group aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbb\r\n\r\n" +
                    Arrays.stream(_0Args.values())
                            .map(args -> args.name() + "  #  " + args.description)
                            .reduce((s, s2) -> s + "\r\n" + s2)
                            .orElse("") + "\r\n" +
                    Arrays.stream(_1Args.values())
                            .map(args -> args.name() + "  #  " + args.description + "  #  " + args.synopsis)
                            .reduce((s, s2) -> s + "\r\n" + s2)
                            .orElse("") + "\r\n" +
                    Arrays.stream(_2Args.values())
                            .map(args -> args.name() + "  #  " + args.description + "  #  " + args.synopsis)
                            .reduce((s, s2) -> s + "\r\n" + s2)
                            .orElse("");


    public static final String manual =
            "Summary of regular-expression constructs from the official Javadoc as of Sep 17. 2021:\n" +
            "Complete version available here: https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html\n" +
            "\n" +
            "Construct:      Matches:\n" +
            "\n" +
            "Characters\n" +
            "    x            The character x\n" +
            "    \\\\         The backslash character\n" +
            "    \\0n         The character with octal value 0n (0 <= n <= 7)\n" +
            "    \\0nn        The character with octal value 0nn (0 <= n <= 7)\n" +
            "    \\0mnn       The character with octal value 0mnn (0 <= m <= 3, 0 <= n <= 7)\n" +
            "    \\xhh        The character with hexadecimal value 0xhh\n" +
            "    \\uhhhh      The character with hexadecimal value 0xhhhh\n" +
            "    \\x{h...h}   The character with hexadecimal value 0xh...h (Character.MIN_CODE_POINT  <= 0xh...h <=  Character.MAX_CODE_POINT)\n" +
            "\n" +
            "    \\t          The tab character ('\\u0009')\n" +
            "    \\n          The newline (line feed) character ('\\u000A')\n" +
            "    \\r          The carriage-return character ('\\u000D')\n" +
            "    \\f          The form-feed character ('\\u000C')\n" +
            "    \\a          The alert (bell) character ('\\u0007')\n" +
            "    \\e          The escape character ('\\u001B')\n" +
            "    \\cx         The control character corresponding to x\n" +
            "\n" +
            "Character classes\n" +
            "\n" +
            "    [abc]           a, b, or c (simple class)\n" +
            "    [^abc]          Any character except a, b, or c (negation)\n" +
            "    [a-zA-Z]        a through z or A through Z, inclusive (range)\n" +
            "    [a-d[m-p]]      a through d, or m through p: [a-dm-p] (union)\n" +
            "    [a-z&&[def]]    d, e, or f (intersection)\n" +
            "    [a-z&&[^bc]]    a through z, except for b and c: [ad-z] (subtraction)\n" +
            "    [a-z&&[^m-p]]   a through z, and not m through p: [a-lq-z](subtraction)\n" +
            "\n" +
            "Predefined character classes\n" +
            "\n" +
            "    .               Any character (may or may not match line terminators)\n" +
            "    \\d              A digit: [0-9]\n" +
            "    \\D              A non-digit: [^0-9]\n" +
            "    \\h              A horizontal whitespace character: [ \\t\\xA0\\u1680\\u180e\\u2000-\\u200a\\u202f\\u205f\\u3000]\n" +
            "    \\H              A non-horizontal whitespace character: [^\\h]\n" +
            "    \\s              A whitespace character: [ \\t\\n\\x0B\\f\\r]\n" +
            "    \\S              A non-whitespace character: [^\\s]\n" +
            "    \\v              A vertical whitespace character: [\\n\\x0B\\f\\r\\x85\\u2028\\u2029]\n" +
            "    \\V              A non-vertical whitespace character: [^\\v]\n" +
            "    \\w              A word character: [a-zA-Z_0-9]\n" +
            "    \\W              A non-word character: [^\\w]\n" +
            "\n" +
            "POSIX character classes (US-ASCII only)\n" +
            "\n" +
            "    \\p{Lower}       A lower-case alphabetic character: [a-z]\n" +
            "    \\p{Upper}       An upper-case alphabetic character:[A-Z]\n" +
            "    \\p{ASCII}       All ASCII:[\\x00-\\x7F]\n" +
            "    \\p{Alpha}       An alphabetic character:[\\p{Lower}\\p{Upper}]\n" +
            "    \\p{Digit}       A decimal digit: [0-9]\n" +
            "    \\p{Alnum}       An alphanumeric character:[\\p{Alpha}\\p{Digit}]\n" +
            "    \\p{Punct}       Punctuation: One of !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\n" +
            "\n" +
            "    \\p{Graph}       A visible character: [\\p{Alnum}\\p{Punct}]\n" +
            "    \\p{Print}       A printable character: [\\p{Graph}\\x20]\n" +
            "    \\p{Blank}       A space or a tab: [ \\t]\n" +
            "    \\p{Cntrl}       A control character: [\\x00-\\x1F\\x7F]\n" +
            "    \\p{XDigit}      A hexadecimal digit: [0-9a-fA-F]\n" +
            "    \\p{Space}       A whitespace character: [ \\t\\n\\x0B\\f\\r]\n" +
            "\n" +
            "java.lang.Character classes (simple java character type)\n" +
            "\n" +
            "    \\p{javaLowerCase}       Equivalent to java.lang.Character.isLowerCase()\n" +
            "    \\p{javaUpperCase}       Equivalent to java.lang.Character.isUpperCase()\n" +
            "    \\p{javaWhitespace}      Equivalent to java.lang.Character.isWhitespace()\n" +
            "    \\p{javaMirrored}        Equivalent to java.lang.Character.isMirrored()\n" +
            "\n" +
            "Classes for Unicode scripts, blocks, categories and binary properties\n" +
            "\n" +
            "    \\p{IsLatin}             A Latin script character (script)\n" +
            "    \\p{InGreek}             A character in the Greek block (block)\n" +
            "    \\p{Lu}                  An uppercase letter (category)\n" +
            "    \\p{IsAlphabetic}        An alphabetic character (binary property)\n" +
            "    \\p{Sc}                  A currency symbol\n" +
            "    \\P{InGreek}             Any character except one in the Greek block (negation)\n" +
            "    [\\p{L}&&[^\\p{Lu}]]      Any letter except an uppercase letter (subtraction)\n" +
            "\n" +
            "Boundary matchers\n" +
            "\n" +
            "    ^       The beginning of a line\n" +
            "    $       The end of a line\n" +
            "    \\b      A word boundary\n" +
            "    \\B      A non-word boundary\n" +
            "    \\A      The beginning of the input\n" +
            "    \\G      The end of the previous match\n" +
            "    \\Z      The end of the input but for the final terminator, if any\n" +
            "    \\z      The end of the input\n" +
            "\n" +
            "Linebreak matcher\n" +
            "\n" +
            "    \\R      Any Unicode linebreak sequence, is equivalent to \\u000D\\u000A|\n" +
            "            [\\u000A\\u000B\\u000C\\u000D\\u0085\\u2028\\u2029]\n" +
            "\n" +
            "Greedy quantifiers\n" +
            "\n" +
            "    X?          X, once or not at all\n" +
            "    X*          X, zero or more times\n" +
            "    X+          X, one or more times\n" +
            "    X{n}        X, exactly n times\n" +
            "    X{n,}       X, at least n times\n" +
            "    X{n,m}      X, at least n but not more than m times\n" +
            "\n" +
            "Reluctant quantifiers\n" +
            "\n" +
            "    X??         X, once or not at all\n" +
            "    X*?         X, zero or more times\n" +
            "    X+?         X, one or more times\n" +
            "    X{n}?       X, exactly n times\n" +
            "    X{n,}?      X, at least n times\n" +
            "    X{n,m}?     X, at least n but not more than m times\n" +
            "\n" +
            "Possessive quantifiers\n" +
            "    X?+         X, once or not at all\n" +
            "    X*+         X, zero or more times\n" +
            "    X++         X, one or more times\n" +
            "    X{n}+       X, exactly n times\n" +
            "    X{n,}+      X, at least n times\n" +
            "    X{n,m}+     X, at least n but not more than m times\n" +
            "\n" +
            "Logical operators\n" +
            "\n" +
            "    XY          X followed by Y\n" +
            "    X|Y         Either X or Y\n" +
            "    (X)         X, as a capturing group\n" +
            "\n" +
            "Back references\n" +
            "\n" +
            "    \\n              Whatever the nth capturing group matched\n" +
            "    \\k<name>        Whatever the named-capturing group \"name\" matched\n" +
            "\n" +
            "Quotation\n" +
            "\n" +
            "    \\       Nothing, but quotes the following character\n" +
            "    \\Q      Nothing, but quotes all characters until \\E\n" +
            "    \\E      Nothing, but ends quoting started by \\Q\n" +
            "\n" +
            "\n" +
            "Special constructs (named-capturing and non-capturing)\n" +
            "\n" +
            "    (?<name>X)              X, as a named-capturing group\n" +
            "    (?:X)                   X, as a non-capturing group\n" +
            "    (?idmsuxU-idmsuxU)      Nothing, but turns match flags i d m s u x U on - off\n" +
            "    (?idmsux-idmsux:X)      X, as a non-capturing group with the given flags i d m s u x on - off\n" +
            "    (?=X)                   X, via zero-width positive lookahead\n" +
            "    (?!X)                   X, via zero-width negative lookahead\n" +
            "    (?<=X)                  X, via zero-width positive lookbehind\n" +
            "    (?<!X)                  X, via zero-width negative lookbehind\n" +
            "    (?>X)                   X, as an independent, non-capturing group\n";

}
