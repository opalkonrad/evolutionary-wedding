package evolutionary;

import java.lang.reflect.Array;
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
     * @param dim      Dimension of individual's x and sigma array.
     * @param xMin     Minimum value of x.
     * @param xMax     Maximum value of x.
     * @param sigmaMax Maximum value of sigma.
     * @param funcNum  Stores number which indicates appropriate objective function.
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
        setObjFuncVal(countObjFuncVal(funcNum));
    }

    /**
     * Used to count new objective function value
     *
     * @return New objective function value.
     */
    public double countObjFuncVal(int funcNum) {
        switch (funcNum) {
            case 0:
                double sum0 = 0;

                for (Double d : x) {
                    sum0 += d * d;
                }

                return sum0;
            case 1:
                double sum1 = 0;

                for(double i=0; i<getDimension(); ++i){
                    sum1 += pow(1000000, i/(getDimension()-1))*pow(x.get((int)i),2);
                }
                return sum1;
            case 2:
                double function2 = 0;
                for(int i=1; i<getDimension(); ++i){
                    function2 += pow(x.get(i), 2);
                }
                return 1000000*function2 + x.get(0)*x.get(0);
            case 3:
                double function3 = 0;
                for(int i=1; i<getDimension(); ++i){
                    function3 += pow(x.get(i), 2);
                }
                return function3 + 1000000*x.get(0)*x.get(0);
            case 4:
                double function4 = 0;
                for(int i=0; i<getDimension()-1; i++){
                    function4 += 100*pow(x.get(i)*x.get(i) - x.get(i+1),2) + pow(x.get(i)-1, 2);
                }
                return function4;
            case 6:
                double sum6_1 = 0;
                double a = 0.5;
                double b = 3;
                double kmax = 20;
                for(double x_i: x){
                    for(int k=0; k<=kmax; ++k){
                        sum6_1 += pow(a, k)*cos(2*PI*pow(b, k)*(x_i+0.5));
                    }
                }
                double sum6_2 = 0;
                for(int k=0; k<=kmax; k++){
                    sum6_2 += pow(a, k)*cos(2*PI*pow(b, k)*0.5);
                }
                return sum6_1 - getDimension() * sum6_2;
            case 23:
                double[] w = new double[evolution.getN()];
                double sumW23 = 0;
                for(int i=0; i<evolution.getN(); ++i){
                    double sum23_1 = 0;
                    for(int j=0; j<getDimension(); ++j){
                        sum23_1 = pow(x.get(j) - evolution.getOptimum(i, j), 2);
                    }
                    w[i] = exp(-sum23_1/(2*getDimension()*evolution.getSigma()[i]))/sqrt(sum23_1);
                    sumW23 += w[i];
                }
                for(int i=0; i<evolution.getN(); ++i){
                    w[i] = w[i]/sumW23;
                }

                double function = 0;
                for(int i=0; i<evolution.getN(); ++i){
                    function += w[i]*(evolution.getLambda()[i]*countObjFuncVal(evolution.getG()[i])+evolution.getBias()[i]);
                }

                return function;
            // TODO objective functions

            default:
                return 0;
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
