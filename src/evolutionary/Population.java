package evolutionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public void addToPopulation(ArrayList<Individual> individuals){
        population.addAll(individuals);
    }

    public void removeFromPopulation(Individual individual) {
        population.remove(individual);
    }

    public void removeFromPopulation(int newSize){
        for(int i=getSize()-1; i>newSize; i--){
            population.remove(i);
        }
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

            while(++i != getSize() && population.get(i).isMarried()) { }

            if(i != getSize()) {
                first.marry(population.get(i));
            }
        }
    }

    Population createChildrenPopulation(int count) throws CloneNotSupportedException {
        double functionValueSum = 0;

        //count sum of function values
        for(Individual individual: population){
            functionValueSum += individual.getFunctionValue();
        }

        //generate count random numbers from 0 to functionValueSum
        ArrayList<Double>randoms = new ArrayList<>();
        for(int i=0; i<count; ++i){
            randoms.add(rand.nextDouble()*functionValueSum);
        }

        Collections.sort(randoms);

        Population pop = new Population();

        //generate new population using roulette wheel
        double wheelPointer = 0;
        int randomsIndex = 0;
        for(Individual individual: population){
            wheelPointer += individual.getFunctionValue();
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

        pop.performMutations();

        return pop;
    }

    void performMutations() {
        //TODO
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
    public Population performEvolution(int lambda, boolean isWedding) throws CloneNotSupportedException {
        if(isWedding) {
            performWedding();
        }

        // Randomly generate lambda individuals using roulette wheel
        Population childrenPopulation = createChildrenPopulation(lambda);

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
        Population allPopulation = new Population(population);
        allPopulation.addToPopulation(childrenPopulation.getPopulation());

        //sort population by objective function value descending
        allPopulation.population.sort(new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                return (int) (o2.getFunctionValue() - o1.getFunctionValue());
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

}
