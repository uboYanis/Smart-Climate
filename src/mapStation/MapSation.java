package mapStation;

import java.util.HashMap;
import java.util.Map;

/**
 * cette class permet d'avoir une HashMap des station avec leurs id et leurs nom
 * et d'avoir une station a partir de son id ou de son nom
 * 
 * @author REMILA Yanis
 * @version 1.3
 */
public final class MapSation {
	private static Map<Integer, String> stationMap = new HashMap<>();

	/**
	 * renvoie le nom de la station dans on a passer l'id en parametre
	 * 
	 * @param id
	 *            : id de la station
	 * @return le nom de la station dans on a passer l'id en parametre
	 */
	public static String getNomStation(int id) {
		for (Map.Entry<Integer, String> station : stationMap.entrySet()) {
			if (station.getKey() == id) {
				return station.getValue();
			}
		}
		return null;
	}

	/**
	 * renvoie l'id de la station dans on a passé le nom on parametre
	 * 
	 * @param nomStation
	 *            nom station
	 * @return l'id de la station dans on a passé le nom on parametre
	 */
	public static Integer getIdStation(String nomStation) {
		for (Map.Entry<Integer, String> station : stationMap.entrySet()) {
			if (station.getValue() == nomStation) {
				return station.getKey();
			}
		}
		return null;
	}

	/**
	 * renvoie la Map(id de la station, nom de la station )
	 * 
	 * @return la Map(id de la station, nom de la station )
	 */
	public static Map<Integer, String> getStationMap() {
		return stationMap;
	}

	/**
	 * permet d'jouter des station a la Map
	 * 
	 * @param id:id
	 *            station
	 * @param nom:
	 *            nom station
	 */
	private static void setMap(int id, String nom) {
		stationMap.put(id, nom);
	}

	/**
	 * permet de remplir la map avec les id et nom de station
	 */
	public static void remplirMap() {
		setMap(7005, "ABBEVILLE");
		setMap(7015, "LILLE-LESQUIN");
		setMap(7020, "PTE DE LA HAGUE");
		setMap(7027, "CAEN-CARPIQUET");
		setMap(7037, "ROUEN-BOOS");
		setMap(7072, "REIMS-PRUNAY");
		setMap(7110, "BREST-GUIPAVAS");
		setMap(7117, "PLOUMANAC'H");
		setMap(7130, "RENNES-ST JACQUES");
		setMap(7139, "ALENCON");
		setMap(7149, "ORLY");
		setMap(7168, "TROYES-BARBEREY");
		setMap(7181, "NANCY-OCHEY");
		setMap(7190, "STRASBOURG-ENTZHEIM");
		setMap(7207, "BELLE ILE-LE TALUT");
		setMap(7222, "NANTES-BOUGUENAIS");
		setMap(7240, "TOURS");
		setMap(7255, "BOURGES");
		setMap(7280, "DIJON-LONGVIC");
		setMap(7299, "BALE-MULHOUSE");
		setMap(7314, "PTE DE CHASSIRON");
		setMap(7335, "POITIERS-BIARD");
		setMap(7434, "LIMOGES-BELLEGARDE");
		setMap(7460, "CLERMONT-FD");
		setMap(7471, "LE PUY-LOUDES");
		setMap(7481, "LYON-ST EXUPERY");
		setMap(7510, "BORDEAUX-MERIGNAC");
		setMap(7535, "GOURDON");
		setMap(7558, "MILLAU");
		setMap(7577, "MONTELIMAR");
		setMap(7591, "EMBRUN");
		setMap(7607, "MONT-DE-MARSAN");
		setMap(7621, "TARBES-OSSUN");
		setMap(7627, "ST GIRONS");
		setMap(7630, "TOULOUSE-BLAGNAC");
		setMap(7643, "MONTPELLIER");
		setMap(7650, "MARIGNANE");
		setMap(7661, "CAP CEPET");
		setMap(7690, "NICE");
		setMap(7747, "PERPIGNAN");
		setMap(7761, "AJACCIO");
		setMap(7790, "BASTIA");
		setMap(61968, "GLORIEUSES");
		setMap(61976, "TROMELIN");
		setMap(61980, "GILLOT-AEROPORT");
		setMap(61996, "NOUVELLE AMSTERDAM");
		setMap(61997, "CROZET");
		setMap(61998, "KERGUELEN");
		setMap(67005, "PAMANDZI");
		setMap(71805, "ST-PIERRE");
		setMap(78890, "LA DESIRADE METEO");
		setMap(78894, "ST-BARTHELEMY METEO");
		setMap(78897, "LE RAIZET AERO");
		setMap(78922, "TRINITE-CARAVEL");
		setMap(78925, "LAMENTIN-AERO");
		setMap(81401, "SAINT LAURENT");
		setMap(81405, "CAYENNE-MATOURY");
		setMap(81408, "SAINT GEORGES");
		setMap(81415, "MARIPASOULA");
		setMap(89642, "DUMONT D'URVILLE");
		setMap(61972, "EUROPA");
	}
}
