package ru.ssau.tk.practiceoop1.functions;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction {

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

    LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        for (int i = 0; i < xValues.length; i++) {
            addNode(xValues[i], yValues[i]);
        }
    }

    LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
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
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
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
        if (x <= head.x) {
            return 0;
        }
        if (x >= head.prev.x) {
            return count - 1;
        }

        Node current = head;
        int index = 0;

        while (current != head.prev && current.x <= x) {
            index++;
            current = current.next;
        }
        return index - 1;
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        Node left = getNode(floorIndex);
        Node right = left.next;
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
        if (x <= head.x) {
            return head;
        }
        if (x >= head.prev.x) {
            return head.prev;
        }

        Node current = head;

        while (current.next != head && current.next.x <= x) {
            current = current.next;
        }

        return current;
    }

    @Override
    public double apply(double x) {
        Node floorNode = floorNodeOfX(x);

        if (x == floorNode.x) {
            return floorNode.y;
        }

        if (x < head.x) {
            return extrapolateLeft(x);
        }

        if (x > head.prev.x) {
            return extrapolateRight(x);
        }

        return interpolate(x, indexOfX(floorNode.x));
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

