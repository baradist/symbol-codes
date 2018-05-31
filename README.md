# symbol-codes
## Short manual

## Build

You supposed to have jdk8+ installed

`git clone https://github.com/baradist/symbol-codes.git`

`cd symbol-codes`

`./gradlew build (or gradlew.bat build on Windows)`

## Run
`java -jar build/libs/symbol-codes.jar -a <algorithm> -m "<message>"`

(on Windows: `java -jar build\libs\symbol-codes.jar -a <algorithm> -m "<message>"`)

For instance: 

`java -jar build/libs/symbol-codes.jar -a sf -m "Football World Championship"`
