package evolutionary;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

/**
 * Represents an individual.
 * For more information about meanings of variables see "Podstawy sztucznej inteligencji" by Pawel Wawrzynski.
 */
public class Individual implements Cloneable {
    private ArrayList<Double> x;
    private ArrayList<Double> sigma;
    private int funcNum;
    private double objFuncVal;
    private boolean isMarried = false;
    private Random rand = new Random();
    private Evolution evolution;


    /*----- Constructors -----*/

    /**
     * @param evolution
     * @param dim       Dimension of individual's x and sigma array.
     * @param xMin      Minimum value of x.
     * @param xMax      Maximum value of x.
     * @param sigmaMax  Maximum value of sigma.
     * @param funcNum   Stores number which indicates appropriate objective function.
     */
    public Individual(Evolution evolution, int dim, int xMin, int xMax, int sigmaMax, int funcNum) {
        this.evolution = evolution;
        x = new ArrayList<>(dim);
        sigma = new ArrayList<>(dim);
        this.funcNum = funcNum;

        // Evaluate random values of x and sigma array based on function parameters
        for (int i = 0; i < dim; ++i) {
            x.add(xMin + (xMax - xMin) * rand.nextDouble());
            sigma.add(sigmaMax * rand.nextDouble());
        }

        // Counts objective function value based on funcNum
        updateObjFuncVal();
    }


    /*----- Getters & setters -----*/

    /**
     * @return Returns x array.
     */
    public ArrayList<Double> getX() {
        return x;
    }

    /**
     * @param x Sets new x array.
     */
    public void setX(ArrayList<Double> x) {
        this.x = x;
    }

    /**
     * @return Returns sigma array.
     */
    public ArrayList<Double> getSigma() {
        return sigma;
    }

    /**
     * @param sigma Sets new sigma array.
     */
    public void setSigma(ArrayList<Double> sigma) {
        this.sigma = sigma;
    }

    /**
     * @return Returns objective function value.
     */
    public double getObjFuncVal() {
        return objFuncVal;
    }

    /**
     * @param objFuncVal Sets new objective function value.
     */
    public void setObjFuncVal(double objFuncVal) {
        this.objFuncVal = objFuncVal;
    }

    /**
     * @return Returns true when individual is married or false otherwise.
     */
    public boolean isMarried() {
        return isMarried;
    }

    /**
     * @param married Sets wedlock.
     */
    public void setMarried(boolean married) {
        isMarried = married;
    }

    /**
     * @return Returns dimension of x array.
     */
    public int getDimension() {
        return x.size();
    }


    /*----- Methods -----*/

    /**
     * Updates objective function value.
     */
    public void updateObjFuncVal() {
        try {
            setObjFuncVal(countObjFuncVal(funcNum));
        }
        catch(Exception e){
            System.out.println("Not implemented function");
        }
    }

