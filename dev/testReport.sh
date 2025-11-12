#!/bin/bash

#export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64/

sbt "clean;compile" && sbt test && sbt generateTestReport

