package evolutionary;

import java.util.*;

/**
 * @author sitekwb
 * @author opalkonrad
 */
public class Population {
    private ArrayList<Individual> population;
    private Random rand = new Random();
    private Evolution evolution;


    /*----- Constructors -----*/

    /**
     * Population default constructor with empty set of individuals.
     * @param evolution reference to mother class Evolution
     * @see ArrayList
     */
    public Population(Evolution evolution) {
        this.evolution = evolution;
        population = new ArrayList<>();
    }

    /**
     * Population constructor, creating individuals with given parameters
     * @param evolution reference to mother class Evolution
     * @param count number of individuals in population
     * @param dim dimension of each individual
     * @param xMin minimum value of gene
     * @param xMax maximum value of gene
     * @param sigmaMax maximum sigma value (sigma randomly generated from 0 to sigmaMax
     * @param funcNum number of CEC 2014 function, which will be optimized
     * @see Individual#Individual(Evolution, int, int, int, int, int)
     * @see ArrayList
     */
    public Population(Evolution evolution, int count, int dim, int xMin, int xMax, int sigmaMax, int funcNum) {
        this.evolution = evolution;
        population = new ArrayList<>();

        for (int i = 0; i < count; ++i) {
            this.addToPopulation(new Individual(evolution, dim, xMin, xMax, sigmaMax, funcNum));
        }
    }

    /**
     * @param evolution reference to mother class Evolution
     * @param individuals ArrayList of individuals which is copied to
     */
    public Population(Evolution evolution, ArrayList<Individual> individuals) {
        this.evolution = evolution;
        population = new ArrayList<>(individuals);
    }


    /*----- Getters & setters -----*/

    /**
     * @return number of individuals
     */
    public int getSize() {
        return population.size();
    }

    /**
     * @return population reference
     */
    public ArrayList<Individual> getPopulation() {
        return population;
    }

    /**
     * @return dimension of each individual
     */
    public int getDimension() {
        return population.get(0).getDimension();
    }


    /*----- Methods -----*/

    /**
     * @param individual reference of individual to add to population
     * @see Population#population
     */
    public void addToPopulation(Individual individual) {
        population.add(individual);
    }

    /**
     * @param individuals set of many references of individuals to add to population
     * @see Population#population
     * @see ArrayList
     * @see ArrayList#addAll(Collection)
     */
    public void addToPopulation(ArrayList<Individual> individuals) {
        population.addAll(individuals);
    }

    /**
     * @param individual reference of individual to remove from population
     * @see ArrayList#remove(Object)
     */
    public void removeFromPopulation(Individual individual) {
        population.remove(individual);
    }

    /**
     * Removes last individuals from population to get newSize of population
     * @param newSize new number of individuals in population
     * @see ArrayList#remove(int)
     */
    public void removeFromPopulation(int newSize) {
        for (int i = getSize() - 1; i >= newSize; --i) {
            population.remove(i);
        }
    }

    /**
     * Shows to standard output population details.
     * @param detail flag whether to show details of population, or short summary
     */
    public void showPopulation(boolean detail) {
        Individual bestIndividual = population.get(0);
        System.out.println("Best individual:\nX      " + bestIndividual.getX() + "\nSigma  " + bestIndividual.getSigma() + "\nObjective function value = " + bestIndividual.getObjFuncVal() + "\n");

        if (detail) {
            System.out.println("Additional info:");

            for (Individual i : population) {
                System.out.println("X     " + i.getX() + "\nsigma " + i.getSigma() + "\n" + i.getObjFuncVal() + "\n");
            }
        }
    }

    /**
     * Modification of classical evolutionary algorithm. Connects individuals in pairs, averages their objective function value and confirms it in individual flag
     * @see Individual#setObjFuncVal(double)
     * @see Individual#marry(Individual)
     * @see Collections#shuffle(List)
     */
    void performWedding() {
        Collections.shuffle(population);
        for (int i = 0; i < getSize(); i++) {
            if (population.get(i).isMarried()) {
                continue;
            }

            Individual first = population.get(i);

            while (++i != getSize() && population.get(i).isMarried()) {
            }

            if (i != getSize()) {
                first.marry(population.get(i));
            }
        }
    }

