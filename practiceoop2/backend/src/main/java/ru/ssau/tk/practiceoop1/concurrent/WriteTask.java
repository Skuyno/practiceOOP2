package ru.ssau.tk.practiceoop1.concurrent;

import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;

public class WriteTask implements Runnable{

    private TabulatedFunction tabulatedFunction;
    private double value;

    public WriteTask(TabulatedFunction tabulatedFunction, double value) {
        this.tabulatedFunction = tabulatedFunction;
        this.value = value;
    }

    @Override
    public void run() {
        int count = tabulatedFunction.getCount();
        for(int i = 0; i < count; i++){
            synchronized (tabulatedFunction){
                tabulatedFunction.setY(i, value);
                System.out.printf("Writing for index %d complete\n", i);
            }
        }
    }
}
