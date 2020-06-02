package hr.fer.nasp.genetic.operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import hr.fer.nasp.genetic.Chromosome;
import hr.fer.nasp.genetic.GeneticAlgorithm;
import hr.fer.nasp.genetic.Utils;

public class Crossover {

	public static List<Chromosome> pmxOnePointCrossover(List<Chromosome> parents, GeneticAlgorithm geneticAlgorithm) {
		Random random = new Random();
		int breakpoint = random.nextInt(geneticAlgorithm.chromosomeSize);

		List<Chromosome> children = new ArrayList<>();
		List<Integer> parentOne = new ArrayList<>(parents.get(0).cityOrder);
		List<Integer> parentTwo = new ArrayList<>(parents.get(1).cityOrder);

		for (int i = 0; i < breakpoint; i++) {
			int temp = parentTwo.get(i);
			Collections.swap(parentOne, parentOne.indexOf(temp), i);
		}

		children.add(new Chromosome(parentOne, geneticAlgorithm.numberOfCities, geneticAlgorithm.cities,
				geneticAlgorithm.startingCity));

		parentOne = parents.get(0).cityOrder;
		for (int i = 0; i < breakpoint; i++) {
			int temp = parentOne.get(i);
			Collections.swap(parentTwo, parentTwo.indexOf(temp), i);
		}

		children.add(new Chromosome(parentTwo, geneticAlgorithm.numberOfCities, geneticAlgorithm.cities,
				geneticAlgorithm.startingCity));

		return children;
	}

	public static List<Chromosome> pmxTwoPointCrossover(List<Chromosome> parents, GeneticAlgorithm geneticAlgorithm) {
		Random random = new Random();
		int breakpointOne = 0;
		int breakpointTwo = 0;

		while (breakpointOne == breakpointTwo) {
			breakpointOne = random.nextInt(geneticAlgorithm.chromosomeSize);
			breakpointTwo = random.nextInt(geneticAlgorithm.chromosomeSize);
		}

		int[] swapTemp = new int[2];
		swapTemp = Utils.swapValues(breakpointOne, breakpointTwo);
		breakpointOne = swapTemp[0];
		breakpointTwo = swapTemp[1];

		List<Chromosome> children = new ArrayList<>();
		List<Integer> parentOne = new ArrayList<>(parents.get(0).cityOrder);
		List<Integer> parentTwo = new ArrayList<>(parents.get(1).cityOrder);

		for (int i = 0; i < breakpointTwo; i++) {
			if (i >= breakpointOne) {
				int temp = parentTwo.get(i);
				Collections.swap(parentOne, parentOne.indexOf(temp), i);
			}
		}

		children.add(new Chromosome(parentOne, geneticAlgorithm.numberOfCities, geneticAlgorithm.cities,
				geneticAlgorithm.startingCity));

		parentOne = parents.get(0).cityOrder;
		for (int i = 0; i < breakpointTwo; i++) {
			if (i >= breakpointOne) {
				int temp = parentOne.get(i);
				Collections.swap(parentTwo, parentTwo.indexOf(temp), i);
			}
		}

		children.add(new Chromosome(parentTwo, geneticAlgorithm.numberOfCities, geneticAlgorithm.cities,
				geneticAlgorithm.startingCity));

		return children;
	}

	public static List<Chromosome> cycleCrossover(List<Chromosome> parents, GeneticAlgorithm geneticAlgorithm) {
		List<Chromosome> children = new ArrayList<>();
		List<Integer> parentOne = new ArrayList<>(parents.get(0).cityOrder);
		List<Integer> parentTwo = new ArrayList<>(parents.get(1).cityOrder);
		List<Integer> safe = new ArrayList<>();

		int current = 0;
		do {
			safe.add(current);
			current = parentOne.indexOf(parentTwo.get(current));
		} while (current != 0);

		for (int i = 0; i < parentOne.size(); i++) {
			if (!safe.contains(i)) {
				parentOne.set(i, parentTwo.get(i));
			}
		}

		children.add(new Chromosome(parentOne, geneticAlgorithm.numberOfCities, geneticAlgorithm.cities,
				geneticAlgorithm.startingCity));

		do {
			safe.add(current);
			current = parentTwo.indexOf(parentOne.get(current));
		} while (current != 0);

		for (int i = 0; i < parentTwo.size(); i++) {
			if (!safe.contains(i)) {
				parentTwo.set(i, parentOne.get(i));
			}
		}

		children.add(new Chromosome(parentTwo, geneticAlgorithm.numberOfCities, geneticAlgorithm.cities,
				geneticAlgorithm.startingCity));

		return children;
	}
}
