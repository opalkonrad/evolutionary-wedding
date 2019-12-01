package evolutionary;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Performs testing on evolutionary wedding.
 *
 * @author Konrad Opalinski
 */
public class Test {

    /**
     * Performs tests with and without wedding and different mutation probability. It prints results to file.
     */
    public static void performTest() throws FileNotFoundException {
        System.out.println("Welcome to evolutionary-wedding. We have prepared some tests for you.");
        System.out.println("You will find results in test_info.txt file after program finishes (we'll warn you).");
        System.out.println("Now it's coffee time :)");
        System.out.println("Doing coffee...");

        PrintStream file = new PrintStream(new FileOutputStream("test_info.txt"));
        PrintStream originalPrintStream = System.out;
        System.setOut(file);

        System.out.println("First we will initialize 25 populations for functions: Weierstrass, Composition Function 1 and 2 (see documentation).");
        System.out.println("Algorithm mi + lambda will be executed on every population 75 times.");
        System.out.println("\nParameters:\n- mi = 1000,\n- lambda = 1500,\n- mutation probability = 0.1 or 0.3,");
        System.out.println("- dimension = 8,\n- x ~ U(0,100),\n- sigma ~ U(0,10),");
        System.out.println("* additional parameters for Composition Function 1 and 2 (same as ones in documentation).");

        System.out.println("\n\n##### SUM UP SECTION #####");

        // List of evolutions
        ArrayList<Evolution> evolutionsF6 = new ArrayList<>();
        ArrayList<Evolution> evolutionsF23 = new ArrayList<>();
        ArrayList<Evolution> evolutionsF27 = new ArrayList<>();
        ArrayList<Evolution> evolutionsF6DiffMutProb = new ArrayList<>();
        ArrayList<Evolution> evolutionsF23DiffMutProb = new ArrayList<>();
        ArrayList<Evolution> evolutionsF27DiffMutProb = new ArrayList<>();


        double info[];

        // Create evolutions
        for (int i = 0; i < 25; ++i) {
            evolutionsF6.add(new Evolution(1000, 1500, 8, 6, 75, 0.1));
            evolutionsF23.add(new Evolution(1000, 1500, 8, 23, 75, 0.1));
//            evolutionsF27.add(new Evolution(1000, 1500, 8, 27, 75, 0.1));
            evolutionsF6DiffMutProb.add(new Evolution(1000, 1500, 8, 6, 75, 0.3));
            evolutionsF23DiffMutProb.add(new Evolution(1000, 1500, 8, 23, 75, 0.3));
//            evolutionsF27DiffMutProb.add(new Evolution(1000, 1500, 8, 27, 75, 0.3));
        }


        // Perform evolutions for every population (function 6)
        for (Evolution e : evolutionsF6) {
            e.performEvolution();
        }

        info = findSpecificIndividuals(evolutionsF6);
        showInfo(info, "Weierstrass Function (Mutation Probability = 0.1)");

        // Perform evolutions for every population (function 6)
        for (Evolution e : evolutionsF6DiffMutProb) {
            e.performEvolution();
        }

        info = findSpecificIndividuals(evolutionsF6DiffMutProb);
        showInfo(info, "Weierstrass Function (Mutation Probability = 0.3)");


        // Perform evolutions for every population (function 23)
        for (Evolution e : evolutionsF23) {
            e.performEvolution();
        }

        info = findSpecificIndividuals(evolutionsF23);
        showInfo(info, "Composition Function 1 (Mutation Probability = 0.1)");

        // Perform evolutions for every population (function 23)
        for (Evolution e : evolutionsF23DiffMutProb) {
            e.performEvolution();
        }

        info = findSpecificIndividuals(evolutionsF23DiffMutProb);
        showInfo(info, "Composition Function 1 (Mutation Probability = 0.3)");


//        // Perform evolutions for every population (function 27)
//        for (Evolution e : evolutionsF27) {
//            e.performEvolution();
//        }
//
//        info = findSpecificIndividuals(evolutionsF27);
//        showInfo(info, "Composition Function 2 (Mutation Probability = 0.1)");
//
//        // Perform evolutions for every population (function 27)
//        for (Evolution e : evolutionsF27DiffMutProb) {
//            e.performEvolution();
//        }
//
//        info = findSpecificIndividuals(evolutionsF27DiffMutProb);
//        showInfo(info, "Composition Function 2 (Mutation Probability = 0.3)");


        System.out.println("\n\n##### DETAILED SECTION #####");
        System.out.println("Additional information about one of populations and their three best individuals:\n");

        System.out.println("# Weierstrass Function (Mutation Probability = 0.1)\n");
        evolutionsF6.get(0).showPopulation(false);

        System.out.println("# Weierstrass Function (Mutation Probability = 0.3)\n");
        evolutionsF6DiffMutProb.get(0).showPopulation(false);

        System.out.println("# Composition Function 1 (Mutation Probability = 0.1)\n");
        evolutionsF23.get(0).showPopulation(false);

        System.out.println("# Composition Function 1 (Mutation Probability = 0.3)\n");
        evolutionsF23DiffMutProb.get(0).showPopulation(false);

//        System.out.println("Composition Function 2 (Mutation Probability = 0.1)\n");
//        System.out.println("-------------------------");
//        evolutionsF27.get(0).showPopulation(false);
//
//        System.out.println("Composition Function 2 (Mutation Probability = 0.3)\n");
//        System.out.println("-------------------------");
//        evolutionsF27DiffMutProb.get(0).showPopulation(false);


        // Print mug of coffee
        System.setOut(originalPrintStream);
        System.out.println();
        System.out.println("|        |");
        System.out.println("|~~~~~~~~|---");
        System.out.println("|~~~~~~~~|  |");
        System.out.println("|~~~~~~~~|  |");
        System.out.println("|________|---");
        System.out.println();
        System.out.println("Finished, see results in test_info.txt");

    }

