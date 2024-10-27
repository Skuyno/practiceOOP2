package ru.ssau.tk.practiceoop1.concurrent;

import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;

public class ReadTask implements Runnable {

    private TabulatedFunction tabulatedFunction;

    public ReadTask(TabulatedFunction tabulatedFunction) {
        this.tabulatedFunction = tabulatedFunction;
    }

    @Override
    public void run() {
        int count = tabulatedFunction.getCount();
        for (int i = 0; i < count; i++) {
            System.out.printf("After read: i = %d, x = %f, y = %f%n", i, tabulatedFunction.getY(i), tabulatedFunction.getX(i));
        }
    }
}