    /**
     * On base of wheel roulette method creates new children population by copying individuals to new references and performing mutations on them.
     * @author sitekwb
     * @param count size of new children population
     * @param mutationProbability probability of each mutation
     * @return children population
     * @throws CloneNotSupportedException may throw exception, if clone is not supported
     * @see Population#performMutations(double)
     */
    Population createChildrenPopulation(int count, double mutationProbability) throws CloneNotSupportedException {
        double functionValueSum = 0;

        //count sum of function values
        for (Individual individual : population) {
            functionValueSum += 1.0 / individual.getObjFuncVal(); //we minimize objective function, so the bigger objective function is, the smaller part of roulette wheel it occupies
        }

        //generate count random numbers from 0 to functionValueSum
        ArrayList<Double> randoms = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            randoms.add(rand.nextDouble() * functionValueSum);
        }

        Collections.sort(randoms);

        Population pop = new Population(evolution);

        //generate new population using roulette wheel
        double wheelPointer = 0;
        int randomsIndex = 0;
        for (Individual individual : population) {
            wheelPointer += 1.0 / individual.getObjFuncVal();
            if (randomsIndex >= count) {
                break;
            }
            //until random will not exceed wheelPointer, individual is cloned to child population
            while (randoms.get(randomsIndex) <= wheelPointer) {
                pop.addToPopulation((Individual) individual.clone());
                if (++randomsIndex >= count) {
                    break;
                }
            }
        }

        pop.performMutations(mutationProbability);

        return pop;
    }

    /**
     * Performs random mutations on individual with probability given in parameters.
     * @author opalkonrad
     * @param mutationProbability probability of mutations
     */
    void performMutations(double mutationProbability) {
        double tau = 1 / (Math.sqrt(2 * getDimension()));
        double tauPrim = 1 / (Math.sqrt(2 * Math.sqrt(getDimension())));

        for (Individual individual : population) {
            // Mutate only some of the individuals in population
            if (rand.nextDouble() > mutationProbability) {
                continue;
            }

            ArrayList<Double> newX = new ArrayList<>();
            ArrayList<Double> newSigma = new ArrayList<>();

            double normDistr = rand.nextGaussian();

            for (double sigma : individual.getSigma()) {
                newSigma.add(sigma * Math.exp(tau * normDistr + tauPrim * rand.nextGaussian()));
            }

            int counter = 0;
            for (double x : individual.getX()) {
                newX.add(x + (newSigma.get(counter) * rand.nextGaussian()));
                counter++;
            }

            individual.setX(newX);
            individual.setSigma(newSigma);
            individual.updateObjFuncVal();
        }
    }

    /**
     * Performs evolution on population.
     * @param lambda number of children
     * @param isWedding determines if there is wedding modification of evolutionary algorithm
     * @param mutationProbability probability of mutations of children population
     * @return new population after one evolution
     */
    public Population performEvolution(int lambda, boolean isWedding, double mutationProbability) {
        if (isWedding) {
            performWedding();
        }

        Population childrenPopulation = null;
        try {
            // Randomly generate lambda individuals using roulette wheel
            childrenPopulation = createChildrenPopulation(lambda, mutationProbability);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        // Limit population to original size
        Population finalPopulation = limitPopulation(childrenPopulation);

        return finalPopulation;
    }

    /**
     * From combined original population and child population we choose new population by limiting it to original size
     * (choosing individuals with best objective function).
     * @author sitekwb
     * @param childrenPopulation reference to population of children in evolution
     * @return new population made of combined and limited parent and children evolution
     */
    public Population limitPopulation(Population childrenPopulation) {
        // Create one references population
        Population allPopulation = new Population(evolution, population);
        allPopulation.addToPopulation(childrenPopulation.getPopulation());

        // Sort population by objective function value descending
        allPopulation.population.sort(new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if (o2.getObjFuncVal() > o1.getObjFuncVal()) {
                    return -1;
                } else if (o2.getObjFuncVal() == o1.getObjFuncVal()) {
                    return 0;
                } else return 1;
            }
        });

        allPopulation.removeFromPopulation(getSize());

        return allPopulation;
    }

}
