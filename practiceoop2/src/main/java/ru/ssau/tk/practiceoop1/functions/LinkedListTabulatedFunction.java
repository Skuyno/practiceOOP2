package ru.ssau.tk.practiceoop1.functions;

import ru.ssau.tk.practiceoop1.exceptions.InterpolationException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Removable, Insertable {

    private Node head;

    private void addNode(double x, double y) {
        Node newNode = new Node(x, y);
        if (head == null) {
            head = newNode;
            head.next = head;
            head.prev = head;
        } else {
            Node last = head.prev;
            last.next = newNode;
            newNode.prev = last;
            newNode.next = head;
            head.prev = newNode;
        }
        count++;
    }

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues.length < 2) {
            throw new IllegalArgumentException("Длина таблицы меньше минимальной (2 точки).");
        }
        checkLengthIsTheSame(xValues, yValues);
        checkSorted(xValues);
        for (int i = 0; i < xValues.length; i++) {
            addNode(xValues[i], yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("Длина таблицы меньше минимальной (2 точки).");
        }
        if (xFrom > xTo) {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }
        double step = (xTo - xFrom) / (count - 1);
        for (int i = 0; i < count; i++) {
            double x = xFrom + step * i;
            addNode(x, source.apply(x));
        }
    }

    private Node getNode(int index) {
        Node current = head;
        if (index < count / 2) { // Если индекс ближе к голове
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else { // Если индекс ближе к хвосту
            current = head.prev; // Начинаем с хвоста
            for (int i = count - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    @Override
    public double leftBound() {
        return head.x;
    }

    @Override
    public double rightBound() {
        return head.prev.x;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double getX(int index) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("Индекс выходит за допустимые пределы.");
        }
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("Индекс выходит за допустимые пределы.");
        }
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("Индекс выходит за допустимые пределы.");
        }
        getNode(index).y = value;
    }

    @Override
    public int indexOfX(double x) {
        Node current = head;
        for (int i = 0; i < count; i++) {
            if (current.x == x) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        Node current = head;
        for (int i = 0; i < count; i++) {
            if (current.y == y) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int floorIndexOfX(double x) {
        if (x < leftBound()) {
            throw new IllegalArgumentException("Значение x меньше левой границы.");
        }
        if (x >= rightBound()) {
            return count - 1;
        }

        Node current = head;
        int index = 0;

        while (current.next != null && current.next.x <= x) {
            current = current.next;
            index++;
        }
        return index;
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        Node left = getNode(floorIndex);
        Node right = left.next;
        //проверка
        if (x < left.x || x > right.next.x) {
            throw new InterpolationException("x находится вне интервала интерполирования.");
        }

        return left.y + (x - left.x) * (right.y - left.y) / (right.x - left.x);
    }

    @Override
    protected double extrapolateLeft(double x) {
        return interpolate(x, head.x, head.next.x, head.y, head.next.y);
    }

    @Override
    protected double extrapolateRight(double x) {
        return interpolate(x, head.prev.prev.x, head.prev.x, head.prev.prev.y, head.prev.y);
    }

    private Node floorNodeOfX(double x) {
        if (x < leftBound()) {
            throw new IllegalArgumentException("Значение x меньше левой границы.");
        }

        Node current = head;

        while (current.next != head && current.next.x <= x) {
            current = current.next;
        }

        return current;
    }

    @Override
    public double apply(double x) {

        if (x < leftBound()) {
            return extrapolateLeft(x);
        }
        if (x > rightBound()) {
            return extrapolateRight(x);
        }

        Node floorNode = floorNodeOfX(x);

        if (x == floorNode.x) {
            return floorNode.y;
        }

        return interpolate(x, indexOfX(floorNode.x));
    }

    @Override
    public void remove(int index) {
        if (count == 1) {
            count = 0;
            head = null;
            return;
        }

        Node nodeToRemove = getNode(index);

        // Удаление, если нужно удалить голову
        if (nodeToRemove == head) {
            head = nodeToRemove.next;
        }

        nodeToRemove.prev.next = nodeToRemove.next;
        nodeToRemove.next.prev = nodeToRemove.prev;
        count--;
    }

    @Override
    public void insert(double x, double y) {
        // Если список пустой, добавляем новый узел
        if (head == null) {
            addNode(x, y);
            return;
        }

        Node current = head;

        while (true) {
            if (current.x == x) {
                // Если значение уже существует, обновляем его
                current.y = y;
                return;
            } else if (current.x > x) {
                // Вставка перед текущим узлом
                Node newNode = new Node(x, y);
                newNode.prev = current.prev;
                newNode.next = current;

                current.prev.next = newNode;
                current.prev = newNode;

                if (current == head) {
                    head = newNode;
                }
                count++;
                return;
            } else if (current.next == head) {
                // Вставка в конец списка
                addNode(x, y);
                return;
            }

            current = current.next;
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<Point>() {
            private Node current = head;
            private int traversed = 0;

            @Override
            public boolean hasNext() {
                return traversed < count;
            }

            @Override
            public Point next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Больше нет элементов для итерации.");
                }
                Point point = new Point(current.x, current.y);
                current = current.next;
                traversed++;
                return point;
            }
        };
    }

    static class Node {
        public Node next;
        public Node prev;
        public double x;
        public double y;

        public Node(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}


