package evolutionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population {
    private ArrayList<Individual> population;
    private Random rand = new Random();

    public Population() {
        population = new ArrayList<>();
    }

    public Population(int count, int dim, int xMin, int xMax, int sigmaMax, int funcNum){
        population = new ArrayList<>();
        for(int i=0; i<count; i++){
            this.addToPopulation(new Individual(dim, xMin, xMax, sigmaMax, funcNum));
        }
    }

    public Population(ArrayList<Individual> individuals) {
        population = new ArrayList<>(individuals);
    }

    public void addToPopulation(Individual individual) {
        population.add(individual);
    }

    public void removeFromPopulation(Individual individual) {
        population.remove(individual);
    }


    public void showPopulation() {
        for(Individual i : population) {
            System.out.println("x     " + i.getX() + "\nsigma " + i.getSigma() + "\n");
        }
    }

    void performWedding() {
        Collections.shuffle(population);
        for(int i = 0; i < getSize(); i++) {
            if(population.get(i).isMarried()) {
                continue;
            }

            Individual first = population.get(i);

            while(i != getSize() && population.get(++i).isMarried()) { }

            if(i != getSize()) {
                first.marry(population.get(i));
            }
        }
    }

    Population createChildrenPopulation(int count, double mutationProbability) throws CloneNotSupportedException {
        double functionValueSum = 0;

        //count sum of function values
        for(Individual individual: population){
            functionValueSum += individual.getFunctionValue();
        }

        //generate count random numbers from 0 to functionValueSum
        ArrayList<Double>randoms = new ArrayList<>(count);
        for(int i=0; i<count; ++i){
            randoms.set(i, rand.nextDouble()*functionValueSum);
        }

        Collections.sort(randoms);

        Population pop = new Population();

        //generate new population using roulette wheel
        double wheelPointer = 0;
        int randomsIndex = 0;
        for(Individual individual: population){
            wheelPointer += individual.getFunctionValue();
            //until random will not exceed wheelPointer, individual is cloned to child population
            while(randoms.get(randomsIndex++) <= wheelPointer){
                pop.addToPopulation((Individual)individual.clone());
            }
        }

        pop.performMutations(mutationProbability);

        return pop;
    }

    void performMutations(double mutationProbability) {
        double tau = 1 / (Math.sqrt(2 * getDimension()));
        double tauPrim = 1 / (Math.sqrt(2 * Math.sqrt(getDimension())));

        for(Individual individual : population) {
            // Mutate only some of the individuals in population
            if(rand.nextDouble() > mutationProbability) {
                continue;
            }

            ArrayList<Double> newX = new ArrayList<>();
            ArrayList<Double> newSigma = new ArrayList<>();

            double normDistr = rand.nextGaussian();

            for(double sigma : individual.getSigma()) {
                newSigma.add(sigma * Math.exp(tau * normDistr + tauPrim * rand.nextGaussian()));
            }

            int counter = 0;
            for(double x : individual.getX()) {
                newX.add(x + (newSigma.get(counter) * rand.nextGaussian()));
                counter++;
            }

            individual.setX(newX);
            individual.setSigma(newSigma);
        }
    }

    /**
     * 1. Stworzenie losowej populacji liczb rzeczywistych (mi osobników) // zrobione w konstruktorze
     * 2. Obliczenie funkcji przystosowania
     * 3. Połączenie w pary i wyliczenie nowych funkcji przystosowania
     * 4. Nowa populacja za pomocą koła ruletki (lambda osobników)
     * 5. Mutacje na podstawie sigmy z rozkładem normalnym
     * 6. Obliczenie funkcji przystosowania i wybór mi najlepszych osobników
     * 7. Jeżeli nie warunek stopu, to powróć do punktu 3.
     *
     */
    public Population performEvolution(int lambda, boolean isWedding, double mutationProbability) throws CloneNotSupportedException {
        if(isWedding) {
            performWedding();
        }

        // Randomly generate lambda individuals using roulette wheel
        Population childrenPopulation = createChildrenPopulation(lambda, mutationProbability);

        //create one references population
        Population population = new Population();
        //TODO
        return population;
    }

    public int getSize(){
        return population.size();
    }


    public ArrayList<Individual> getPopulation() {
        return population;
    }

    public int getDimension() {
        return population.get(0).getDimension();
    }

}
