# RegMatch - Capture the flag challenge

###How to play?

To play the challenge, simply download the RegMatch.jar from:
https://github.com/eivind-bn/IDATT2503

Launch the application in the terminal as follows:

'java -jar RegMatch [optional port-number]'

The application will default to port 8080 if provided argument is missing, or malformed.
Afterwards, simply connect to the server by telnet, or any other suitable alternatives.
The interface will present supported commands once connected. Telnet command:

‘telnet localhost [your port number]’.

### Regex example:

'^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$' matches 'ola.nordmann@gmail.com'

Returns true.

'^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$' matches 'øla.normann@gmail.com'

Returns false.

### Hint:

A subtle hint is implicitly provided in the challenge. For instance the changelog in the
terminal.

### Explicit hint: (SPOILER ALERT!)

The system rejects long patterns and match strings. Why? Is there any loophole, or
oversight?

Time complexity is of the essence. Can you worsen it somehow?

Size doesn't matter. Evil regexes do.

### Difficulty:
This challenge might prove difficult if you’re unfamiliar with regular expressions, 
but moderate if you have mastered the arts of google. 
Beware the pitfalls of regexes, as powerful and eye pleasing they may be, 
they can easily drive you to madness.

