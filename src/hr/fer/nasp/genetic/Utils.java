package hr.fer.nasp.genetic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Utils {

	public static <E> List<E> randomElements(List<E> list, int n) {
		Random r = new Random();
		int length = list.size();

		if (length < n)
			return null;

		for (int i = length - 1; i >= length - n; --i) {
			Collections.swap(list, i, r.nextInt(i + 1));
		}

		return list.subList(length - n, length);
	}

	public static int[][] readMatrixFromFile(String fileLocation) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(fileLocation));

		int N = sc.nextInt();
		int[][] m = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				m[i][j] = Integer.valueOf(sc.next());
			}
		}
		sc.close();
		return m;
	}

	public static void printCities(int[][] cities, int numberOfCities) {
		for (int i = 0; i < numberOfCities; i++) {
			for (int j = 0; j < numberOfCities; j++) {
				System.out.print(cities[i][j]);

				if (cities[i][j] / 10 == 0)
					System.out.print("  ");
				else
					System.out.print(' ');
			}
			System.out.println("");
		}
	}

	public static int[][] populateRandomCities(int numberOfCities) {
		int[][] cities = new int[numberOfCities][numberOfCities];

		for (int i = 0; i < numberOfCities; i++) {
			for (int j = 0; j <= i; j++) {
				Random rand = new Random();

				if (i != j) {
					cities[i][j] = rand.nextInt(100);
					cities[j][i] = cities[i][j];
				} else {
					cities[i][j] = 0;
				}
			}
		}
		return cities;
	}

	public static int[] swapValues(int a, int b) {
		int[] values = new int[2];
		if (a < b) {
			values[0] = a;
			values[1] = b;
		} else {
			values[0] = b;
			values[1] = a;
		}
		return values;
	}
}
