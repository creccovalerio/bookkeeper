#!/bin/sh
BADUACLI="ba-dua-cli-0.6.0-all.jar"
BADUASER="../bookkeeper-server/target/badua.ser"
CLASSES="../bookkeeper-server/target/classes"
BADUAXML="../bookkeeper-server/target/badua.xml"

java -jar ${BADUACLI} report    \
        -input ${BADUASER}      \
        -classes ${CLASSES}     \
        -show-classes           \
        -show-methods           \
        -xml ${BADUAXML}        \
