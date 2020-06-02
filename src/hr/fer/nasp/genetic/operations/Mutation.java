package hr.fer.nasp.genetic.operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import hr.fer.nasp.genetic.Chromosome;
import hr.fer.nasp.genetic.GeneticAlgorithm;
import hr.fer.nasp.genetic.Utils;

public class Mutation {
	public static Chromosome swapMutation(Chromosome chromosome, GeneticAlgorithm geneticAlgorithm) {
		Random random = new Random();
		float probability = random.nextFloat();

		if (probability < geneticAlgorithm.mutationRate) {
			List<Integer> genome = chromosome.cityOrder;
			Collections.swap(genome, random.nextInt(geneticAlgorithm.chromosomeSize),
					random.nextInt(geneticAlgorithm.chromosomeSize));

			return new Chromosome(genome, geneticAlgorithm.numberOfCities, geneticAlgorithm.cities,
					geneticAlgorithm.startingCity);
		}
		return chromosome;
	}

	public static Chromosome scrambleMutation(Chromosome chromosome, GeneticAlgorithm geneticAlgorithm) {
		Random random = new Random();
		float probability = random.nextFloat();

		if (probability < geneticAlgorithm.mutationRate) {
			List<Integer> genome = chromosome.cityOrder;
			int genomeSize = genome.size();

			int breakpointOne = random.nextInt(geneticAlgorithm.chromosomeSize);
			int breakpointTwo = random.nextInt(geneticAlgorithm.chromosomeSize);

			int[] swapTemp = new int[2];
			swapTemp = Utils.swapValues(breakpointOne, breakpointTwo);
			breakpointOne = swapTemp[0];
			breakpointTwo = swapTemp[1];

			List<Integer> listOne = new ArrayList<>();
			List<Integer> listTwo = new ArrayList<>();
			List<Integer> listThree = new ArrayList<>();
			List<Integer> newGenome = new ArrayList<>();

			if (breakpointOne != breakpointTwo) {
				listOne = genome.subList(0, breakpointOne);
				listTwo = genome.subList(breakpointOne, breakpointTwo);
				listThree = genome.subList(breakpointTwo, genomeSize);

				Collections.shuffle(listTwo, random);

				newGenome.addAll(listOne);
				newGenome.addAll(listTwo);
				newGenome.addAll(listThree);
			} else {
				listOne = genome.subList(0, breakpointOne);
				listTwo = genome.subList(breakpointOne, genomeSize);

				Collections.shuffle(listTwo, random);

				newGenome.addAll(listOne);
				newGenome.addAll(listTwo);
			}
			return new Chromosome(newGenome, geneticAlgorithm.numberOfCities, geneticAlgorithm.cities,
					geneticAlgorithm.startingCity);
		}
		return chromosome;
	}

	public static Chromosome inversionMutation(Chromosome chromosome, GeneticAlgorithm geneticAlgorithm) {
		Random random = new Random();
		float probability = random.nextFloat();
		if (probability < geneticAlgorithm.mutationRate) {
			List<Integer> genome = chromosome.cityOrder;
			int genomeSize = genome.size();

			int breakpointOne = random.nextInt(geneticAlgorithm.chromosomeSize);
			int breakpointTwo = random.nextInt(geneticAlgorithm.chromosomeSize);

			int[] swapTemp = new int[2];
			swapTemp = Utils.swapValues(breakpointOne, breakpointTwo);
			breakpointOne = swapTemp[0];
			breakpointTwo = swapTemp[1];

			List<Integer> listOne = new ArrayList<>();
			List<Integer> listTwo = new ArrayList<>();
			List<Integer> listThree = new ArrayList<>();
			List<Integer> newGenome = new ArrayList<>();

			if (breakpointOne != breakpointTwo) {
				listOne = genome.subList(0, breakpointOne);
				listTwo = genome.subList(breakpointOne, breakpointTwo);
				listThree = genome.subList(breakpointTwo, genomeSize);

				Collections.reverse(listTwo);

				newGenome.addAll(listOne);
				newGenome.addAll(listTwo);
				newGenome.addAll(listThree);
			} else {
				listOne = genome.subList(0, breakpointOne);
				listTwo = genome.subList(breakpointOne, genomeSize);

				Collections.reverse(listTwo);

				newGenome.addAll(listOne);
				newGenome.addAll(listTwo);
			}
			return new Chromosome(newGenome, geneticAlgorithm.numberOfCities, geneticAlgorithm.cities,
					geneticAlgorithm.startingCity);
		}
		return chromosome;
	}
}
