package evolutionary;

import java.util.ArrayList;

public class Population {
    private ArrayList<Individual> population;

    public Population() {
        population = new ArrayList<>();
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


    public ArrayList<Individual> getPopulation() {
        return population;
    }

}
