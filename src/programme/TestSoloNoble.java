package programme;

import java.io.IOException;
import java.util.HashMap;

/**
 * classe gerant la resolution d un Solo Noble
 * @author DEMANGE Alessi - NICOL Benoit
 *
 */
public class TestSoloNoble {
	
	/**
	 * attribut donnant la grille du SoloNoble
	 */
	private TestGrille tablier;
	
	/**
	 * attribut rassemblant l'ensemble des grilles menant a la resolution du SoloNoble
	 */
	private HashMap<Integer,String[][]> solutions;
	
	/**
	 * attribut donnant le nombre de deplacements realises durant la resolution
	 */
	private int nombreDeplacements;
	
	/**
	 * attribut donnant le nombre d appels de la methode resoudreSoloNoble()
	 */
	private int nombreAppelsResoudreSoloNoble;
	
	/**
	 * constructeur d un SoloNoble avec un nom de fichier en parametre
	 * @param nomFichier nom de la grille a utiliser
	 * @throws IOException en cas d erreur de lecture de fichier
	 */
	public TestSoloNoble(String nomFichier) throws IOException {
		
		this.tablier = new TestGrille(nomFichier);
		this.solutions = new HashMap<Integer, String[][]>();
		this.nombreDeplacements = 0;
		this.nombreAppelsResoudreSoloNoble = 0;
		
		
		//Message de début
		String s = "Bienvenue !\n";
		s += "Cet algorithme va tenter de résoudre le Solo Noble suivant :\n\n";
		
		//On affiche la grille
		for (int i = 0; i < tablier.getGrille().length; i++) {
			for (int j = 0; j < tablier.getGrille()[0].length; j++) {
				s += tablier.getGrille()[i][j];
			}
			s += "\n";
		}
		
		System.out.println(s);
	}
	
	/**
	 * constructeur d un SoloNoble par defaut
	 * @throws IOException
	 */
	public TestSoloNoble() throws IOException{
		
		this.tablier = new TestGrille();
		this.solutions = new HashMap<Integer, String[][]>();
		this.nombreDeplacements = 0;
		this.nombreAppelsResoudreSoloNoble = 0;
		
		//Message de début
		String s = "Bienvenue !\n";
		s += "Cet algorithme va tenter de résoudre le Solo Noble suivant :\n\n";
		
		//On affiche la grille
		for (int i = 0; i < tablier.getGrille().length; i++) {
			for (int j = 0; j < tablier.getGrille()[0].length; j++) {
				s += new String(tablier.getGrille()[i][j]);
			}
			s += "\n";
		}
		
		System.out.println(s);
	}
	
	/**
	 * methode permettant de resoudre le SoloNoble a l aide de la recursivite
	 * @param billes le nombre de billes restant dans la grille
	 * @return true si on a reussi a resoudre le SoloNoble (si billes est egal a 1), false sinon
	 */
	public boolean resoudreSoloNoble(int billes){
		
		//Booléen initialisé à false
		//Il sera vrai si et seulement si le nombre de billes est égal à 1
		boolean grilleValide = false;
		
		//On incremente le nombre d'appels de la méthode resoudreSoloNoble(billes)
		nombreAppelsResoudreSoloNoble++;
		
		//On stocke dans une variable la grille actuelle en copiant caractère par caractère
		//Cela nous a permis de contourner le problème dû au stockage d'une grille
		//Si on sauvegardait toute la grille directement, on aurait un problème car la grille change en permanence, à cause de la récursivité
		//L'ensemble des grilels contenues dans solutions seraient alors toutes les mêmes et contiendraient la grille de fin (avec une bille)
		String[][] grilleTempo = new String[this.tablier.getNbLignes()][this.tablier.getNbColonnes()];
		for (int i = 0; i < this.tablier.getGrille().length; i++) {
			for (int j = 0; j < this.tablier.getGrille()[0].length; j++) {
				grilleTempo[i][j] = this.tablier.getGrille()[i][j];
			}
		}
		
		//on ajoute à la table solutions la grille, avec la clé billes
		solutions.put(billes, grilleTempo);
		
		if (billes == 1) {
			
			//On a résolu le Solo Noble
			grilleValide = true;
			
			//On affiche toutes les solutions
			ecrireToutesSolutions();
		}
		
		else {
			
			//On change la valeur de l'attribut deplacement de la grille à debut
			//Car on commence un nouvel appel de la fonction resoudreSoloNoble(billes)
			tablier.setDeplacement("debut");
			
			//On initialise deux indices afin de se déplacer dans la grille
			int i = 0;
			int j = 0;
			
			//on continue tant que l'on a pas résolu et que l'on est pas arrivé à la fin de la grille
			while (!grilleValide && i < tablier.getNbLignes()) {
				
				j = 0;
				while (!grilleValide && j < tablier.getNbColonnes()) {
					
					//Si le caractère à la position [i,j] est un trou
					if (tablier.getGrille()[i][j].equals(".")) {
						
						//On tente d'effectuer un déplacement, défini selon un ordre précis
						tablier.deplacerBille(i, j);
						
						
						
						//on continue tant que l'on a pas résolu le Solo Noble
						// Et que l'on est pas arrivé à la fin des déplacements possibles
						while(!grilleValide && !this.tablier.getDeplacement().equals("fin")) {
							
							//On incrémente le nombre de déplacements
							nombreDeplacements++;
							
							//On récupère le déplacement actuel
							String deplacement = new String(this.tablier.getDeplacement());
							
							//La variable grilleValide récupère le resultat de la fonction resoudreSoloNoble(billes - 1)
							//C'est un appel caractéristique de la récursivité
							grilleValide = resoudreSoloNoble(billes - 1);
							
							//Si notre grille n'était pas valide
							if(!grilleValide) {
								
								//On récupère l'ancien déplacement
								tablier.setDeplacement(deplacement);
								
								//On effectue le retour Arriere
								tablier.retourArriere(i, j);
								
								//On incrémente le nombre de déplacements
								nombreDeplacements++;
								
								//On teste un autre déplacement, si il y en a
								tablier.deplacerBille(i, j);
								
							}
						}
					}
					j++;
				}
				i++;
			}
		}
		return grilleValide;
	}
	
