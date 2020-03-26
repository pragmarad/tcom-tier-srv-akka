TCP Akka Streams Server POC
==================================
Inception year: **2020**

## Overview
It's server tier for akka streams TCP PoC. Idea is to run stream capable server using akka Tcp extension.
Having different variations of response from TCP server different cases of handling streams can be checked.
Overall, the goal is to validate how well TCP akka streams work in various loads. 

## Build
To build project, sbt 1.3.8+ is needed. Install sbt launcher and add setup dir in $PATH.
If you want to check cross compilation, use commands with '+' e.g. for compile:
```
sbt '+ compile'
```

## Run
Using sbt with all defaults:
```
sbt "runMain tech.pragmarad.tcom.server.TcpAkkaStreamServerApp"
```
Also, with sample arguments:
```
sbt "runMain tech.pragmarad.tcom.server.TcpAkkaStreamServerApp" --srvhost localhost --srvport 1661 --message tst1 --frequencymsecs 1000 --maxburstcount 10
```
Flexibility of input parameters allows creating different test cases for TCP akka streaming.


## Test
For unit testing usual junit can be used:
```
sbt test
```

## Publish
In order to publish in local repo you can use:
```
sbt publishLocal
```
To publish into  [bintray repo](https://bintray.com/pragmarad-tech/tcom-scala-akka/tcom-tier-srv-akka), use:
```
sbt publish
```

## Release
In order to release new version use:
```
sbt release
```

## Logging
SLF4J with logback impl used.

# Links

## Status
* 2020-03-07 - 0.0.1-SNAPSHOT - Init.
* 2020-03-25 - 0.1.0 - Args handling added.

# Roadmap
1. (DONE) Add basic TCP server with untyped actors use.
2. (DONE) Make port (and host?) configurable (Args -> env var -> app conf -> constants).
3. Replace untyped to typed actors.
4. Update more complicated message handling (imagine protocols with timeouts, errors, traffic overloads).
5. Prepare for local deployments.
6. Prepare for containers (docker/k8s).
