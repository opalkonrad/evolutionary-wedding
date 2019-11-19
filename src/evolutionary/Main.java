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



        Population population1 = new Population(10000, 5, -100, 100, 10, 6);
        population1.showPopulation(false);
        Population population = population1;
        for(int i=0; i<1000; i++){
            population = population.performEvolution(15000, true, 0.6);
        }

        population.showPopulation(true);


//        Population population1 = new Population(10000, 1, -100, 100, 10, 0);
//        population1.showPopulation(false);
//        for(int i=0; i<100; i++){
//                population1.performEvolution(15000, true, 1);
//        }
//
//        population1.showPopulation(false);


    }
}
