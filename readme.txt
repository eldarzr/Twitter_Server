SERVER:
run the command: $mvn compile
in order to run the thread per client you should run the command $mvn exec:java@tpc -Dexec.args="<port>"
EXAMPLE:
$mvn exec:java@tpc -Dexec.args="7777"

in order to run the reactor you should run the command $mvn exec:java@reactor -Dexec.args="<number of threads> <port>"
EXAMPLE:
$mvn exec:java@reactor -Dexec.args="2 7777"

CLIENT:
run the command: $make
in order to run the client you should run the command $bin/BGSClient <ip> <port>
EXAMPLE:
$bin/BGSClient 127.0.0.1 7777


EXAMPLES:
REGISTER:
REGISTER <username> <password> <dd-MM-YYYY>
REGISTER eldar eldar 20-01-1998

LOGIN:
LOGIN <username> <password> <captcha>
LOGIN eldar eldar 1

LOGOUT:
LOGOUT

FOLLOW:
FOLLOW <0 for follow/1 for unfollow> <username>
FOLLOW 0 naor
//will follow naor
FOLLOW 1 naor
//will unfollow naor

POST:
POST <content>
POST hello world. this is spl 3 assignment.

PM:
PM <username> <content>
PM naor hey, how are you today?

LOGSTAT:
LOGSTAT

STAT:
STAT <username>|<username>|<username>(etc.)
STAT naor|avi|shalom
STAT naor|avi
STAT naor

BLOCK:
BLOCK <username>
BLOCK naor
