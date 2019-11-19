package evolutionary;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

public class Individual implements Cloneable{
    private ArrayList<Double> x;
    private ArrayList<Double> sigma;
    private double functionValue;
    private boolean isMarried = false;

    private Random rand = new Random();


    public Individual(int dim, int xMin, int xMax, int sigmaMax, int funcNum) {
        x = new ArrayList<>(dim);
        sigma = new ArrayList<>(dim);

        for(int i = 0; i < dim; i++) {
            x.add(xMin + (xMax - xMin) * rand.nextDouble());
            sigma.add(sigmaMax * rand.nextDouble());
        }

        functionValue = countObjectiveFunction(funcNum);
    }

    public ArrayList<Double> getX() {
        return x;
    }

    public ArrayList<Double> getSigma() {
        return sigma;
    }

    public void setFunctionValue(double functionValue) {
        this.functionValue = functionValue;
    }

    public double countObjectiveFunction(int funcNum) {
        switch(funcNum) {
            case 0:
                double sum0 = 0;
                for(Double d: x){
                    sum0 += d*d*(d-800)*(d-800)*(d+200)*(d+200);
                }
                return sum0;
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
            default:
                double sum1 = 0;
                for(Double d: x){
                    sum1 += d*d*d;
                }
                return sum1;
                //TODO objective functions
        }
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void marry(Individual second) {
        double val = (this.functionValue + second.getFunctionValue()) / 2;
        this.functionValue = val;
        second.setFunctionValue(val);
    }

    public double getFunctionValue() {
        return functionValue;
    }

    public void setX(ArrayList<Double> x) {
        this.x = x;
    }

    public void setSigma(ArrayList<Double> sigma) {
        this.sigma = sigma;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public Object clone() throws CloneNotSupportedException{
        Individual individual = (Individual)super.clone();

        individual.setMarried(false);

        //copy array of x
        ArrayList<Double> xTmp = new ArrayList<>(x);
        //copy array of sigma
        ArrayList<Double> sigmaTmp = new ArrayList<>(sigma);

        individual.setX(xTmp);
        individual.setSigma(sigmaTmp);

        return individual;
    }

    public int getDimension() {
        return x.size();
    }
}