	/**
	 * methode permettant d ecrire toutes les solutions et les messages donnant le nombre de deplacements et d appels de la methode resoudreSoloNoble()
	 */
	public void ecrireToutesSolutions() {
		
		System.out.println("\nSolo Noble résolu !\n");
		System.out.println("Voici les étapes menant à la résolution :\n");
		
		//On parcourt la table solutions depuis la fin
		for(int i = solutions.size(); i > 0; i--) {
			
			//On récupère la grille associée
			String [][] elt = solutions.get(i);
			
			//On affiche le numéro de l'étape en fonction du nombre d'éléments de la table solutions
			System.out.println("\nEtape n°" + (solutions.size() + 1 - i) + "\n");
			
			//On parcourt la grille actuelle et on l'affiche
			for (int j = 0; j < elt.length; j++) {
				
				for (int k = 0; k < elt[0].length; k++) {
					
					System.out.print(elt[j][k]);
				}
				System.out.println();
			}
			System.out.println("\n-------");
		}
		
		//On affiche le nombre de déplacements et le nombre
		System.out.println("Nombre de déplacements : " + nombreDeplacements);
		System.out.println("Nombre d'appels de la méthode resoudreSoloNoble() : " + nombreAppelsResoudreSoloNoble);
	}
	
	/**
	 * methode main permettant de lancer la fonction resoudreSoloNoble() et d afficher le temps d execution
	 * @param args le nom de la grille si il y en a
	 * @throws IOException en cas d erreur de lecture de fichier
	 */
	public static void main(String[] args) throws IOException {
		
		//On crée un nouveau SoloNoble
		TestSoloNoble sn;
		if (args.length == 1)
			
			//On utilise le constructeur SoloNoble(String) si il y a un argument
			sn = new TestSoloNoble(args[0]);
		
		//On utilise le constructeur SoloNoble() si il n'y a pas d'arguments
		else 
			sn = new TestSoloNoble();
		
		//On cree une variable récupérant l'heure actuelle en nanosecondes
		double debut = System.nanoTime();
		boolean resolution = sn.resoudreSoloNoble(sn.getTablier().calculerNombreBilles());
		
		//On cree une autre variable récupérant l'heure actuelle en nanosecondes
		double fin = System.nanoTime();
		
		//Si la résolution est impossible, cela veut dire que la grille ne pas valide et on l'indique à l'utilisateur
		if(!resolution)
			System.out.println("Grille impossible !");
		
		//On affiche le temps de recherche
		//En faisant la différence de nos deux variables et en divisant le résultat par 1 000 000 000
		//Afin d'obtenir le temps en secondes
		System.out.println("Temps de recherche : " + ((fin - debut) / 1000000000) + "s");
	}

	/**
	 * getter du tablier pour la methode main
	 * @return le tablier du SoloNoble
	 */
	public TestGrille getTablier() {
		return tablier;
	}
}
