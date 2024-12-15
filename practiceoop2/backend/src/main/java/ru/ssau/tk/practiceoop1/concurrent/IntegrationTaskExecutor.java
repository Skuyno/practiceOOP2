package ru.ssau.tk.practiceoop1.concurrent;

import ru.ssau.tk.practiceoop1.functions.TabulatedFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IntegrationTaskExecutor {

    private final ExecutorService executor;
    private final int numberOfThreads;

    public IntegrationTaskExecutor() {
        this.numberOfThreads = Runtime.getRuntime().availableProcessors() - 1;
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
    }

    public IntegrationTaskExecutor(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
    }

    public double integrate(TabulatedFunction function, int totalSteps) throws ExecutionException, InterruptedException {
        double deltaX = (function.rightBound() - function.leftBound()) / numberOfThreads;
        double sumOfTrapezoids = 0;
        List<Future<Double>> futureList = new ArrayList<>();
        int stepsPerThread = totalSteps / numberOfThreads;
        int remainingSteps = totalSteps % numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            double lower = function.leftBound() + i * deltaX;
            double upper = lower + deltaX;
            int steps = stepsPerThread + (i < remainingSteps ? 1 : 0);
            IntegrationTask task = new IntegrationTask(function, lower, upper, steps);
            futureList.add(executor.submit(task));
        }

        for (Future<Double> future : futureList) {
            sumOfTrapezoids += future.get();
        }

        return sumOfTrapezoids;
    }

    public void shutdown() {
        executor.shutdown();
    }
}
