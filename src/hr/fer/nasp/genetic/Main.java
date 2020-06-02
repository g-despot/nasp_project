package hr.fer.nasp.genetic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hr.fer.nasp.genetic.operations.*;

public class Main {

	public static void test(String[] args) throws IOException {
		int[][] cities;
		int numberOfCities;

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Choose option 'random' or input file name: ");
		String command = reader.readLine();

		if (command.equals("random")) {
			System.out.println("Number of cities: ");
			numberOfCities = Integer.parseInt(reader.readLine());
			cities = Utils.populateRandomCities(numberOfCities);

		} else {
			cities = Utils.readMatrixFromFile("files/" + command);
			numberOfCities = cities.length;
		}

		Utils.printCities(cities, numberOfCities);

		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(numberOfCities, cities, SelectionType.ROULETTEWHEEL,
				MutationType.SWAP, CrossoverType.CYCLE, 0);
		Chromosome result = geneticAlgorithm.evolve();
		System.out.println(result.cityOrder);
		System.out.println(result.fitness);
	}

	public static void main(String[] args) throws IOException {
		int[][] cities;
		int numberOfCities = 7;

		/*cities = Utils.populateRandomCities(numberOfCities);
		System.out.println("Matrix of distance between cities: ");
		Utils.printCities(cities, numberOfCities);*/
		cities = Utils.readMatrixFromFile("files/input1");
		numberOfCities = cities.length;
		System.out.println("Matrix of distance between cities: ");
		Utils.printCities(cities, numberOfCities);
		
		long startTime;
		long endTime;
		long durationInNano;
		long durationInMillis = 0;
		long totalTime = 0;

		SelectionType selectionType = SelectionType.ROULETTEWHEEL;
		CrossoverType crossoverType;
		MutationType mutationType;

		String mutation;
		String crossover;

		int totalFitness = 0;
		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				crossoverType = CrossoverType.PMXONEPOINT;
			} else if (i == 1) {
				crossoverType = CrossoverType.PMXTWOPOINT;
			} else {
				crossoverType = CrossoverType.CYCLE;
			}
			for (int j = 0; j < 3; j++) {
				if (j == 0) {
					mutationType = MutationType.SWAP;
				} else if (j == 1) {
					mutationType = MutationType.SCRAMBLE;
				} else {
					mutationType = MutationType.INVERSION;
				}
				List<Integer> fitnessList = new ArrayList<>();
				Chromosome result = null;
				for (int k = 0; k < 5; k++) {
					startTime = System.nanoTime();

					GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(numberOfCities, cities, selectionType,
							mutationType, crossoverType, 0);
					result = geneticAlgorithm.evolve();

					endTime = System.nanoTime();
					durationInNano = (endTime - startTime);
					durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNano);
					totalTime += durationInMillis;

					totalFitness += result.fitness;
					fitnessList.add(result.fitness);
				}

				mutation = "Mutation: " + mutationType;
				crossover = "Crossover: " + crossoverType;

				System.out.format("%-25s%-25s%-15s%-10f%-15s%-10s%-15s%-10s%-7s%-10f", mutation, crossover,
						"Average fitness: ", totalFitness / 5., "   Best fitness: ", Collections.min(fitnessList),
						"   Number chromosomes with best fitness: ", Collections.frequency(fitnessList, Collections.min(fitnessList)),
						"Time: ", totalTime / 5.);
				System.out.println();

				totalTime = 0;
				totalFitness = 0;
			}
			System.out.println();
		}
	}
}
