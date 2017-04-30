# Tests for school.hh.ru

## Структура


testframework/src/


    |-- main
    |   |-- java
    |   |   |-- ru
    |   |       |-- hh
    |   |           |-- school
    |   |               |-- testframework
    |   |                   |-- db (методы по взаимодействию с бд)
    |   |                   |
    |   |                   |-- ui (pageObject-ы - классы с описанием страниц и методами взаимодействия)
    |   |                   |   
    |   |                   |-- util (утильные классы) 
    |   |                       
    |   |                       
    |   |-- resources
    |       |-- app.properties (настройки фреймворка)
    |       |-- sql (скрипты)
    |         
    |-- test
        |-- java
            |-- ru
                |-- hh
                    |-- school
                        |-- testframework
                            |--(Тут располагаются тесты)
                            
                            
                            
## Согласешния 
1. В описании страниц придерживаемся паттерна PageObject.
2. Поля классов именуются следующим образом : тип_элемента + смысл (например, buttonSave, fieldLogin, labelError и тп)

## Запуск

1. Можно из IDE
2. Можно mvn clean test
