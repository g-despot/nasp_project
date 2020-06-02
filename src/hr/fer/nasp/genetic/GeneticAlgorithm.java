package hr.fer.nasp.genetic;

import java.util.*;

import hr.fer.nasp.genetic.operations.*;

public class GeneticAlgorithm {
	public int[][] cities;
	public int maximumPopulation;
	public int chromosomeSize;
	public int numberOfCities;
	public int maximumReproduction;
	public int maximumIterations;
	public float mutationRate;
	public int tournamentSize;
	public int startingCity;

	public SelectionType selectionType;
	public CrossoverType crossoverType;
	public MutationType mutationType;

	public GeneticAlgorithm(int numberOfCities, int[][] cities, SelectionType selectionType, MutationType mutationType,
			CrossoverType crossoverType, int startingCity) {
		this.numberOfCities = numberOfCities;
		this.chromosomeSize = numberOfCities - 1;
		this.cities = cities;

		this.selectionType = selectionType;
		this.mutationType = mutationType;
		this.crossoverType = crossoverType;

		this.startingCity = startingCity;

		maximumPopulation = 3000;
		maximumReproduction = 300;
		maximumIterations = 1000;
		mutationRate = 0.05f;

		tournamentSize = 30;
	}

	public Chromosome evolve() {
		List<Chromosome> population = new ArrayList<>();
		for (int i = 0; i < maximumPopulation; i++) {
			population.add(new Chromosome(numberOfCities, cities, startingCity));
		}
		Chromosome bestChromosome = population.get(0);

		for (int i = 0; i < maximumIterations; i++) {
			List<Chromosome> selected = new ArrayList<>();
			for (int j = 0; j < maximumReproduction; j++) {
				if (selectionType == SelectionType.ROULETTEWHEEL) {
					selected.add(Selection.rouletteWheelSelection(population, maximumPopulation));
				} else if (selectionType == SelectionType.TOURNAMENT) {
					selected.add(Selection.tournamentSelection(population, tournamentSize));
				}
			}
			population = createGeneration(selected);
			bestChromosome = Collections.min(population);
		}
		return bestChromosome;
	}

	public List<Chromosome> createGeneration(List<Chromosome> population) {
		List<Chromosome> generation = new ArrayList<>();
		int currentGenerationSize = 0;

		while (currentGenerationSize < maximumPopulation) {
			List<Chromosome> parents = Utils.randomElements(population, 2);
			List<Chromosome> children = new ArrayList<>();

			if (crossoverType == CrossoverType.PMXONEPOINT) {
				children = Crossover.pmxOnePointCrossover(parents, this);
			} else if (crossoverType == CrossoverType.PMXTWOPOINT) {
				children = Crossover.pmxTwoPointCrossover(parents, this);
			} else if (crossoverType == CrossoverType.CYCLE) {
				children = Crossover.cycleCrossover(parents, this);
			}

			if (mutationType == MutationType.SWAP) {
				children.set(0, Mutation.swapMutation(children.get(0), this));
				children.set(1, Mutation.swapMutation(children.get(1), this));
			} else if (mutationType == MutationType.SCRAMBLE) {
				children.set(0, Mutation.scrambleMutation(children.get(0), this));
				children.set(1, Mutation.scrambleMutation(children.get(1), this));
			} else if (mutationType != MutationType.INVERSION) {
				children.set(0, Mutation.inversionMutation(children.get(0), this));
				children.set(1, Mutation.inversionMutation(children.get(1), this));
			}

			generation.addAll(children);
			currentGenerationSize += 2;
		}
		return generation;
	}
}
