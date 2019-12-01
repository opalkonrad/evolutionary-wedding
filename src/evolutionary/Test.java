package evolutionary;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Performs testing on evolutionary wedding.
 * @author Konrad Opalinski
 */
public class Test {

    /**
     * Performs tests with and without wedding. It prints results to file.
     */
    public static void performTest() throws FileNotFoundException {
        System.out.println("Welcome to evolutionary-wedding. We have prepared some tests for you.");
        System.out.println("You will find results in test_info.md file after program finishes (we'll warn you).");
        System.out.println("Now it's coffee time :)");
        System.out.println("Doing coffee...");

        PrintStream file = new PrintStream(new FileOutputStream("test_info.md"));
        PrintStream originalPrintStream = System.out;
        System.setOut(file);

        System.out.println("First we will initialize 25 populations for functions: 6, 23, 27 (see documentation).");
        System.out.println("Algorithm mi + lambda will be executed on every population 75 times.");
        System.out.println("\nParameters:\n- mi = 2500,\n- lambda = 3500,\n- mutation probability = 0.1,");
        System.out.println("* additional parameters for function 23 (same as ones in documentation).");

        System.out.println("\n\n##### SUM UP SECTION #####");

        // List of evolutions
        ArrayList<Evolution> evolutionsF6 = new ArrayList<>();
        ArrayList<Evolution> evolutionsF23 = new ArrayList<>();
        ArrayList<Evolution> evolutionsF27 = new ArrayList<>();

        double meanBestInitial = 0;
        double meanBestWithWedding = 0;
        double meanBestWithoutWedding = 0;

        // Create evolutions
        for (int i = 0; i < 25; ++i) {
            evolutionsF6.add(new Evolution(2500, 3500, 8, 6, 75, 0.1));
            evolutionsF23.add(new Evolution(2500, 3500, 8, 23, 75, 0.1));
            evolutionsF27.add(new Evolution(2500, 3500, 8, 27, 75, 0.1));
        }

        // Perform evolutions for every population 100 times (function 6)
        for (Evolution e : evolutionsF6) {
            e.performEvolution();
        }

        // Evaluate mean value of best individuals' objective function values (function 6)
        for (Evolution e : evolutionsF6) {
            meanBestInitial += e.bestObjFuncValIndividual()[0];
            meanBestWithWedding += e.bestObjFuncValIndividual()[1];
            meanBestWithoutWedding += e.bestObjFuncValIndividual()[2];
        }

        meanBestInitial = meanBestInitial / 25;
        meanBestWithWedding = meanBestWithWedding / 25;
        meanBestWithoutWedding = meanBestWithoutWedding / 25;

        System.out.println("\nFunction 6");
        System.out.println("--------------------");
        System.out.println("Mean of the objective function values of the best individual of every population:");
        System.out.println("- Initial population = " + meanBestInitial);
        System.out.println("- Population with wedding = " + meanBestWithWedding);
        System.out.println("- Population without wedding = " + meanBestWithoutWedding);


        meanBestInitial = 0;
        meanBestWithWedding = 0;
        meanBestWithoutWedding = 0;

        // Perform evolutions for every population 100 times
        for (Evolution e : evolutionsF23) {
            e.performEvolution();
        }

        for (Evolution e : evolutionsF23) {
            meanBestInitial += e.bestObjFuncValIndividual()[0];
            meanBestWithWedding += e.bestObjFuncValIndividual()[1];
            meanBestWithoutWedding += e.bestObjFuncValIndividual()[2];
        }

        meanBestInitial = meanBestInitial / 25;
        meanBestWithWedding = meanBestWithWedding / 25;
        meanBestWithoutWedding = meanBestWithoutWedding / 25;

        System.out.println("\nFunction 23");
        System.out.println("--------------------");
        System.out.println("Mean of the objective functions value of the best individual of every population:");
        System.out.println("- Initial population = " + meanBestInitial);
        System.out.println("- Population with wedding = " + meanBestWithWedding);
        System.out.println("- Population without wedding = " + meanBestWithoutWedding);



        meanBestInitial = 0;
        meanBestWithWedding = 0;
        meanBestWithoutWedding = 0;

        // Perform evolutions for every population 100 times
        for (Evolution e : evolutionsF27) {
            e.performEvolution();
        }

        for (Evolution e : evolutionsF27) {
            meanBestInitial += e.bestObjFuncValIndividual()[0];
            meanBestWithWedding += e.bestObjFuncValIndividual()[1];
            meanBestWithoutWedding += e.bestObjFuncValIndividual()[2];
        }

        meanBestInitial = meanBestInitial / 25;
        meanBestWithWedding = meanBestWithWedding / 25;
        meanBestWithoutWedding = meanBestWithoutWedding / 25;

        System.out.println("\nFunction 27");
        System.out.println("--------------------");
        System.out.println("Mean of the objective functions value of the best individual of every population:");
        System.out.println("- Initial population = " + meanBestInitial);
        System.out.println("- Population with wedding = " + meanBestWithWedding);
        System.out.println("- Population without wedding = " + meanBestWithoutWedding);

        System.out.println("\n\n##### DETAILED SECTION #####");
        System.out.println("Additional information about every population:\n");

        int cntr = 0;

        // Show specific info about populations
        for (Evolution e : evolutionsF6) {
            System.out.println("--- Population " + (++cntr) + " (function 6)\n");
            e.showPopulation(false);
        }

        cntr = 0;

        for (Evolution e : evolutionsF23) {
            System.out.println("--- Population " + (++cntr) + " (function 23)\n");
            e.showPopulation(false);
        }

        // Print mug of coffee
        System.setOut(originalPrintStream);
        System.out.println();
        System.out.println("|        |");
        System.out.println("|~~~~~~~~|---");
        System.out.println("|~~~~~~~~|  |");
        System.out.println("|~~~~~~~~|  |");
        System.out.println("|________|---");
        System.out.println();
        System.out.println("Finished, see results in test_info.md");

    }

}
