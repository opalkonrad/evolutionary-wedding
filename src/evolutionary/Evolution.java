package evolutionary;

import java.util.Random;

/**
 * Represents evolution that is conducted on population n times.
 * It also includes many objective functions that can be used.
 */
public class Evolution {
    private Population population;
    private Population newPopulationWithWedding;
    private Population newPopulationWithoutWedding;
    private int childrenCount;
    private double[][] optimum;
    private boolean[] isWedding;
    private double mutationProbability;
    private int repeatNum;
    private int dim;
    private int N;
    private double[] sigma;
    private double[] lambda;
    private int[] g;
    private double[] bias;

    private Random rand = new Random();


    /*----- Constructors -----*/

    /**
     * @param mi                  Size of the initial population.
     * @param childrenCount       Size of children population, in mi + lambda that is lambda.
     * @param dim                 Dimension of x and sigma array.
     * @param funcNum             Number of objective function.
     * @param repeatNum           Number of evolutions.
     * @param mutationProbability Probability of performing mutations.
     */
    public Evolution(int mi, int childrenCount, int dim, int funcNum, int repeatNum, double mutationProbability) {
        this.dim = dim;
        this.childrenCount = childrenCount;
        this.isWedding = new boolean[]{true, false};
        this.mutationProbability = mutationProbability;
        this.repeatNum = repeatNum;

        switch (funcNum) {
            case 23:
                N = 5;
                sigma = new double[]{10, 20, 30, 40, 50};
                lambda = new double[]{1, 1e-6, 1e-26, 1e-6, 1e-6};
                g = new int[]{4, 1, 2, 3, 1};
                bias = new double[]{0, 100, 200, 300, 400};
                break;

            case 27:
                N = 5;
                sigma = new double[]{10, 10, 10, 20, 20};
                lambda = new double[]{10, 10, 2.5, 25, 1e-6};
                g = new int[]{12, 8, 9, 6, 1};
                bias = new double[]{0, 100, 200, 300, 400};
                break;

            default:
                N = 1;
                sigma = new double[]{};
                lambda = new double[]{};
                g = new int[]{};
                bias = new double[]{};
        }

        optimum = new double[N][dim];

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < dim; ++j) {
                // Random distribution from -80 to 80
                optimum[i][j] = rand.nextDouble() * 160 - 80;
            }
        }

        population = new Population(this, mi, dim, -100, 100, 10, funcNum);

        // Sort population at the beginning to show three best individuals
        population.getPopulation().sort((o1, o2) -> {
            if (o2.getObjFuncVal() > o1.getObjFuncVal()) {
                return -1;
            } else if (o2.getObjFuncVal() == o1.getObjFuncVal()) {
                return 0;
            } else return 1;
        });

        try {
            newPopulationWithWedding = (Population) population.clone();
            newPopulationWithoutWedding = (Population) population.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }


    /*----- Getters & setters -----*/

    public double[] getLambda() {
        return lambda;
    }

    public double[] getSigma() {
        return sigma;
    }

    public int getN() {
        return N;
    }

    public int[] getG() {
        return g;
    }

    public double[] getBias() {
        return bias;
    }

    /**
     * @param i Number of i function.
     * @param j Number of dimension.
     * @return
     */
    public double getOptimum(int i, int j) {
        if (i >= N || j >= dim) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return optimum[i][j];
    }


    /*----- Methods -----*/

    /**
     * Performs evolution on population n times.
     */
    public void performEvolution() {
        for (int i = 0; i < repeatNum; ++i) {
            newPopulationWithWedding = newPopulationWithWedding.performEvolution(childrenCount, isWedding[0], mutationProbability);
            newPopulationWithoutWedding = newPopulationWithoutWedding.performEvolution(childrenCount, isWedding[1], mutationProbability);
        }
    }

    /**
     * @param all Additional parameter to show x and sigma arrays of all population.
     */
    public void showPopulation(boolean all) {
        System.out.println("TOP 3 - Initial population");
        System.out.println("--------------------------------------------------");
        population.showPopulation(all);

        System.out.println("");
        System.out.println("TOP 3 - Population with wedding");
        System.out.println("--------------------------------------------------");
        newPopulationWithWedding.showPopulation(all);

        System.out.println("");
        System.out.println("TOP 3 - Population without wedding");
        System.out.println("--------------------------------------------------");
        newPopulationWithoutWedding.showPopulation(all);
    }

    public double[] bestObjFuncValIndividual() {
        double[] best = new double[3];

        // Best in initial population
        best[0] = population.getBestIndividual();

        // Best in population with wedding
        best[1] = newPopulationWithWedding.getBestIndividual();

        // Best in population without wedding
        best[2] = newPopulationWithoutWedding.getBestIndividual();

        return best;
    }

    public double[] worstObjFuncValIndividual() {
        double[] worst = new double[3];

        // Worst in initial population
        worst[0] = population.getWorstIndividual();

        // Worst in population with wedding
        worst[1] = newPopulationWithWedding.getWorstIndividual();

        // Worst in population without wedding
        worst[2] = newPopulationWithoutWedding.getWorstIndividual();

        return worst;
    }

}
