package evolutionary;

public class Evolution {
    private int lambda;
    private int functionNumber;
    private boolean isWedding;

    public Evolution(int lambda, int functionNumber){
        this.lambda = lambda;
        this.functionNumber = functionNumber;
        this.isWedding = isWedding;
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
    public Population performEvolution(Population population) {
        if(isWedding){
            population.performWedding();
        }
        Population childrenPopulation = this.getChildrenPopulation(lambda); //randomly generate lambda individuals using roulette wheel
        childrenPopulation.performMutations();
        childrenPopulation.countObjectiveFunctions();

    }

    void countObjectiveFunction(Individual individual){
        individual.setFunctionValue();
    }
}
