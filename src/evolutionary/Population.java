package evolutionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Population {
    private ArrayList<Individual> population;
    private Random rand = new Random();
    private Evolution evolution;

    public Population(Evolution evolution) {
        this.evolution = evolution;
        population = new ArrayList<>();
    }

    public Population(Evolution evolution, int count, int dim, int xMin, int xMax, int sigmaMax, int funcNum){
        this.evolution = evolution;
        population = new ArrayList<>();
        for(int i=0; i<count; i++){
            this.addToPopulation(new Individual(evolution, dim, xMin, xMax, sigmaMax, funcNum));
        }
    }

    public Population(Evolution evolution, ArrayList<Individual> individuals) {
        this.evolution = evolution;
        population = new ArrayList<>(individuals);
    }

    public void addToPopulation(Individual individual) {
        population.add(individual);
    }

    public void addToPopulation(ArrayList<Individual> individuals){
        population.addAll(individuals);
    }

    public void removeFromPopulation(Individual individual) {
        population.remove(individual);
    }

    public void removeFromPopulation(int newSize){
        for(int i=getSize()-1; i>=newSize; i--){
            population.remove(i);
        }
    }


    public void showPopulation(boolean detail) {
        double sum = 0;
        for(Individual i : population) {
            if(detail) {
                System.out.println("x     " + i.getX() + "\nsigma " + i.getSigma() + "\n" + i.getObjFuncVal() + "\n");
            }
            sum += i.getX().get(0);
        }
        System.out.println("srednia     " + sum/getSize() + "\n");

    }

    void performWedding() {
        Collections.shuffle(population);
        for(int i = 0; i < getSize(); i++) {
            if(population.get(i).isMarried()) {
                continue;
            }

            Individual first = population.get(i);

            while(++i != getSize() && population.get(i).isMarried()) { }

            if(i != getSize()) {
                first.marry(population.get(i));
            }
        }
    }

    Population createChildrenPopulation(int count, double mutationProbability) throws CloneNotSupportedException {
        double functionValueSum = 0;

        //count sum of function values
        for(Individual individual: population){
            functionValueSum += individual.getObjFuncVal();
        }

        //generate count random numbers from 0 to functionValueSum
        ArrayList<Double>randoms = new ArrayList<>();
        for(int i=0; i<count; ++i){
            randoms.add(rand.nextDouble()*functionValueSum);
        }

        Collections.sort(randoms);

        Population pop = new Population(evolution);

        //generate new population using roulette wheel
        double wheelPointer = 0;
        int randomsIndex = 0;
        for(Individual individual: population){
            wheelPointer += individual.getObjFuncVal();
            if(randomsIndex >= count){
                break;
            }
            //until random will not exceed wheelPointer, individual is cloned to child population
            while(randoms.get(randomsIndex) <= wheelPointer){
                pop.addToPopulation((Individual)individual.clone());
                if(++randomsIndex >= count){
                    break;
                }
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
            individual.updateObjFuncVal();
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
    public Population performEvolution(int lambda, boolean isWedding, double mutationProbability) {
        if(isWedding) {
            performWedding();
        }

        Population childrenPopulation = null;
        try {
            // Randomly generate lambda individuals using roulette wheel
            childrenPopulation = createChildrenPopulation(lambda, mutationProbability);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        // Limit population to original size
        Population finalPopulation = limitPopulation(childrenPopulation);

        return finalPopulation;
    }

    /**
     * From combined original population and child population we choose new population by limiting it to original size
     * (choosing individuals with best objective function).
     * @param childrenPopulation
     * @return
     */
    public Population limitPopulation(Population childrenPopulation){
        //create one references population
        Population allPopulation = new Population(evolution, population);
        allPopulation.addToPopulation( childrenPopulation.getPopulation() );

        //sort population by objective function value descending
        allPopulation.population.sort(new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if(o2.getObjFuncVal() > o1.getObjFuncVal()){
                    return -1;
                }
                else if(o2.getObjFuncVal() == o1.getObjFuncVal()){
                    return 0;
                }
                else return 1;
            }
        });

        allPopulation.removeFromPopulation(getSize());

        return allPopulation;
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
