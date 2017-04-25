package model;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * la class Station represente une Station par un id Station,un nom de Station
 * et une liste d'année , elle permet de construire une Station et de lui
 * associer une liste d'année et d'ajouter des année à cette dernière,elle
 * permet de mieux géré la création des année avec la methode getOrCreatAnnee et
 * définie des méthodes pour récupérer une liste de releve ou un releve d'une
 * journée et d'un mois et d'une année donné et
 * </p>
 * <p>
 * elle définie les methodes de calcul suivante :
 * </p>
 * <ul>
 * <li>moyennes des Mois d'une année donné en parametre</li>
 * <li>l'Ecart Type des Mois d'une année donné en parametre</li>
 * <li>le maximum des Mois d'une année donné en parametre</li>
 * <li>le minimum des Mois d'une année donné en parametre</li>
 * </ul>
 * 
 * <ul>
 * <li>liste moyennes d'un mois et d'une année donné en parametre</li>
 * <li>liste d'Ecart Type d'un mois et d'une année donné en parametre</li>
 * <li>liste maximum d'un mois et d'une année donné en parametre</li>
 * <li>liste minimum d'un mois et d'une année donné en parametre</li>
 * </ul>
 * 
 * @author REMILA Yanis
 * @version 1.1
 * @see Releve
 * @see Jour
 * @see Mois
 * @see Annee
 * @see Model
 */
public class Station {
	/**
	 * nom de la station
	 */
	private String nomStation;
	/**
	 * l'identifiant de la station
	 */
	private int idStation;
	/**
	 * liste des années de la station
	 */
	private List<Annee> listesAnnee = new ArrayList<Annee>();

	/**
	 * constructeur avec parametre
	 * 
	 * @param idStation
	 *            : id station
	 * @param nomStation
	 *            : nom station
	 */
	public Station(int idStation, String nomStation) {
		this.idStation = idStation;
		this.nomStation = nomStation;
	}

	/**
	 * vérifie si l'année demander existe deja dans la liste d'année de station
	 * si l'année existe alors elle renvoie l'année existante sinon elle crée
	 * une année et renvoie cet derniere
	 * 
	 * @param idAnnee
	 *            :numero d'année
	 * @return si l'année existe alors return l'année existante sinon crée une
	 *         année et return new année
	 * @see Annee
	 */
	public Annee getOrCreatAnnee(int idAnnee) {
		for (Annee anneeChercher : this.getListesAnnee()) {
			if (anneeChercher.getAnnee() == idAnnee) {
				return anneeChercher;
			}
		}
		Annee newAnnee = new Annee(idAnnee);
		this.ajoutAnnee(newAnnee);
		return newAnnee;
	}

	/**
	 * Permet d'ajouter une année a la liste des années d'une station
	 * 
	 * @param annee
	 *            : annee
	 * @see Annee
	 */
	public void ajoutAnnee(Annee annee) {
		listesAnnee.add(annee);
	}

	/**
	 * renvoie la liste des releves d'une journée
	 * 
	 * @param anneeChercher
	 *            : annee chercher
	 * @param moisChercher
	 *            : mois chercher
	 * @param jourChercher
	 *            : jour chercher : jour chercher
	 * @return si l'année le jour et le mois existe alors return la liste des
	 *         releves sinon return null
	 * @see Annee
	 * @see Mois
	 * @see Jour
	 */
	public List<Releve> getReleveJour(int anneeChercher, int moisChercher, int jourChercher) {
		for (Annee annee : listesAnnee) {
			if (annee.getAnnee() == anneeChercher) {
				return annee.getReleve(moisChercher, jourChercher);
			}
		}
		return null;
	}

	/**
	 * renvoie un releve d'une année d'un mois une journée et une heur donné
	 * 
	 * @param anneeChercher
	 *            : annee chercher
	 * @param moisChercher
	 *            : mois chercher
	 * @param jourChercher
	 *            : jour chercher
	 * @param horaireChercher
	 *            : horaire chercher
	 * @return si l'anné exist dans la liste d'année le mois existe dans la
	 *         liste de mois le jour existe dans la liste de jour et le releve
	 *         existe dans la liste de releve alors return le releve chercher
	 *         sinon return null
	 */
	public Releve getParNumReleve(int anneeChercher, int moisChercher, int jourChercher, int horaireChercher) {
		for (Annee annee : this.getListesAnnee()) {
			return annee.getParNumReleve(moisChercher, jourChercher, horaireChercher);
		}
		return null;
	}

	/**
	 * renvoie l'id station
	 * 
	 * @return id station
	 */
	public int getIdStation() {
		return idStation;
	}

	/**
	 * renvoie le nom de la station
	 * 
	 * @return mon de la station
	 */
	public String getNomStation() {
		return nomStation;
	}

