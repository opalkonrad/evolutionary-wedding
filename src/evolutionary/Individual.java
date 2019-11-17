package evolutionary;

import java.util.ArrayList;
import java.util.Random;

public class Individual {
    private ArrayList<Double> x;
    private ArrayList<Double> sigma;

    private Random rand = new Random();

    public Individual(int dim, int xMin, int xMax, int sigmaMax) {
        x = new ArrayList<>(dim);
        sigma = new ArrayList<>(dim);

        for(int i = 0; i < dim; i++) {
            x.add(xMin + (xMax - xMin) * rand.nextDouble());
            sigma.add(sigmaMax * rand.nextDouble());
        }
    }

    public ArrayList<Double> getX() {
        return x;
    }

    public ArrayList<Double> getSigma() {
        return sigma;
    }
}
