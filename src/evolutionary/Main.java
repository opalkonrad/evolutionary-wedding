package evolutionary;

public class Main {

    public static void main(String[] args) {
//        Population pWedding = new Population(2, 1, -50, 50, 5, 0);
//        pWedding.showPopulation();
//        pWedding.performWedding();
//        pWedding.showPopulation();

//        Population pChildren = new Population(2, 1, -50, 50, 5, 0);
//        pChildren.showPopulation();
//        try {
//            pChildren.performWedding();
//            pChildren.createChildrenPopulation(5, 0).showPopulation();
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }

//        Population population1 = new Population(3, 1, -10, 100, 10, 0);
//        Population ch = new Population(3, 1, -10, 100, 10, 0);
//        population1.showPopulation(true);
//        ch.showPopulation(true);
//
//        population1.limitPopulation(ch).showPopulation(true);
        Evolution evolution = new Evolution(1000, 1500, 10, 23, 100, true, 0.6);
        //Evolution evolution = new Evolution(3, 5, 1, 23, 4, true, 1);
        evolution.showPopulation(true);
        evolution.performEvolution();
        evolution.showPopulation(true);
    }
}
