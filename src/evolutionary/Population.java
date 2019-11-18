package evolutionary;

import java.util.ArrayList;

public class Population {
    private ArrayList<Individual> population;
    private Evolution evolution;

    public Population() {
        population = new ArrayList<>();
    }

    public Population(int count, int dim, int xMin, int xMax, int sigmaMax){
        population = new ArrayList<>();
        for(int i=0; i<count; i++){
            this.addToPopulation(new Individual(dim, xMin, xMax, sigmaMax));
        }
        countObjectiveFunctions();
    }

    public Population(ArrayList<Individual> individuals) {
        population = new ArrayList<>(individuals);
        countObjectiveFunctions();
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

    void countObjectiveFunctions(){
        for(Individual i: population){
            evolution.countObjectiveFunction(i);
        }
    }

    void performWedding(){
        //TODO
    }

    Population getChildrenPopulation(int count){
        //TODO
    }

    void performMutations(){
        //TODO
    }



    public ArrayList<Individual> getPopulation() {
        return population;
    }

}
