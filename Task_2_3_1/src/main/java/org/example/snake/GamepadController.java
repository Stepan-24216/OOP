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
    private final GameController gameController;
    private volatile boolean running = true;
    private volatile boolean readerThreadStarted = false;
    private volatile boolean gamepadConnected = false;
    private String currentDevicePath = null;

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
                gamepadConnected = true;
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
                while (running) {
                    int bytesRead = inputStream.read(buffer);
                    if (bytesRead == JS_EVENT_SIZE) {
                        processJoystickEvent(buffer);
                    }
                }
            } catch (IOException e) {
                if (running) {
                    System.err.println("Ошибка чтения геймпада: " + e.getMessage());
                }
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
        final int THRESHOLD = 10000;

        switch (axisNumber) {
            case 0:
                if (value < -THRESHOLD) {
                    Platform.runLater(() -> gameController.handleKeyPress(KeyCode.LEFT));
                } else if (value > THRESHOLD) {
                    Platform.runLater(() -> gameController.handleKeyPress(KeyCode.RIGHT));
                }
                break;

            case 1:
                if (value < -THRESHOLD) {
                    Platform.runLater(() -> gameController.handleKeyPress(KeyCode.UP));
                } else if (value > THRESHOLD) {
                    Platform.runLater(() -> gameController.handleKeyPress(KeyCode.DOWN));
                }
                break;

            case 6:
                if (value < 0) {
                    Platform.runLater(() -> gameController.handleKeyPress(KeyCode.LEFT));
                    break;
                } else if (value > 0) {
                    Platform.runLater(() -> gameController.handleKeyPress(KeyCode.RIGHT));
                    break;
                }
                break;

            case 7:
                if (value < 0) {
                    Platform.runLater(() -> gameController.handleKeyPress(KeyCode.UP));
                    break;
                } else if (value > 0) {
                    Platform.runLater(() -> gameController.handleKeyPress(KeyCode.DOWN));
                    break;
                }
                break;
            default:
                break;
        }
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

