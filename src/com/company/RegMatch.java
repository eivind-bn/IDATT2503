package com.company;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.company.Constants.*;
import static com.company.Constants.fallbackMessage;

public class RegMatch implements Server {

    @Override
    public void init(Socket session, Receiver<String> input, Dispatcher<String> output) throws IOException {
        output.dispatch("\r\n\r\n" + welcomeMessage);
    }

    @Override
    public void execute(Socket session, Receiver<String> input, Dispatcher<String> output) throws IOException {

        output.dispatch(prompReady);
        String in = input.get().trim();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                try {
                    switch (i){
                        case 0:
                            output.dispatch("Working...");
                            break;
                        case 1:
                            output.dispatch("...");
                            break;
                        case 2:
                            output.dispatch("...");
                            break;
                        case 3:
                            output.dispatch("...............");
                            break;
                        case 4:
                            output.dispatch("...............");
                            break;
                        case 5:
                            output.dispatch(("\r\n^FLAG^" + UUID.randomUUID().toString().replace("-","") + "$FLAG$\r\n"));
                            break;
                        case 6:
                            output.dispatch("Error! System not responding. Terminating process.\r\n");
                            session.close();
                            System.exit(-1);
                        default: this.cancel();
                    }
                    i++;
                } catch (IOException ignored){}
            }
        };
        timer.schedule(timerTask, 3000, 3000);

        Matcher query = _2arg.matcher(in);
        if (query.find()) {
            switch (_2Args.valueOf(query.group(cmd))) {
                case matches:
                    output.dispatch(isMatch(query.group(arg1), query.group(arg2)));
                    break;
                case group:
                    output.dispatch(groupScan(query.group(arg1), query.group(arg2)));
                    break;
                case contains:
                    output.dispatch(query.group(arg1).contains(query.group(arg2)) ? "True" : "False");
                    break;
                case indexOf:
                    output.dispatch(String.valueOf(query.group(arg1).indexOf(query.group(arg2))));
                    break;
            }
            timer.cancel();
            return;
        }


        query = _1arg.matcher(in);
        if (query.find()) {
            switch (_1Args.valueOf(query.group(cmd))) {
                case regex:
                    output.dispatch(filterDocLines(query.group(arg1)));
                    break;
            }
            timer.cancel();
            return;
        }


        query = _0arg.matcher(in);
        if (query.find()) {
            switch (_0Args.valueOf(query.group(cmd))) {
                case help:
                    output.dispatch(welcomeMessage);
                    break;
                case cmd:
                    output.dispatch(commands);
                    break;
                case changelog:
                    output.dispatch(changelog);
                    break;
                case regex:
                    output.dispatch(manual);
                    break;
                case quit:
                    output.dispatch("Goodbye!\r\n");
            }
        } else if (!in.isEmpty()) output.dispatch(fallbackMessage);
        timer.cancel();
    }

    private static String groupScan(String regex, String arg) {
        if (regex.length() > 50) return "Pattern of: " + regex.length() + " codepoints exceeds limit of 50.";
        if (arg.length() > 1000)
            return "String to match contains: " + arg.length() + " codepoints. Max permitted is 1000.";
        Matcher pattern = Pattern.compile(regex).matcher(arg);
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
        return Pattern.compile(regex).matcher(arg).matches() ? "True" : "False";
    }

    private static String filterDocLines(String search) {
        return Arrays.stream(manual.split("[\r\n]+"))
                .filter(s -> s.contains(search))
                .reduce((s, s2) -> s + "\r\n" + s2)
                .orElse("No match found.");
    }
}
