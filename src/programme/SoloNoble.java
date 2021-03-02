package programme;

import java.io.IOException;
import java.util.HashMap;

/**
 * classe gerant la resolution d un Solo Noble
 * 
 * @author DEMANGE Alessi - NICOL Benoit S3C
 *
 */
public class SoloNoble {

	/**
	 * attribut donnant la grille du SoloNoble
	 */
	private Grille tablier;

	/**
	 * attribut rassemblant l'ensemble des grilles menant a la resolution du
	 * SoloNoble
	 */
	private HashMap<Integer, String[][]> solutions;

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
	 * 
	 * @param nomFichier nom de la grille a utiliser
	 * @throws IOException en cas d erreur de lecture de fichier
	 */
	public SoloNoble(String nomFichier) throws IOException {

		tablier = new Grille(nomFichier);
		solutions = new HashMap<Integer, String[][]>();
		nombreDeplacements = 0;
		nombreAppelsResoudreSoloNoble = 0;

		// Message de d�but
		String s = "Bienvenue !\n";
		s += "Cet algorithme va tenter de r�soudre le Solo Noble suivant :\n\n";

		// On affiche la grille
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
	 * 
	 * @throws IOException
	 */
	public SoloNoble() throws IOException {

		tablier = new Grille();
		solutions = new HashMap<Integer, String[][]>();
		nombreDeplacements = 0;
		nombreAppelsResoudreSoloNoble = 0;

		// Message de d�but
		String s = "Bienvenue !\n";
		s += "Cet algorithme va tenter de r�soudre le Solo Noble suivant :\n\n";

		// On affiche la grille
		for (int i = 0; i < tablier.getGrille().length; i++) {
			for (int j = 0; j < tablier.getGrille()[0].length; j++) {
				s += new String(tablier.getGrille()[i][j]);
			}
			s += "\n";
		}

		System.out.println(s);
	}

	/**
	 * methode permettant de resoudre le SoloNoble a l aide de la recursivite avec
	 * retour arriere
	 * 
	 * @param billes le nombre de billes restant dans la grille
	 * @return true si on a reussi a resoudre le SoloNoble (si billes est egal a 1),
	 *         false sinon
	 */
	public boolean resoudreSoloNoble(int billes) {

		// Bool�en initialis� � false
		// Il sera vrai si et seulement si le nombre de billes est �gal � 1
		boolean grilleValide = false;

		// On incremente le nombre d'appels de la m�thode resoudreSoloNoble(billes)
		nombreAppelsResoudreSoloNoble++;

		// On stocke dans une variable la grille actuelle en copiant caract�re par
		// caract�re
		// Cela nous a permis de contourner le probl�me d� au stockage d'une grille
		// Si on sauvegardait toute la grille directement, on aurait un probl�me car la
		// grille change en permanence, � cause de la r�cursivit� avec retour arri�re
		// L'ensemble des grilles contenues dans solutions seraient alors toutes les
		// m�mes et contiendraient la grille de fin (avec une bille)
		String[][] grilleTempo = new String[tablier.getNbLignes()][tablier.getNbColonnes()];
		for (int i = 0; i < tablier.getGrille().length; i++) {
			for (int j = 0; j < tablier.getGrille()[0].length; j++) {
				grilleTempo[i][j] = tablier.getGrille()[i][j];
			}
		}

		// on ajoute � la table solutions la grille, avec la cl� billes
		solutions.put(billes, grilleTempo);

		if (billes == 1) {

			// On a r�solu le Solo Noble
			grilleValide = true;

			// On affiche toutes les solutions
			ecrireToutesSolutions();
		}

		else {

			// On change la valeur de l'attribut deplacement de la grille � debut
			// Car on commence un nouvel appel de la fonction resoudreSoloNoble(billes)
			tablier.setDeplacement("debut");

			// On initialise deux indices afin de se d�placer dans la grille
			int i = 0;
			int j = 0;

			// On continue tant que l'on a pas r�solu et que l'on est pas arriv� � la fin de
			// la grille
			while (!grilleValide && i < tablier.getNbLignes()) {

				j = 0;
				while (!grilleValide && j < tablier.getNbColonnes()) {

					// Si le caract�re � la position [i,j] est un trou
					if (tablier.getGrille()[i][j].equals(".")) {

						// On tente d'effectuer un d�placement, d�fini selon un ordre pr�cis
						tablier.deplacerBille(i, j);

						// On continue tant que l'on a pas r�solu le Solo Noble
						// Et que l'on est pas arriv� � la fin des d�placements possibles
						while (!grilleValide && !tablier.getDeplacement().equals("fin")) {

							// On incr�mente le nombre de d�placements
							nombreDeplacements++;

							// On r�cup�re le d�placement actuel
							String deplacement = new String(tablier.getDeplacement());

							// La variable grilleValide r�cup�re le resultat de la fonction
							// resoudreSoloNoble(billes - 1)
							// C'est un appel caract�ristique de la r�cursivit� avec retour arri�re
							grilleValide = resoudreSoloNoble(billes - 1);

							// Si notre grille n'�tait pas valide
							if (!grilleValide) {

								// On r�cup�re l'ancien d�placement
								tablier.setDeplacement(deplacement);

								// On effectue le retour Arriere
								tablier.retourArriere(i, j);

								// On incr�mente le nombre de d�placements
								nombreDeplacements++;

								// On teste un autre d�placement, si il y en a
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
	 * methode permettant d ecrire toutes les solutions et les messages donnant le
	 * nombre de deplacements et d appels de la methode resoudreSoloNoble()
	 */
	public void ecrireToutesSolutions() {

		System.out.println("\nLe Solo Noble est r�solu !\n");
		System.out.println("Voici les �tapes menant � la r�solution :\n");

		// On parcourt la table solutions depuis la fin
		for (int i = solutions.size(); i > 0; i--) {

			// On r�cup�re la grille associ�e
			String[][] elt = solutions.get(i);

			// On affiche le num�ro de l'�tape en fonction du nombre d'�l�ments de la table
			// solutions
			System.out.println("\nEtape n�" + (solutions.size() + 1 - i) + "\n");

			// On parcourt la grille actuelle et on l'affiche
			for (int j = 0; j < elt.length; j++) {

				for (int k = 0; k < elt[0].length; k++) {

					System.out.print(elt[j][k]);
				}
				System.out.println();
			}
			System.out.println("\n-------");
		}

		// On affiche le nombre de d�placements et le nombre
		System.out.println("Nombre de d�placements : " + nombreDeplacements);
		System.out.println("Nombre d'appels de la m�thode resoudreSoloNoble() : " + nombreAppelsResoudreSoloNoble);
	}

	/**
	 * methode main permettant de lancer la fonction resoudreSoloNoble() et d
	 * afficher le temps d execution
	 * 
	 * @param args le nom de la grille si il y en a
	 * @throws IOException en cas d erreur de lecture de fichier
	 */
	public static void main(String[] args) throws IOException {

		// On cr�e un nouveau SoloNoble
		SoloNoble sn;
		if (args.length == 1)

			// On utilise le constructeur SoloNoble(String) si il y a un argument
			sn = new SoloNoble(args[0]);

		// On utilise le constructeur SoloNoble() si il n'y a pas d'arguments
		else
			sn = new SoloNoble();
		
		int nbBilles = sn.getTablier().calculerNombreBilles();
		
		System.out.println("\nLe nombre de billes de cette grille est de " + nbBilles + "\n");
		
		// On cree une variable r�cup�rant l'heure actuelle en nanosecondes
		double debut = System.nanoTime();
		
		//On appelle la methode de resolution
		boolean resolution = sn.resoudreSoloNoble(nbBilles);

		// On cree une autre variable r�cup�rant l'heure actuelle en nanosecondes
		double fin = System.nanoTime();

		// Si la r�solution est impossible, cela veut dire que la grille ne pas valide
		// Et on l'indique � l'utilisateur
		if (!resolution)
			System.out.println("Grille impossible !");

		// On affiche le temps de recherche
		// En faisant la diff�rence de nos deux variables et en divisant le r�sultat par
		// 1 000 000 000
		// Afin d'obtenir le temps en secondes
		System.out.println("Temps de recherche : " + ((fin - debut) / 1000000000) + "s");
	}

	/**
	 * getter du tablier pour la methode main
	 * 
	 * @return le tablier du SoloNoble
	 */
	public Grille getTablier() {
		return tablier;
	}
}
