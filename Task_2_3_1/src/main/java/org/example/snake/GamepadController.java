package org.example.snake;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import org.example.game.GameController;

/**
 * Класс для работы с геймпадом.
 */
public class GamepadController {
    private static final int JS_EVENT_SIZE = 8;
    private static final int JS_EVENT_BUTTON = 0x01;
    private static final int JS_EVENT_AXIS = 0x02;
    private static final int JS_EVENT_INIT = 0x80;
    private static final int DEAD_ZONE = 10000;
    private static final int HYSTERESIS = 2500;
    private final GameController gameController;
    private volatile boolean readerThreadStarted = false;
    private String currentDevicePath = null;
    private int horizontalAxisValue = 0;
    private int verticalAxisValue = 0;
    private Direction lastEmittedDirection = null;

    /**
     * Конструктор.
     */
    public GamepadController(GameController gameController) {
        this.gameController = gameController;
        initializeGamepad();
    }

    /**
     * Поиск геймпада в портах.
     */
    private void initializeGamepad() {
        for (int i = 0; i < 10; i++) {
            String devicePath = "/dev/input/js" + i;
            File device = new File(devicePath);
            if (device.exists() && device.canRead()) {
                currentDevicePath = devicePath;
                System.out.println("Геймпад найден: " + devicePath);
                startGamepadThread();
                return;
            }
        }
        System.out.println("Геймпад не найден. Используется только клавиатура.");
    }

    /**
     * Начало считывания нажатий с геймпада.
     */
    public void startGamepadThread() {
        if (readerThreadStarted || currentDevicePath == null) {
            return;
        }
        readerThreadStarted = true;

        Thread gamepadThread = new Thread(() -> {
            byte[] buffer = new byte[JS_EVENT_SIZE];
            try (FileInputStream inputStream = new FileInputStream(currentDevicePath)) {
                while (!Thread.currentThread().isInterrupted()) {
                    int bytesRead = inputStream.read(buffer);
                    if (bytesRead == JS_EVENT_SIZE) {
                        processJoystickEvent(buffer);
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка чтения геймпада: " + e.getMessage());
            } finally {
                readerThreadStarted = false;
            }
        });
        gamepadThread.setDaemon(true);
        gamepadThread.start();
    }

    /**
     * Превращение байтов в кнопку и доп информацию о ней.
     */
    private void processJoystickEvent(byte[] eventData) {
        ByteBuffer buffer = ByteBuffer.wrap(eventData);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        // Структура js_event:
        // __u32 time (4 байта) - временная метка
        // __s16 value (2 байта) - значение (-32767 до 32767 для осей, 0/1 для кнопок)
        // __u8 type (1 байт) - тип события
        // __u8 number (1 байт) - номер оси или кнопки

        buffer.getInt();
        short value = buffer.getShort();
        byte type = buffer.get();
        byte number = buffer.get();

        int eventType = type & ~JS_EVENT_INIT;

        if (eventType == JS_EVENT_AXIS) {
            handleAxisEvent(number, value);
        } else if (eventType == JS_EVENT_BUTTON) {
            handleButtonEvent(number, value);
        }
    }

    /**
     * Обработка нажатий и наклонения стиков.
     */
    private void handleAxisEvent(int axisNumber, short value) {
        switch (axisNumber) {
            case 0:
                horizontalAxisValue = value;
                emitResolvedDirection(axisNumber);
                break;

            case 1:
                verticalAxisValue = value;
                emitResolvedDirection(axisNumber);
                break;

            case 6:
                horizontalAxisValue = value < 0 ? Short.MIN_VALUE : value > 0 ? Short.MAX_VALUE : 0;
                emitResolvedDirection(axisNumber);
                break;

            case 7:
                verticalAxisValue = value < 0 ? Short.MIN_VALUE : value > 0 ? Short.MAX_VALUE : 0;
                emitResolvedDirection(axisNumber);
                break;
            default:
                break;
        }
    }

    /**
     * Преобразование состояния стика в одно направление.
     */
    private void emitResolvedDirection(int changedAxis) {
        Direction direction = resolveDirection(changedAxis);
        if (direction == null || direction == lastEmittedDirection) {
            if (isCentered()) {
                lastEmittedDirection = null;
            }
            return;
        }

        lastEmittedDirection = direction;
        Platform.runLater(() -> gameController.handleDirectionInput(direction));
    }

    /**
     * Определение направления по текущему состоянию осей.
     */
    private Direction resolveDirection(int changedAxis) {
        boolean horizontalActive = Math.abs(horizontalAxisValue) > DEAD_ZONE;
        boolean verticalActive = Math.abs(verticalAxisValue) > DEAD_ZONE;

        if (!horizontalActive && !verticalActive) {
            return null;
        }

        if (horizontalActive && !verticalActive) {
            return horizontalAxisValue < 0 ? Direction.LEFT : Direction.RIGHT;
        } else if (!horizontalActive) {
            return verticalAxisValue < 0 ? Direction.UP : Direction.DOWN;
        }

        int horizontalAbs = Math.abs(horizontalAxisValue);
        int verticalAbs = Math.abs(verticalAxisValue);

        if (horizontalAbs > verticalAbs + HYSTERESIS) {
            return horizontalAxisValue < 0 ? Direction.LEFT : Direction.RIGHT;
        }

        if (verticalAbs > horizontalAbs + HYSTERESIS) {
            return verticalAxisValue < 0 ? Direction.UP : Direction.DOWN;
        }

        Direction fallbackDirection = lastEmittedDirection;
        if (fallbackDirection != null) {
            return fallbackDirection;
        }

        return changedAxis == 0
            ? (horizontalAxisValue < 0 ? Direction.LEFT : Direction.RIGHT)
            : (verticalAxisValue < 0 ? Direction.UP : Direction.DOWN);
    }

    /**
     * Проверка, что стик вернулся в центр.
     */
    private boolean isCentered() {
        return Math.abs(horizontalAxisValue) <= DEAD_ZONE
            && Math.abs(verticalAxisValue) <= DEAD_ZONE;
    }

    /**
     * Кнопочки с фигурками :)
     */
    private void handleButtonEvent(int buttonNumber, short value) {
        // value: 1 = нажата, 0 = отпущена
        boolean pressed = (value == 1);

        if (!pressed) {
            return;
        }

        switch (buttonNumber) {
            case 0: // Кнопка X
                Platform.runLater(() -> gameController.handleKeyPress(KeyCode.SPACE));
                break;
            case 1: // Кнопка O
                break;
            case 2: // Кнопка Square
                break;
            case 3: // Кнопка Triangle
                break;
        }
    }
}

