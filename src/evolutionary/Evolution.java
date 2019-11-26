package evolutionary;

import java.util.Random;

public class Evolution {
    private Population population;
    private int childrenCount;
    private double[][] optimum;
    private boolean isWedding;
    private double mutationProbability;
    private int repeatNum;
    private int dim;
    private int N;
    private double[] sigma;
    private double[] lambda;
    private int[] g;
    private double[] bias;

    public double[] getLambda() {
        return lambda;
    }

    public double[] getSigma() {
        return sigma;
    }

    private Random rand = new Random();

    public int getN() {
        return N;
    }

    public int[] getG() {
        return g;
    }

    public double[] getBias() {
        return bias;
    }

    public Evolution(int mi, int childrenCount, int dim, int funcNum, int repeatNum, boolean isWedding, double mutationProbability){
        population = new Population(this, mi, dim, -100, 100, 10, funcNum);
        this.dim = dim;
        this.childrenCount = childrenCount;
        this.isWedding = isWedding;
        this.mutationProbability = mutationProbability;
        this.repeatNum = repeatNum;

        switch(funcNum){
            case 23:
                N = 5;
                sigma = new double[]{10, 20, 30, 40, 50};
                lambda = new double[]{1, 1e-6, 1e-26, 1e-6, 1e-6};
                g = new int[]{4, 1, 2, 3, 1};
                bias = new double[]{0, 100, 200, 300, 400};
                break;
        }

        optimum = new double[N][dim];
        for(int i=0; i<N; ++i){
            for(int j=0; j<dim; ++j) {
                optimum[i][j] = rand.nextDouble()*160-80; // random distribution from -80 to 80
            }
        }
    }

    public void performEvolution(){
        for(int i=0; i < repeatNum; i++){
            population = population.performEvolution(childrenCount, isWedding, mutationProbability);
        }
    }

    public void showPopulation(boolean all){
        System.out.println("OPTIMA:");
        for(int i=0; i<N; ++i){
            System.out.print(i+" [");
            for(int j=0; j<dim; ++j) {
                System.out.print(getOptimum(i, j)+"    ");
            }
            System.out.println("");
        }

        population.showPopulation(all);
    }

    public double getOptimum(int i, int j) {
        if(i>=N || j>=dim) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return optimum[i][j];
    }
}
