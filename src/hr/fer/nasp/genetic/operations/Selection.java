package hr.fer.nasp.genetic.operations;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import hr.fer.nasp.genetic.Chromosome;
import hr.fer.nasp.genetic.Utils;

public class Selection {

	public static Chromosome rouletteWheelSelection(List<Chromosome> population, int size) {
		Random random = new Random();
		int totalFitness = 0;

		for (Chromosome chromosome : population) {
			totalFitness += chromosome.fitness;
		}

		float inverted = (float) 1 / random.nextInt(totalFitness);
		float sum = 0;

		for (Chromosome chromosome : population) {
			sum += 1. / chromosome.fitness;
			if (sum >= inverted) {
				return chromosome;
			}
		}
		return population.get(random.nextInt(size));
	}

	public static Chromosome tournamentSelection(List<Chromosome> population, int tournamentSize) {
		List<Chromosome> selected = Utils.randomElements(population, tournamentSize);
		return Collections.min(selected);
	}
}
