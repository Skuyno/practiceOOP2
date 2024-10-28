package ru.ssau.tk.practiceoop1.concurrent;

import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;

public class MultiplyingTask implements Runnable{
    private TabulatedFunction tab;
    MultiplyingTask(TabulatedFunction tab){
        this.tab = tab;

    }

    @Override
    public void run() {
        int count = tab.getCount();
        for (int i = 0; i < count; i++) {

            synchronized(tab) {
                double y = tab.getY(i);
                tab.setY(i, y * 2);
            }
        }

        System.out.println("Поток " + Thread.currentThread().getName() + " закончил выполнение задачи.");
    }
}
