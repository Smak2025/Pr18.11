import java.util.Arrays;
import java.util.Random;

void printMatrix(int[][] m) {
    for (int[] row : m) {
        for (int v : row) System.out.printf("%5d", v);
        System.out.println();
    }
    System.out.println();
}

void blur(int[][] m) {
    int[][] backup = m.clone();
    int sum = 0;
    for (int i = 1; i < m.length - 1; i++) {
        for (int j = 1; j < m[0].length - 1; j++) {
            for (int di = -1; di <= 1; di++) {
                for (int dj = 0; dj <= 1; dj++) {
                    sum += backup[i + di][j + dj];
                }
            }
            m[i][j] = sum / 9;
        }
    }
}

void main(String[] args) {
    // Тест 1: константная матрица обязана остаться константной
    int[][] flat = new int[5][5];
    for (int[] row : flat) Arrays.fill(row, 5);
    blur(flat);
    printMatrix(flat);

    // Тест 2: одиночная яркая точка
    int[][] spike = new int[5][5];
    spike[2][2] = 90;
    blur(spike);
    printMatrix(spike);   // ожидалось: внутренний квадрат 3x3 весь = 10

    // Большой прогон: seed = номер в журнале группы
    Random rnd = new Random(17);
    int[][] big = new int[1000][1000];
    for (int i = 0; i < big.length; i++)
        for (int j = 0; j < big[i].length; j++)
            big[i][j] = rnd.nextInt(10_000);
    blur(big);

    int min = Integer.MAX_VALUE;
    for (int[] row : big)
        for (int v : row) min = Math.min(min, v);
    System.out.println("min = " + min);   // яркость не может быть < 0!
}