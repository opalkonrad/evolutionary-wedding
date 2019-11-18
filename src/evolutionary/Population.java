package evolutionary;

import java.util.ArrayList;

public class Population {
    private ArrayList<Individual> population;

    public Population() {
        population = new ArrayList<>();
    }

    public Population(int count, int dim, int xMin, int xMax, int sigmaMax){
        population = new ArrayList<>();
        for(int i=0; i<count; i++){
            this.addToPopulation(new Individual(dim, xMin, xMax, sigmaMax));
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
    public void evolution(boolean wedding) {

    }



    public ArrayList<Individual> getPopulation() {
        return population;
    }

}
