# RegMatch - Capture the flag challenge
This repository contains a simple Cli Regex tool server with a not so simple exploit. This server is purposely 
restrictive on user-input to limit the attack-vector scope. Your objective is to find any oversight or loophole left
in the open.

The challenge consists of one flag only. Once the intended attack is performed successfully, the flag
will be printed. No source code inspection necessary to acquire the flag.

## Difficulty:
This challenge might prove difficult if you’re unfamiliar with regular expressions,
but moderate if you understand how most regex constructs work, both in day-to-day use, and under-the-hood.

## Setup instructions

1. Download the latest RegMatch.jar file
2. Start the process: 'java -jar RegMatch.jar'

The server will interact with parent process directly through stdin/stdout, 
so it should be executed through a terminal.


## Hints (SPOILER ALERT)
1. The system rejects long patterns and match strings. Why? Is there any loophole, or oversight?

2. Time complexity is of the essence.

3. Size doesn't matter. Evil regexes do.
