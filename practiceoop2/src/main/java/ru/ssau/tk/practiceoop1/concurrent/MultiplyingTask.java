package ru.ssau.tk.practiceoop1.concurrent;

import ru.ssau.tk.practiceoop1.functions.LinkedListTabulatedFunction;


public class MultiplyingTask implements Runnable {
    private final LinkedListTabulatedFunction function;
    private volatile boolean isCompleted = false; // Флаг завершения задачи

    public MultiplyingTask(LinkedListTabulatedFunction function) {
        this.function = function;
    }

    @Override
    public void run() {
        for (int i = 0; i < function.getCount(); i++) {
            synchronized (function) {
                double y = function.getY(i);
                function.setY(i, y * 2);
            }
        }
        isCompleted = true; // Устанавливаем флаг завершения
        System.out.println("Задача завершена: " + Thread.currentThread().getName());
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}