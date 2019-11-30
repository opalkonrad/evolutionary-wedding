package evolutionary;

public class Main {

    public static void main(String[] args) {
        Evolution evolution = new Evolution(5, 8, 10, 23, 100, true, 0.6);
        //Evolution evolution = new Evolution(3, 5, 1, 23, 4, true, 1);
        evolution.showPopulation(true);
        evolution.performEvolution();
        evolution.showPopulation(true);
    }
}
