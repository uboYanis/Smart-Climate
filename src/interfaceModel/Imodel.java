package interfaceModel;

import java.util.List;
import java.util.Map;

import model.Releve;

/**
 * <p>
 * cette interface permet a l'utillisateur de r�cup�rer un releve a une heur
 * d'une journ�e ,la liste des releve d'une ann�e ,la liste des releve d'un mois
 * , la liste des releve d'une journ�e
 * </p>
 * <p>
 * et r�cup�rer la map des station et le nom de la station a partir de son id ou
 * l'id a partir de son nom
 * </p>
 * 
 * @author REMILA Yanis
 * @version 1.1
 *
 */
public interface Imodel {

	/**
	 * renvoie un releve d'une journ�e apr�s avoir v�rifier si la journ�e
	 * demander n'existe pas deja dans la liste des journ�es si oui elle revoie
	 * un releve sinon elle v�rifie si le fichier correspondant a ce jour existe
	 * si oui elle construit les donn�e puis renvoie un relev� sinon s'il
	 * n'existe pas elle telecharge le fichier puis elle construit les donn�es
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
	 * renvoie la liste des releves d'une journ�e apr�s avoir v�rifier si la
	 * journ�e demander n'existe pas deja dans la liste des journ�es si oui elle
	 * revoie la liste de releve sinon elle v�rifie si le fichier correspondant
	 * a ce jour existe si oui elle construit les donn�e puis renvoie la liste
	 * des relev� sinon s'il n'existe pas elle telecharge le fichier puis elle
	 * construit les donn�es puis renvoie la liste des releves
	 * 
	 * @param stationChercher
	 *            : station chercher
	 * @param anneeChercher
	 *            : annee chercher
	 * @param moisChercher
	 *            : mois chercher
	 * @param jourChercher
	 *            : jour chercher
	 * @return la liste des releve d'une journ�e
	 * @throws Exception
	 *             : s'il y a un probleme de connexion
	 */
	public List<Releve> donneeJour(int stationChercher, int anneeChercher, int moisChercher, int jourChercher)
			throws Exception;

	/**
	 * renvoie la liste des releves d'un mois apr�s avoir v�rifier si le mois
	 * demander n'existe pas deja dans la liste des mois si oui elle revoie la
	 * liste de releve sinon elle v�rifie si le fichier correspondant a ce mois
	 * existe si oui elle construit les donn�e puis renvoie la liste des relev�
	 * sinon s'il n'existe pas elle telecharge le fichier puis elle construit
	 * les donn�es puis renvoie la liste des releves
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
	 * renvoie la liste des releves d'une ann�e apr�s avoir v�rifier si l'ann�e
	 * demander n'existe pas deja dans la liste d'ann�e si oui elle revoie la
	 * liste de releve sinon elle v�rifie si le fichier correspondant a cette
	 * ann�e existe si oui elle construit les donn�e puis renvoie la liste des
	 * relev� sinon s'il n'existe pas elle telecharge le fichier puis elle
	 * construit les donn�es puis renvoie la liste des releves
	 * 
	 * @param stationChercher
	 *            : station chercher
	 * @param anneeChercher
	 *            : annee chercher
	 * @param operation
	 *            : operation chercher
	 * @return la liste des releve d'une ann�e
	 * @throws Exception
	 *             : s'il y a un probleme de connexion
	 */
	public List<Releve> donneeAnnee(int stationChercher, int anneeChercher, int operation) throws Exception;

	/**
	 * renvoie le nom d'une station qui correspond a l'id pass� en parametre
	 * 
	 * @param idStation
	 *            : id station
	 * @return le nom d'une station qui correspond a l'id pass� en parametre
	 */
	public String getNomMapStation(int idStation);

	/**
	 * renvoie la liste des station
	 * 
	 * @return la liste des station
	 */
	public Map<Integer, String> getMapStation();

	/**
	 * renvoie l'id d'une station qui correspond a le nom station pass� en
	 * parametre
	 * 
	 * @param nomStation
	 *            : nom station
	 * @return l'id d'une station qui correspond a le nom station pass� en
	 *         parametre
	 */
	public Integer getidMapStation(String nomStation);

}