    /**
     * Used to count new objective function value
     *
     * @param funcNum number of CEC 2014 (without rotations and shifting) function
     * @throws Exception when function with this number is not implemented
     * @return Objective function value for this individual and given as parameter function number.
     */
    public double countObjFuncVal(int funcNum) throws Exception{
        switch (funcNum) {
            // High Conditioned Elliptic Function
            case 1:
                double func = 0;

                for (int i = 0; i < getDimension(); ++i) {
                    func += pow(1000000, ((double) i - 1) / ((double) getDimension() - 1)) * pow(x.get(i), 2);
                }

                return func;

            // Bent Cigar Function
            case 2:
                func = 0;

                for (int i = 1; i < getDimension(); ++i) {
                    func += pow(x.get(i), 2);
                }

                return 1000000 * func + pow(x.get(0), 2);

            // Discuss Function
            case 3:
                double func3 = 0;

                for (int i = 1; i < getDimension(); ++i) {
                    func3 += pow(x.get(i), 2);
                }

                return 1000000 * pow(x.get(0), 2) + func3;

            // Rosenbrock's Function
            case 4:
                func = 0;

                for (int i = 0; i < getDimension() - 1; ++i) {
                    func += 100 * pow(pow(x.get(i), 2) - x.get(i + 1), 2) + pow(x.get(i) - 1, 2);
                }

                return func;

            // Weierstrass Function
            case 6:
                double sum6_1 = 0;
                double sum6_2 = 0;
                double a = 0.5;
                double b = 3;
                double kmax = 20;

                for (double x_i : x) {
                    for (int k = 0; k <= kmax; ++k) {
                        sum6_1 += pow(a, k) * cos(2 * PI * pow(b, k) * (x_i + 0.5));
                    }
                }

                for (int k = 0; k <= kmax; k++) {
                    sum6_2 += pow(a, k) * cos(2 * PI * pow(b, k) * 0.5);
                }

                return sum6_1 - (getDimension() * sum6_2);

            // Griewank's Function
            case 7:
                double sum7_2 = 0;
                double mul7 = 1;

                for(int i = 0; i < getDimension(); ++i){
                    double x_i = x.get(i);
                    sum7_2 += x_i * x_i;
                    mul7 *= cos(x_i / sqrt(i));
                }

                return sum7_2 - mul7 + 1;

            // Rastrigin's Function
            case 8:
                double sum8 = 0;
                for(double x_i: x){
                    sum8 += x_i * x_i - 10 * cos(2*PI*x_i) + 10;
                }
                return sum8;

            // Modified Schwefel's Function
            case 9:
                double sum_9_gz = 0;
                for(int i=0; i<getDimension(); ++i){
                    double z_i = x.get(i) + 4.209687462275036e+002;
                    if(abs(z_i) <= 500){
                        sum_9_gz += z_i * sin(sqrt(abs(z_i)));
                    }
                    else if(z_i > 500){
                        sum_9_gz += (500 - (z_i%500)) * sin(sqrt(abs(500 - (z_i%500)))) - (z_i - 500)*(z_i - 500) / (10000 * getDimension());
                    }
                    else{
                        sum_9_gz += (abs(z_i)%500 - 500) * sin(sqrt(abs(abs(z_i)%500 - 500))) - (z_i + 500) * (z_i + 500) / (10000 * getDimension());
                    }
                }
                return 418.9829 * getDimension() - sum_9_gz;

            // HGBat Function
            case 12:
                double sum12_1 = 0;
                double sum12_2 = 0;
                for(double x_i: x){
                    sum12_1 += x_i;
                    sum12_2 += x_i * x_i;
                }
                func = sqrt(abs(sum12_2 * sum12_2 - sum12_1 * sum12_1));
                func += (0.5 * sum12_2 + sum12_1) / getDimension() + 0.5;
                return func;
            // Composition Function 1 (not-rotated and not-shifted)
            case 23:
            // Composition Function 5 (not-rotated and not-shifted)
            case 27:
                double[] w = new double[evolution.getN()];
                double sumW = 0;
                double compFunc = 0;

                // Count w_i and sum of w_i
                for (int i = 0; i < evolution.getN(); ++i) {
                    double sum_1 = 0;

                    for (int j = 0; j < getDimension(); ++j) {
                        sum_1 = pow(x.get(j) - evolution.getOptimum(i, j), 2);
                    }

                    w[i] = exp((-sum_1) / (2 * getDimension() * pow(evolution.getSigma()[i], 2))) / sqrt(sum_1);
                    sumW += w[i];
                }

                // Count omega_i
                for (int i = 0; i < evolution.getN(); ++i) {
                    w[i] = w[i] / sumW;
                }

                // Count result
                for (int i = 0; i < evolution.getN(); ++i) {
                    compFunc += w[i] * ((evolution.getLambda()[i] * countObjFuncVal(evolution.getG()[i])) + evolution.getBias()[i]);
                }

                return compFunc;

            default:
                throw new Exception("This function is not implemented");
        }
    }

    /**
     * It sets objective function values of two individuals equal to q(i) = q(j) = mean(q(i), q(j)).
     *
     * @param second Another individual to marry.
     */
    public void marry(Individual second) {
        double val = (this.objFuncVal + second.getObjFuncVal()) / 2;
        setObjFuncVal(val);
        setMarried(true);
        second.setMarried(true);
        second.setObjFuncVal(val);
    }

    /**
     * Clone individual with new x and sigma arrays equal to cloneable object and sets isMarried to false.
     *
     * @return New individual.
     * @throws CloneNotSupportedException When cannot clone the object.
     */
    public Object clone() throws CloneNotSupportedException {
        Individual individual = (Individual) super.clone();

        // Copy x and sigma arrays
        ArrayList<Double> xTmp = new ArrayList<>(x);
        ArrayList<Double> sigmaTmp = new ArrayList<>(sigma);

        individual.setX(xTmp);
        individual.setSigma(sigmaTmp);
        individual.setMarried(false);

        return individual;
    }

}