	/**
	 * renvoie la liste des années
	 * 
	 * @return liste des d'années
	 */
	public List<Annee> getListesAnnee() {
		return listesAnnee;
	}

	// -------------------------------------------//
	// ---------- fonction de calcul ------------//
	// -----------------------------------------//

	/**
	 * renvoie la liste des moyenne des mois d'une année donné en parametre
	 * 
	 * @param anneeChercher
	 *            : annee chercher
	 * @return liste des moyenne des mois d'une année donné en parametre
	 * @see Annee
	 */
	public List<Releve> getMoyenneParMois(int anneeChercher) {
		for (Annee annee : this.getListesAnnee()) {
			if (annee.getAnnee() == anneeChercher) {
				return annee.getMoyenneParMois();
			}
		}
		return null;
	}

	/**
	 * renvoie la liste des Ecart Type d'une année donné en parametre
	 * 
	 * @param anneeChercher
	 *            : annee chercher
	 * @return liste des Ecart Type d'une année donné en parametre
	 * @see Annee
	 */
	public List<Releve> getEcartTypeParMois(int anneeChercher) {
		for (Annee annee : this.getListesAnnee()) {
			if (annee.getAnnee() == anneeChercher) {
				return annee.getEcartTypeMois();
			}
		}
		return null;
	}

	/**
	 * renvoie la liste des MAX d'une année donné en parametre
	 * 
	 * @param anneeChercher
	 *            : annee chercher
	 * @return liste des MAX d'une année donné en parametre
	 * @see Annee
	 */
	public List<Releve> getMaxParMois(int anneeChercher) {
		for (Annee annee : this.getListesAnnee()) {
			if (annee.getAnnee() == anneeChercher) {
				return annee.getMaxParMois();
			}
		}
		return null;
	}

	/**
	 * renvoie la liste des MIN d'une année donné en parametre
	 * 
	 * @param anneeChercher
	 *            : annee chercher
	 * @return liste des MIN d'une année donné en parametre
	 * @see Annee
	 */
	public List<Releve> getMinParMois(int anneeChercher) {
		for (Annee annee : this.getListesAnnee()) {
			if (annee.getAnnee() == anneeChercher) {
				return annee.getMinParMois();
			}
		}
		return null;
	}

	/**
	 * renvoie la liste des moyenne d'un mois et d'une année donné
	 * 
	 * @param anneeChercher
	 *            : annee chercher
	 * @param moisChercher
	 *            : mois chercher
	 * @return liste des moyenne d'un mois et d'une année donné
	 * @see Annee
	 * @see Mois
	 */
	public List<Releve> getMoyenneParJour(int anneeChercher, int moisChercher) {
		for (Annee annee : this.getListesAnnee()) {
			if (annee.getAnnee() == anneeChercher) {
				return annee.getMoyenneParJour(moisChercher);
			}
		}
		return null;
	}

	/**
	 * renvoie la liste des Ecart Type d'un mois et d'une année donné en
	 * parametre
	 * 
	 * @param anneeChercher
	 *            : annee chercher
	 * @param moisChercher
	 *            : mois chercher
	 * @return liste des Ecart Type d'un mois et d'une année donné en parametre
	 * @see Annee
	 * @see Mois
	 */
	public List<Releve> getEcartTypeParJour(int anneeChercher, int moisChercher) {
		for (Annee annee : this.getListesAnnee()) {
			if (annee.getAnnee() == anneeChercher) {
				return annee.getEcartTypeParJour(moisChercher);
			}
		}
		return null;
	}

	/**
	 * renvoie la liste des MAX d'un mois et d'une année donné en parametre
	 * 
	 * @param anneeChercher
	 *            : annee chercher
	 * @param moisChercher
	 *            : mois chercher
	 * @return liste des MAX d'un mois et d'une année donné en parametre
	 * @see Annee
	 * @see Mois
	 */
	public List<Releve> getMaxParJour(int anneeChercher, int moisChercher) {
		for (Annee annee : this.getListesAnnee()) {
			if (annee.getAnnee() == anneeChercher) {
				return annee.getMaxParJour(moisChercher);
			}
		}
		return null;
	}

	/**
	 * renvoie la liste des MIN d'un mois et d'une année donné en parametre
	 * 
	 * @param anneeChercher
	 *            : annee chercher
	 * @param moisChercher
	 *            : mois chercher
	 * @return liste des MIN d'un mois et d'une année donné en parametre
	 * @see Annee
	 * @see Mois
	 */
	public List<Releve> getMinParJour(int anneeChercher, int moisChercher) {
		for (Annee annee : this.getListesAnnee()) {
			if (annee.getAnnee() == anneeChercher) {
				return annee.getMinParJour(moisChercher);
			}
		}
		return null;
	}

}
