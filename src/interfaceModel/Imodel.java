package interfaceModel;

import java.util.List;
import java.util.Map;

import model.Releve;

/**
 * <p>
 * cette interface permet a l'utillisateur de récupérer un releve a une heur
 * d'une journée ,la liste des releve d'une année ,la liste des releve d'un mois
 * , la liste des releve d'une journée
 * </p>
 * <p>
 * et récupérer la map des station et le nom de la station a partir de son id ou
 * l'id a partir de son nom
 * </p>
 * 
 * @author REMILA Yanis
 * @version 1.1
 *
 */
public interface Imodel {

	/**
	 * renvoie un releve d'une journée aprés avoir vérifier si la journée
	 * demander n'existe pas deja dans la liste des journées si oui elle revoie
	 * un releve sinon elle vérifie si le fichier correspondant a ce jour existe
	 * si oui elle construit les donnée puis renvoie un relevé sinon s'il
	 * n'existe pas elle telecharge le fichier puis elle construit les données
	 * puis renvoie un releve
	 * 
	 * @param stationChercher
	 *            : station chercher
	 * @param anneeChercher
	 *            : annee chercher
	 * @param moisChercher
	 *            : mois chercher
	 * @param jourChercher
	 *            : jour chercher
	 * @param horaireChercher
	 *            : horaire chercher
	 * @return un releve d'un horaire
	 * @throws Exception
	 *             : s'il y a un probleme de connexion
	 */
	public Releve donneeHoraire(int stationChercher, int anneeChercher, int moisChercher, int jourChercher,
			int horaireChercher) throws Exception;

	/**
	 * renvoie la liste des releves d'une journée aprés avoir vérifier si la
	 * journée demander n'existe pas deja dans la liste des journées si oui elle
	 * revoie la liste de releve sinon elle vérifie si le fichier correspondant
	 * a ce jour existe si oui elle construit les donnée puis renvoie la liste
	 * des relevé sinon s'il n'existe pas elle telecharge le fichier puis elle
	 * construit les données puis renvoie la liste des releves
	 * 
	 * @param stationChercher
	 *            : station chercher
	 * @param anneeChercher
	 *            : annee chercher
	 * @param moisChercher
	 *            : mois chercher
	 * @param jourChercher
	 *            : jour chercher
	 * @return la liste des releve d'une journée
	 * @throws Exception
	 *             : s'il y a un probleme de connexion
	 */
	public List<Releve> donneeJour(int stationChercher, int anneeChercher, int moisChercher, int jourChercher)
			throws Exception;

	/**
	 * renvoie la liste des releves d'un mois aprés avoir vérifier si le mois
	 * demander n'existe pas deja dans la liste des mois si oui elle revoie la
	 * liste de releve sinon elle vérifie si le fichier correspondant a ce mois
	 * existe si oui elle construit les donnée puis renvoie la liste des relevé
	 * sinon s'il n'existe pas elle telecharge le fichier puis elle construit
	 * les données puis renvoie la liste des releves
	 * 
	 * @param stationChercher
	 *            : station chercher
	 * @param anneeChercher
	 *            : annee chercher
	 * @param moisChercher
	 *            : mois chercher
	 * @param operation
	 *            : operation chercher
	 * @return la liste des releve d'un mois
	 * @throws Exception
	 *             : s'il y a un probleme de connexion
	 */
	public List<Releve> donneeMois(int stationChercher, int anneeChercher, int moisChercher, int operation)
			throws Exception;

	/**
	 * renvoie la liste des releves d'une année aprés avoir vérifier si l'année
	 * demander n'existe pas deja dans la liste d'année si oui elle revoie la
	 * liste de releve sinon elle vérifie si le fichier correspondant a cette
	 * année existe si oui elle construit les donnée puis renvoie la liste des
	 * relevé sinon s'il n'existe pas elle telecharge le fichier puis elle
	 * construit les données puis renvoie la liste des releves
	 * 
	 * @param stationChercher
	 *            : station chercher
	 * @param anneeChercher
	 *            : annee chercher
	 * @param operation
	 *            : operation chercher
	 * @return la liste des releve d'une année
	 * @throws Exception
	 *             : s'il y a un probleme de connexion
	 */
	public List<Releve> donneeAnnee(int stationChercher, int anneeChercher, int operation) throws Exception;

	/**
	 * renvoie le nom d'une station qui correspond a l'id passé en parametre
	 * 
	 * @param idStation
	 *            : id station
	 * @return le nom d'une station qui correspond a l'id passé en parametre
	 */
	public String getNomMapStation(int idStation);

	/**
	 * renvoie la liste des station
	 * 
	 * @return la liste des station
	 */
	public Map<Integer, String> getMapStation();

	/**
	 * renvoie l'id d'une station qui correspond a le nom station passé en
	 * parametre
	 * 
	 * @param nomStation
	 *            : nom station
	 * @return l'id d'une station qui correspond a le nom station passé en
	 *         parametre
	 */
	public Integer getidMapStation(String nomStation);

}
