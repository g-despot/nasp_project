package hr.fer.nasp.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chromosome implements Comparable<Chromosome> {
	public List<Integer> cityOrder;
	public int[][] cities;
	public int numberOfCities = 0;
	public int startCity;
	public int fitness;

	public Chromosome(int numberOfCities, int[][] cities, int startCity) {
		this.numberOfCities = numberOfCities;
		this.cityOrder = randomChromosome();
		this.cities = cities;
		this.startCity = startCity;
		this.fitness = this.calculateFitness();
	}

	public Chromosome(List<Integer> cityOrder, int numberOfCities, int[][] cities, int startingCity) {
		this.cityOrder = cityOrder;
		this.cities = cities;
		this.startCity = startingCity;
		this.numberOfCities = numberOfCities;
		this.fitness = this.calculateFitness();
	}

	public int calculateFitness() {
		int fitness = 0;
		int currentCity = startCity;
		for (int gene : cityOrder) {
			fitness += cities[currentCity][gene];
			currentCity = gene;
		}
		fitness += cities[cityOrder.get(numberOfCities - 2)][startCity];
		return fitness;
	}

	private List<Integer> randomChromosome() {
		List<Integer> randomCityOrder = new ArrayList<>();

		for (int i = 0; i < numberOfCities; i++) {
			if (i != startCity)
				randomCityOrder.add(i);
		}
		Collections.shuffle(randomCityOrder);

		return randomCityOrder;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public int compareTo(Chromosome chromosome) {
		if (this.fitness > chromosome.fitness)
			return 1;
		else if (this.fitness < chromosome.fitness)
			return -1;
		else
			return 0;
	}
}
