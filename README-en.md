# PETOOH Khan

[PETOOH](https://github.com/Ky6uk/PETOOH) and Brainfuck interpreter and translator.

Made in workshop 02 "Kotlin & PETOOH" at [Summer School of Young Programmers](http://school.iis.nsk.su/info), 2016.

## Features
* Translate Brainfuck to PETOOH and vice versa
* Interpret Brainfuck or PETOOH code
* Translate Brainfuck or PETOOH code to JVM bytecode
* CLI and GUI provided

## Build
### Windows
    gradlew.bat
### Linux
    ./gradlew

## Run
The jars will be located in `build/libs/` directory. One jar is for CLI, and the other is for GUI.

To launch:

    java -jar <jarname>.jar

## PetoohFun
An extension to PETOOH language has been developed in the workshop. It contains function definitions and invokations.

Function definition:

    Morning <function name> <Ke* - as many Ke's as the function takes parameters>
        <body>
    Evening

Function invokation:

    PAR <function name>

The function takes the arguments from the main memory array cells preceding the one that the cursor is pointing at. A new array of 30000 bytes is created, the arguments are located in the first cells, and the cursor is pointing at cell 0. The value in the cell at which the function cursor is pointing in the end is returned.

Example:

    Morning sum KeKe
        kudah kudah
        Kud
            kO Kudah
            Ko kudah
        kud
        Kudah
    Evening
    PAR sum
    Kukarek

To try it, see git branch `fun-compiler`.

## License
See LICENCE.md