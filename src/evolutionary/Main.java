package evolutionary;

public class Main {

    public static void main(String[] args) {
        Population population1 = new Population(3, 1, -100, 100, 10, 0);
        population1.showPopulation();
        try {
            population1.performEvolution(8, true);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        population1.showPopulation();
    }
}
