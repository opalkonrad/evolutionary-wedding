# evolutionary-wedding
The modification of classical evolutionary algorithm, basing on connecting chromosomes in pairs and changing their objective function value to mean value of couple.


[link do CEC 2017 do funkcji przystosowania](https://www.researchgate.net/profile/Guohua_Wu5/publication/317228117_Problem_Definitions_and_Evaluation_Criteria_for_the_CEC_2017_Competition_and_Special_Session_on_Constrained_Single_Objective_Real-Parameter_Optimization/links/5982cdbaa6fdcc8b56f59104/Problem-Definitions-and-Evaluation-Criteria-for-the-CEC-2017-Competition-and-Special-Session-on-Constrained-Single-Objective-Real-Parameter-Optimization.pdf)

## Algorytm
lambda > mi
1. Stwrozenie losowej populacji liczb rzeczywistych (mi osobników)
2. Obliczenie funkcji przystosowania
3. Połączenie w pary i wyliczenie nowych funkcji przystosowania
4. Nowa populacja za pomocą koła ruletki (lambda osobników)
5. Mutacje na podstawie sigmy z rozkładem normalnym
6. Obliczenie funkcji przystosowania i wybór mi najlepszych osobników
7. Jeżeli nie warunek stopu, to powróć do punktu 3.

## Język
Java
