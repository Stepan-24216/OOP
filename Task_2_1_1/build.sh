#!/bin/bash

mkdir -p build/classes
mkdir -p build/docs
mkdir -p lib

# Проверяем наличие JUnit
if [ ! -f "lib/junit-platform-console-standalone-1.9.2.jar" ]; then
    echo "Скачивание JUnit..."
    wget -q -P lib https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.2/junit-platform-console-standalone-1.9.2.jar
fi

# Проверяем что JUnit скачался
if [ ! -f "lib/junit-platform-console-standalone-1.9.2.jar" ]; then
    echo "ОШИБКА: Не удалось скачать JUnit!"
    exit 1
fi

# Компилируем исходники
echo "Компиляция исходников..."
javac -d build/classes src/main/java/org/example/*.java

# Генерируем документацию (игнорируем предупреждения)
echo "Генерация документации..."
javadoc -d build/docs -sourcepath src/main/java -subpackages com.example.Expression 2>/dev/null || true

# Создаем JAR файл
echo "Создание JAR файла..."
jar cf build/Expression.jar -C build/classes .

echo ""
echo "=== Компиляция тестов ==="
# Компилируем тесты
javac -cp "build/classes:lib/junit-platform-console-standalone-1.9.2.jar" -d build/test src/test/java/com/example/*.java

echo ""
echo "=== Запуск тестов ==="
# Запускаем тесты
java -jar lib/junit-platform-console-standalone-1.9.2.jar \
    --class-path "build/classes:build/test" \
    --select-class com.example.OperationTest \
    --details summary

echo ""
echo "=== Сборка завершена ==="
echo "Документация: build/docs/index.html"
echo "JAR файл: build/heapsort.jar"