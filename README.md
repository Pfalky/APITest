Папка src/main отвечает за реализацию основных классов

test/config - файл конфигурации, инициализирует список бинов, используемых приложением
test/service - служебные классы (генераторы,служебные утилиты)
test/testData - классы объектов фреймворка

main/resources/swagger - файлы описание swaggerAPI на основе которых собирается Jar файл с апишками

\APITest\src\test\java\ru\ifr\lcc\acceptance\test\service\swaggerService\generatorServices - классы генераторов пользователей и подписантов, используются в логике приложения

\APITest\src\test\java\ru\ifr\lcc\acceptance\test\LoginServiceTest.java - небольшой набор кейсов по генерации заявок, с последовательными проверками данных
