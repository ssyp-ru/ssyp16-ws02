# PETOOH Khan

Транслятор в байт-код JVM и интерпретатор языков PETOOH и Brainfuck.

Проект сделан в мастерской №2 "Котлин и Питу", Летняя школа юных программистов, 2016

## Функционал
* Перевод из Brainfuck в PETOOH и обратно
* Исполнение кода на Brainfuck или PETOOH
* Трансляция кода на Brainfuck или PETOOH в байт-код JVM
* Консольный и графический интерфейс

## Сборка
### Windows
    gradlew.bat
### Linux
    ./gradlew

## Запуск
Собранные jar-ы будут расположены в папке `build/libs/`. Один jar - с консольным интерфейсом, другой - с графическим.

Для запуска:

    java -jar <имя jar-а>.jar

## PetoohFun
Мастерская разработала расширение языка PETOOH: определения и вызовы функций.

Определение функции:

    Morning <имя функции> <Ke* - столько "Ke", сколько функция принимает параметров>
        <тело>
    Evening

Вызов функции:

    PAR <имя функции>

Функция принимает аргументы из основного массива памяти, из ячеек, предшествующих текущей ячейке под курсором. Для функции создаётся новый массив в 30000 байт, аргументы расположены в ячейках начиная с нулевой, а курсор указывает на нулевую ячейку. Значение возвращается из той ячейки, на которой останавливается курсор в конце функции.

Пример:

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

Чтобы попробовать данную функциональность, см. git-ветку `fun-compiler`.

## Лицензия
Лицензия MIT. См. файл LICENCE.md