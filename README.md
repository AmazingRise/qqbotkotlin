# QQ Group Chatting Robot
![](https://img.shields.io/badge/language-Kotlin-blueviolet.svg)
![](https://img.shields.io/badge/license-MIT-blue.svg)

## Introduction
This is a QQ robot back-end for group chatting.
The robot will try matching the keywords.
Once the keyword is hit, the robot will reply the preset paragraph.

**This readme file is still under construction.**
 
## Features
1. On-the-fly keyword modifying.
1. Operators supported. (They can access the server settings via private (or group) conversations with the robot.)
1. Blacklist supported. Users in blacklist will be ignored.
1. A simple command prompt.
1. Keywords loading and saving.
1. Keywords isolation among conversations.
1. Cool down time limitation.

## Build and Deployment
**NOTE:** If the instruction make you confused, don't be worried. The README file needs supplement, and I will try to make it more clear in the following days.

If you have any questions about build and deployment, please **open an issue**.
Thanks for your support.

### Build Instruction for Gradle
Add following lines to `build.gradle`.

For the `dependencies` section, please modify:
```groovy
dependencies{
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    implementation "com.squareup.moshi:moshi:1.8.0"
    compile "org.jetbrains.kotlin:kotlin-reflect:1.3.41"
    compile group: 'khttp', name: 'khttp', version: '1.0.0'
    testCompile group: 'junit', name: 'junit', version: '4.12'
}
```

And don't forget to add `jcenter` to the `repositories` section:
```groovy
repositories{
    mavenCentral()
    jcenter()
}
```
If you've done the instructions above, now you can build this project with gradle successfully.

### Deployment
**NOTE:** Our robot is referred as "back-end" in the following instructions.

1. Download CoolQ Air ([Official website](https://cqp.cc/)), and install the plugin [CoolQ HTTP API](https://cqp.cc/t/30748).
(Pro is better but not necessary.)
1. Deploy the plugin as HTTP server, and set `post_url` in configuration file as our Kotlin back-end's address. (default port is defined as `1080`, which lies in `/src/main/data/Global.kt`,the var `localPort`) [Official docs here](https://cqhttp.cc/docs/4.10/#/).
1. Modify `remoteAddress` (defined in `/src/main/data/Global.kt`) to the CoolQ HTTP API listening address. (e.g. for users whose CoolQ runs on localhost, at the port of 5700, the remote address may be 127.0.0.1:5700)
1. Now launch our back-end, CoolQ Air and enable CoolQ HTTP API plugin.
1. As a test. you can send a message `syn` to your bot. If your configuration is correct, you'll receive `ack` from the robot.