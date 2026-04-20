package org.example.workers;

import org.example.enums.TypeWorker;

/**
 * Интерфейс работника.
 */
public interface Worker extends Runnable {
    TypeWorker getType();
}
