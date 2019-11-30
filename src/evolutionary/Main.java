package evolutionary;

import java.io.IOException;

import static evolutionary.Test.*;

public class Main {

    public static void main(String[] args) {
//        Evolution evolution = new Evolution(10000, 15000, 10, 23, 100, 0.2);
//        //Evolution evolution = new Evolution(3, 5, 1, 23, 4, true, 1);
//        evolution.showPopulation(false);
//        evolution.performEvolution();
//        evolution.showPopulation(false);

        try {
            performTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
