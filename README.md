# RegMatch - Capture the flag challenge

###How to play?

To play the challenge, simply download the RegMatch.jar from:
https://github.com/eivind-bn/IDATT2503

Launch the application in the terminal as follows:

'java -jar RegMatch [port number]'

Afterwards, simply connect to the server by telnet, or any other suitable alternatives.
The interface will present supported commands once connected. Telnet command:

‘telnet localhost [port number]’.

### Regex example:

'^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$' matches 'ola.nordmann@gmail.com'

Returns true.

'^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$' matches 'øla.normann@gmail.com'

Returns false.

### Hint:

A subtle hint is implicitly provided in the changelog via the command-line-interface once connected.

### Explicit hint: (SPOILER ALERT!)

1. The system rejects long patterns and match strings. Why? Is there any loophole, or
oversight?

2. Time complexity is of the essence. Can you worsen it somehow?

3. Size doesn't matter. Evil regexes do.

### Difficulty:
This challenge might prove difficult if you’re unfamiliar with regular expressions, 
but moderate if you have mastered the arts of google. 
Happy hacking, and enjoy your stay at regex hell.

