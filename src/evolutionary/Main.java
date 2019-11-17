package evolutionary;

public class Main {

    public static void main(String[] args) {
        Population tmpPop = new Population();

        tmpPop.addToPopulation(new Individual(5, -100, 100, 10));
        tmpPop.addToPopulation(new Individual(5, -100, 100, 10));
        tmpPop.addToPopulation(new Individual(5, -100, 100, 10));
        tmpPop.addToPopulation(new Individual(5, -100, 100, 10));
        tmpPop.addToPopulation(new Individual(5, -100, 100, 10));

        tmpPop.showPopulation();
    }
}
