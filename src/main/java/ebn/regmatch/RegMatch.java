package ebn.regmatch;

import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static ebn.regmatch.Constants.*;
import static ebn.regmatch.Constants.fallbackMessage;

public class RegMatch {


    public static void start(Receiver<String> source, Dispatcher<String> sink) throws IOException {
        preQuery(source, sink);
        while (true) onQuery(source, sink);
    }

    private static void preQuery(Receiver<String> source, Dispatcher<String> sink) {
        sink.dispatch("\r\n\r\n" + welcomeMessage);
    }

    private static void onQuery(Receiver<String> source, Dispatcher<String> sink) throws IOException {

        sink.dispatch(promptReady);
        String in = source.get().trim();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                switch (i) {
                    case 0:
                        sink.dispatch("Working...");
                        break;
                    case 1:
                        sink.dispatch("...");
                        break;
                    case 2:
                        sink.dispatch("...");
                        break;
                    case 3:
                        sink.dispatch("...............");
                        break;
                    case 4:
                        sink.dispatch("...............");
                        break;
                    case 5:
                        sink.dispatch(("\r\n^FLAG^" + UUID.randomUUID().toString().replace("-", "") + "$FLAG$\r\n"));
                        break;
                    case 6:
                        sink.dispatch("Error: non-responsive thread! Terminating process.\r\n");
                        System.exit(-1);
                    default:
                        this.cancel();
                }
                i++;
            }
        };
        timer.schedule(timerTask, 3000, 3000);

        Matcher query = _2arg.matcher(in);
        if (query.matches()) {
            switch (_2Args.valueOf(query.group(cmd))) {
                case matches:
                    sink.dispatch(isMatch(query.group(arg1), query.group(arg2)));
                    break;
                case group:
                    sink.dispatch(groupScan(query.group(arg1), query.group(arg2)));
                    break;
                case contains:
                    sink.dispatch(query.group(arg1).contains(query.group(arg2)) ? "True" : "False");
                    break;
                case indexOf:
                    sink.dispatch(String.valueOf(query.group(arg1).indexOf(query.group(arg2))));
                    break;
            }
            timer.cancel();
            return;
        }


        query = _1arg.matcher(in);
        if (query.matches()) {
            switch (_1Args.valueOf(query.group(cmd))) {
                case grep:
                    sink.dispatch(filterDocLines(query.group(arg1)));
                    break;
            }
            timer.cancel();
            return;
        }


        query = _0arg.matcher(in);
        if (query.matches()) {
            switch (_0Args.valueOf(query.group(cmd))) {
                case help:
                    sink.dispatch(welcomeMessage);
                    break;
                case cmd:
                    sink.dispatch(commands);
                    break;
                case releases:
                    sink.dispatch(releaseNote);
                    break;
                case doc:
                    sink.dispatch(manual);
                    break;
                case quit:
                    timer.cancel();
                    sink.dispatch("Goodbye!\r\n");
                    System.exit(0);
            }
        } else if (!in.isEmpty()) sink.dispatch(fallbackMessage);
        timer.cancel();
    }

    private static String groupScan(String regex, String arg) {
        if (regex.length() > 50) return "Pattern of: " + regex.length() + " codepoints exceeds limit of 50.";
        if (arg.length() > 1000)
            return "String to match contains: " + arg.length() + " codepoints. Max permitted is 1000.";
        Matcher pattern;
        try {
            pattern = Pattern.compile(regex).matcher(arg);
        } catch (PatternSyntaxException e) {
            return "Syntax error: " + e.getDescription();
        }
        if (pattern.find()) {
            StringBuilder stringBuilder = new StringBuilder(1024);
            for (int i = 1; i < pattern.groupCount() + 1; i++) {
                stringBuilder.append("Group ").append(i).append(": ").append(pattern.group(i)).append("\n");
            }
            return "Scan result from " + pattern.groupCount() + (pattern.groupCount() > 1 ? " groups:\n" : " group:\n") + stringBuilder;
        }
        return "No group match detected.";
    }

    private static String isMatch(String regex, String arg) {
        if (regex.length() > 50) return "Pattern of: " + regex.length() + " codepoints exceeds limit of 50.";
        if (arg.length() > 1000)
            return "String to match contains: " + arg.length() + " codepoints. Max permitted is 1000.";
        try {
            return Pattern.compile(regex).matcher(arg).matches() ? "True" : "False";
        } catch (PatternSyntaxException e) {
            return "Syntax error: " + e.getDescription();
        }
    }

    private static String filterDocLines(String search) {
        String lowered = search.toLowerCase();
        return Arrays.stream(manual.split("[\r\n]+"))
                .filter(s -> s.toLowerCase().contains(lowered))
                .reduce((s, s2) -> s + "\r\n" + s2)
                .orElse("No match found.");
    }
}
