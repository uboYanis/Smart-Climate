package model;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * la class Annee represente une Annee par un numero d'Annee et une liste de
 * mois , elle permet de construire une Annee et de lui associer une liste de
 * mois et d'ajouter des mois à cette dernière,elle permet de mieux géré la
 * création des mois avec la methode {@link#getOrCreatMois} et définie des
 * méthodes pour récupérer une liste de releve ou un releve d'une journée et
 * d'un mois donné et
 * </p>
 * <p>
 * elle définie les methodes de calcul suivante :
 * </p>
 * <ul>
 * <li>moyennes des Mois d'une année</li>
 * <li>l'Ecart Type des Mois d'une année</li>
 * <li>le maximum des Mois d'une année</li>
 * <li>le minimum des Mois d'une année</li>
 * </ul>
 * 
 * <ul>
 * <li>moyennes d'un mois donné</li>
 * <li>l'Ecart Type d'un mois donné</li>
 * <li>le maximum d'un mois donné</li>
 * <li>le minimum d'un mois donné</li>
 * </ul>
 * 
 * @author REMILA Yanis
 * @version 1.1
 * @see Releve
 * @see Jour
 * @see Mois
 * @see Station
 * @see Model
 *
 */
public class Annee {
	/**
	 * le numéro d'année
	 */
	private int annee;
	/**
	 * liste de mois de l'année
	 */
	private List<Mois> listesMois = new ArrayList<Mois>();

	/**
	 * constructeur avec parametre
	 * 
	 * @param annee : annee
	 */
	public Annee(int annee) {
		this.annee = annee;
	}

	/**
	 * vérifie si le mois demander existe deja dans la liste de mois de l'année
	 * si le mois existe alors elle renvoie le mois existant sinon elle crée un
	 * mois et renvoie ce dernier
	 * 
	 * @param idMois
	 *            :numero du mois
	 * @return si le mois existe alors return le mois existant sinon crée un
	 *         mois et return new mois
	 * @see Mois
	 */
	public Mois getOrCreatMois(int idMois) {
		for (Mois moisChecher : this.getListesMois()) {
			if (moisChecher.getMois() == idMois) {
				return moisChecher;
			}
		}
		Mois newMois = new Mois(idMois);
		this.ajoutMois(newMois);
		return newMois;
	}

	/**
	 * Permet d'ajouter un mois a la liste des mois d'une année
	 * 
	 * @param mois
	 *            :mois
	 * @see Mois
	 */
	public void ajoutMois(Mois mois) {
		listesMois.add(mois);
	}

	/**
	 * renvoie la liste des releves d'une journée et d'un mois donné
	 * 
	 * @param moisChercher
	 *            : mois :numero du mois
	 * @param jourChercher
	 *            :numero du jour
	 * @return si le jour et le mois existe alors return la liste des releves
	 *         sinon return null
	 */
	public List<Releve> getReleve(int moisChercher, int jourChercher) {
		for (Mois mois : listesMois) {
			if (mois.getMois() == moisChercher) {
				return mois.getReleve(jourChercher);
			}
		}
		return null;
	}

	/**
	 * renvoie un releve d'un mois et une journée et une heur donné
	 * 
	 * @param moisChercher
	 *            : mois :numero du mois
	 * @param jourChercher
	 *            :numero du jour
	 * @param horaireChercher
	 *            : horaire du releve
	 * @return si le mois existe dans la liste de mois le jour existe dans la
	 *         liste de jour et le releve existe dans la liste de releve alors
	 *         return le releve chercher sinon return null
	 * @see Mois
	 * @see Jour
	 * @see Releve
	 */
	public Releve getParNumReleve(int moisChercher, int jourChercher, int horaireChercher) {
		for (Mois mois : this.getListesMois()) {
			return mois.getParNumReleve(jourChercher, horaireChercher);
		}
		return null;
	}

	/**
	 * renvoie le numero de l'année
	 * 
	 * @return numero d'année
	 */
	public int getAnnee() {
		return annee;
	}

	/**
	 * renvoie la liste des Mois d'une année
	 * 
	 * @return liste des Mois d'une année
	 * @see Mois
	 */
	public List<Mois> getListesMois() {
		return listesMois;
	}

	// -------------------------------------------//
	// ---------- fonction de calcul ------------//
	// -----------------------------------------//

	/**
	 * renvoie une liste des moyenne des mois d'une année
	 * 
	 * @return la liste des moyenne des mois d'une année
	 * @see Mois
	 */
	public List<Releve> getMoyenneParMois() {
		List<Releve> listMoyenneparMois = new ArrayList<Releve>();
		for (Mois mois : this.getListesMois()) {
			listMoyenneparMois.add(mois.calculMoyenneMois());
		}
		return listMoyenneparMois;
	}

	/**
	 * renvoie une liste des Ecart Type des mois d'une année
	 * 
	 * @return la liste des Ecart Type des mois d'une année
	 * @see Mois
	 */
	public List<Releve> getEcartTypeMois() {
		List<Releve> listEcartTypeMois = new ArrayList<Releve>();
		for (Mois mois : this.getListesMois()) {
			listEcartTypeMois.add(mois.calculeEcartTypeMois());
		}
		return listEcartTypeMois;
	}

	/**
	 * renvoie une liste des Max des mois d'une année
	 * 
	 * @return la liste des Max des mois d'une année
	 * @see Mois
	 */
	public List<Releve> getMaxParMois() {
		List<Releve> listMaxparMois = new ArrayList<Releve>();
		for (Mois mois : this.getListesMois()) {
			listMaxparMois.add(mois.calculMaxMois());
		}
		return listMaxparMois;
	}

	/**
	 * renvoie une liste des Min des mois d'une année
	 * 
	 * @return la liste des Min des mois d'une année
	 * @see Mois
	 */
	public List<Releve> getMinParMois() {
		List<Releve> listMinparMois = new ArrayList<Releve>();
		for (Mois mois : this.getListesMois()) {
			listMinparMois.add(mois.calculMinMois());
		}
		return listMinparMois;
	}

	/**
	 * revoie une liste des moyenne d'un mois donné en parametre
	 * 
	 * @param moisChercher
	 *            : mois :mois
	 * @return liste des moyenne d'un mois donné en parametre
	 * @see Jour
	 */
	public List<Releve> getMoyenneParJour(int moisChercher) {
		for (Mois mois : this.getListesMois()) {
			if (mois.getMois() == moisChercher) {
				return mois.getMoyenneParJour();
			}
		}
		return null;
	}

	/**
	 * revoie une liste des Ecart Type d'un mois donné en parametre
	 * 
	 * @param moisChercher
	 *            : mois : mois
	 * @return liste des Ecart Type d'un mois donné en parametre
	 * @see Jour
	 */
	public List<Releve> getEcartTypeParJour(int moisChercher) {
		for (Mois mois : this.getListesMois()) {
			if (mois.getMois() == moisChercher) {
				return mois.getEcartTypeParJour();
			}
		}
		return null;
	}

	/**
	 * revoie une liste des MAX d'un mois donné en parametre
	 * 
	 * @param moisChercher
	 *            : mois :mois
	 * @return liste des MAX d'un mois donné en parametre
	 * @see Jour
	 */
	public List<Releve> getMaxParJour(int moisChercher) {
		for (Mois mois : this.getListesMois()) {
			if (mois.getMois() == moisChercher) {
				return mois.getMaxParJour();
			}
		}
		return null;
	}

	/**
	 * revoie une liste des MIN d'un mois donné en parametre
	 * 
	 * @param moisChercher
	 *            : mois
	 * @return liste des MIN d'un mois donné en parametre
	 * @see Jour
	 */
	public List<Releve> getMinParJour(int moisChercher) {
		for (Mois mois : this.getListesMois()) {
			if (mois.getMois() == moisChercher) {
				return mois.getMinParJour();
			}
		}
		return null;
	}
}
