package xogame;

import java.util.Random;
import java.util.Scanner;

public class Logic {
    static int SIZE = 5;
    static int DOTS_TO_WIN = 4;

    static final char DOT_X = 'X';
    static final char DOT_O = 'O';
    static final char DOT_EMPTY = '.';

    static char[][] map;

    static Random random = new Random();
    static boolean gameFinished = false;

    static void go() {
        gameFinished = true;
        printMap();
        if (checkWin(DOT_X)) {
            System.out.println("Вы победили! Поздравляем!");
            return;
        }
        if (isFull()) {
            System.out.println("Ничья!");
            return;
        }

        aiTurn();
        printMap();
        if (checkWin(DOT_O)) {
            System.out.println("Компьютер победил! ");
            return;
        }
        if (isFull()) {
            System.out.println("Ничья!");
            return;
        }
        gameFinished = false;
    }

    static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    static void printMap() {
        System.out.print("  ");
        for (int i = 1; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void humanTurn(int x, int y) {
        if (gameFinished) {
            return;
        }
        if (isCellValid(y, x)) {
            map[y][x] = DOT_X;
            go();
        }
    }

    static boolean isCellValid(int y, int x) {
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) {
            return false;
        }
        return map[y][x] == DOT_EMPTY;
    }

    static void aiTurn() {
        int x = -1, y = -1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isCellValid(i, j)) {
                    map[i][j] = DOT_X;
                    if (checkWin(DOT_X)) {
                        x = j;
                        y = i;
                    }
                    map[i][j] = DOT_EMPTY;
                }
            }
        }

        if (x == -1 && y == -1) {
            do {
                x = random.nextInt(SIZE);
                y = random.nextInt(SIZE);
            } while (!isCellValid(y, x));
        }

        map[y][x] = DOT_O;
    }

    static boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean checkWin(char c) {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (checkGorizont(i, j, c) || checkVertical(i, j, c)
                        || checkDiogonal1(i, j, c) || checkDiogonal2(i, j, c)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean checkGorizont(int x, int y, char symbol) {
        if (x < 0 || y < 0 || x + DOTS_TO_WIN > SIZE || y >= SIZE) {
            return false;
        }
        int k = 0;
        for (int i = 0; i < DOTS_TO_WIN; i++) {
            if (map[i + x][y] == symbol) {
                k++;
            }
        }
        return k == DOTS_TO_WIN;
    }

    public static boolean checkVertical(int x, int y, char symbol) {
        if (x < 0 || y < 0 || y + DOTS_TO_WIN > SIZE || x >= SIZE) {
            return false;
        }
        int k = 0;
        for (int i = 0; i < DOTS_TO_WIN; i++) {
            if (map[x][y + i] == symbol) {
                k++;
            }
        }
        return k == DOTS_TO_WIN;
    }

    public static boolean checkDiogonal1(int x, int y, char symbol) {
        if (x < 0 || y < 0 || x + DOTS_TO_WIN > SIZE || y + DOTS_TO_WIN > SIZE) {
            return false;
        }
        int k = 0;
        for (int i = 0; i < DOTS_TO_WIN; i++) {
            if (map[x + i][y + i] == symbol) {
                k++;
            }
        }
        return k == DOTS_TO_WIN;
    }

    public static boolean checkDiogonal2(int x, int y, char symbol) {
        if (x < 0 || y < 0 || x + DOTS_TO_WIN > SIZE || y + 1 - DOTS_TO_WIN < 0) {
            return false;
        }
        int k = 0;
        for (int i = 0; i < DOTS_TO_WIN; i++) {
            if (map[x + i][y - i] == symbol) {
                k++;
            }
        }
        return k == DOTS_TO_WIN;
    }
}
