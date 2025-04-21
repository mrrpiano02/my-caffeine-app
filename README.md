### my-caffeine-app
_Java clone of Caffeine anti-sleep utility_\
Link: [https://www.zhornsoftware.co.uk/caffeine/](https://www.zhornsoftware.co.uk/caffeine/)

This app serves as a GUI utility for preventing one's system from sleeping. It does so by sending a keyboard signal (F15) once per minute, much like the original Caffeine app.

The main difference between the two utilities is that this app, being built under the Java Swing framework, is platform-agnostic and is controlled from a GUI. This fact is my main inspiration for writing my own version of Caffeine; I wanted an anti-sleep program that I could use on my Linux boxes without having to worry too much about compatibility. 

Currently, this app is controllable exclusively via GUI - however, I would like to publish an applet interface as well.

### Installation
One can simply download and run the .jar file using `java -jar my-caffeine.jar`

### Compilation
1. Clone this repository using `git clone https://github.com/mrrpiano02/my-caffeine-app.git`
2. Navigate to just outside the source code directory using `cd src`
3. If there is no build directory, make one using `mkdir build`
4. Compile the source code using `javac my-caffeine/*.java -d build`
5. Navigate to the build directory (`cd build`) and execute using `java my-caffeine.Caffeine`