    /**
     * Evaluate mean value of best individuals' objective function values, best and worst individual from all populations
     *
     * @param evo List of evolutions
     * @return Returns array containing information about evolutions
     */
    public static double[] findSpecificIndividuals(ArrayList<Evolution> evo) {
        /*
        0 - meanBestInitial
        1 - meanBestWithWedding
        2 - meanBestWithoutWedding
        3 - bestIndividualInitial
        4 - worstIndividualInitial
        5 - bestIndividualWithWedding
        6 - worstIndividualWithWedding
        7 - bestIndividualWithoutWedding
        8 - worstIndividualWithoutWedding
        */
        double[] info = new double[]{0, 0, 0, 10e10, 0, 10e10, 0, 10e10, 0};

        // Evaluate mean value of best individuals' objective function values also best and worst individual from all populations
        for (Evolution e : evo) {
            info[0] += e.bestObjFuncValIndividual()[0];
            info[1] += e.bestObjFuncValIndividual()[1];
            info[2] += e.bestObjFuncValIndividual()[2];

            // Best individual in initial population
            if (e.bestObjFuncValIndividual()[0] < info[3]) {
                info[3] = e.bestObjFuncValIndividual()[0];
            }

            // Best individual in population with wedding
            if (e.bestObjFuncValIndividual()[1] < info[5]) {
                info[5] = e.bestObjFuncValIndividual()[1];
            }

            // Best individual in population without wedding
            if (e.bestObjFuncValIndividual()[2] < info[7]) {
                info[7] = e.bestObjFuncValIndividual()[2];
            }

            // Worst individual in initial population
            if (e.worstObjFuncValIndividual()[0] > info[4]) {
                info[4] = e.worstObjFuncValIndividual()[0];
            }

            // Worst individual in population with wedding
            if (e.worstObjFuncValIndividual()[1] > info[6]) {
                info[6] = e.worstObjFuncValIndividual()[1];
            }

            // Worst individual in population without wedding
            if (e.worstObjFuncValIndividual()[2] > info[8]) {
                info[8] = e.worstObjFuncValIndividual()[2];
            }
        }

        info[0] = info[0] / 25;
        info[1] = info[1] / 25;
        info[2] = info[2] / 25;

        return info;
    }

    /**
     * Shows information from findSpecificIndividuals function
     *
     * @param info     Array returned by findSpecificIndividuals function
     * @param funcName Name of the function
     */
    public static void showInfo(double[] info, String funcName) {
        System.out.println("\n" + funcName);
        System.out.println("-------------------------");
        System.out.println("Mean of the objective function values of the best individual of every population:");
        System.out.println("- Initial population = " + info[0]);
        System.out.println("- Population with wedding = " + info[1]);
        System.out.println("- Population without wedding = " + info[2]);
        System.out.println("\nBest and worst individuals of all populations:");
        System.out.println("- Best / Worst individual from initial populations = " + info[3] + " / " + info[4]);
        System.out.println("- Best / Worst individual from populations with wedding  = " + info[5] + " / " + info[6]);
        System.out.println("- Best / Worst individual from populations without wedding  = " + info[7] + " / " + info[8]);
    }

}
