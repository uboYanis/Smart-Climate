package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;

public class Utilitaire {
	/**
	 * le nom du dossier ou sont mis les fichiers telecharger
	 */
	private final String DOSSIERPRINCIPALE = "C:\\SmartClimate";
	/**
	 * le préfixe de l'URL de telechargement des fichier sur le site meteo
	 * France
	 */
	private final String URLMETEOFRANCE = "https://donneespubliques.meteofrance.fr/donnees_libres/Txt/Synop/Archive/synop.";
	/**
	 * extension des fichier CSV
	 */
	private final String EXTENSIONCSV = ".csv";
	/**
	 * extension des fichier telecharger GZ
	 */
	private final String EXTENSIONGZ = ".gz";

	/**
	 * la date courante
	 */
	LocalDate dateLocal = LocalDate.now();

	/**
	 * renvoie une url de telechargement d'un mois selon la date passer en
	 * parametre
	 * 
	 * @param date:
	 *            format yyyyMM
	 * @return url de telechargement d'une date passer en parametre
	 * @throws MalformedURLException
	 *             si aucun protocole est spécifié, ou un protocole inconnu est
	 *             trouvée, ou spec est nulle .
	 */
	private URL urlTelechargement(String date) throws MalformedURLException {
		return new URL(URLMETEOFRANCE + date.substring(0, 6) + EXTENSIONCSV + EXTENSIONGZ);
	}

	/**
	 * renvoie le chemin ver le fichier GZ telecharger
	 * 
	 * @param date:format
	 *            yyyyMM
	 * @return le chemin ver le fichier GZ telecharger
	 */
	private File pathFile(String date) {
		String path = DOSSIERPRINCIPALE + File.separator + date.substring(0, 4);
		String nomFichier = date.substring(4, 6) + EXTENSIONCSV + EXTENSIONGZ;
		return new File(path, nomFichier);
	}

	/**
	 * renvoie le chemin ver le fichier CSV aprés décompression
	 * 
	 * @param date
	 *            :format yyyyMM
	 * @return le chemin ver le fichier CSV aprés décompression
	 */
	public File newPahtFile(String date) {
		return new File(DOSSIERPRINCIPALE + File.separator + date.substring(0, 4) + File.separator
				+ date.substring(4, 6) + EXTENSIONCSV);
	}

	/**
	 * renvoie le chemin ver le dossier qui porte le nom de l'année du fichier
	 * CSV telecharger por mieux organiser les fichier
	 * 
	 * @param date:format
	 *            yyyy
	 * @return le chemin ver le dossier qui porte le nom de l'année du fichier
	 *         CSV telecharger
	 */
	private File pathFolder(String date) {
		return new File(DOSSIERPRINCIPALE + File.separator + date.substring(0, 4));
	}

	/**
	 * permet de telecharger les fichier CSV selon la date passer en parametre
	 * ainsi que decompresser le fichier GZ puis les supprimer et garder que les
	 * fichiers CSV
	 * 
	 * @param date
	 *            : format yyyyMMdd
	 * @throws Exception
	 *             : s' il y a un probleme de connexion
	 */
	public void telecharger(String date) throws Exception {
		FileUtils.copyURLToFile(urlTelechargement(date), pathFile(date));
		decompresserGZ(date);
		pathFile(date).delete();
	}

	/**
	 * permet de telecharger tout les moi manquant d'une année
	 * 
	 * @param date
	 *            : format yyyy
	 * @param moisManquant
	 *            : liste des mois manquant
	 * @throws Exception
	 *             s'il y a un probleme de connexion
	 */
	public void telechargerAnnee(String date, List<String> moisManquant) throws Exception {
		for (String mois : moisManquant) {
			telecharger(date.substring(0, 4) + mois);
		}
	}

	/**
	 * verifie si un fichier CSV existe
	 * 
	 * @param date
	 *            : format yyyyMM
	 * @return true si le fichier existe sinon si le fichier demander correspond
	 *         a l'année et au mois courant ou si le fichier n'existe pas alors
	 *         return false
	 */
	public boolean isFileExist(String date) {
		if (!parsDateToString(dateLocal.getYear(), dateLocal.getMonthValue(), 00).startsWith(date)) {
			if (pathFolder(date).exists()) {
				File[] listesFichier = pathFolder(date).listFiles();
				for (File fileCourant : listesFichier) {
					if (fileCourant.getName().startsWith(date.substring(4, 6))) {
						return true;

					}
				}
			}
		}
		return false;
	}

	/**
	 * renvoie la liste des nom de fichiers des mois manquant pour une année
	 * donné en parametre
	 * 
	 * @param date
	 *            : format yyyy
	 * @return la liste des nom de fichiers des mois manquant pour une année
	 *         donné en parametre
	 * @throws Exception
	 *             : probleme E/S
	 */
	public List<String> FileMissingAnnee(String date) throws Exception {
		List<String> moisManquant = new ArrayList<String>();
		int nombreMois = (dateLocal.getYear() == Integer.parseInt(date.substring(0, 4))) ? dateLocal.getMonthValue()
				: 12;
		for (int mois = 1; mois <= nombreMois; mois++) {
			if (!isFileExist(date.substring(0, 4) + parsIntToString(mois))) {
				moisManquant.add(parsIntToString(mois));
			}
		}
		return moisManquant;
	}

	/**
	 * permet de decompresser les fichier GZ
	 * 
	 * @param date:
	 *            format yyyyMM
	 * @throws Exception
	 *             si le fichier n'existe pas
	 */
	private void decompresserGZ(String date) throws Exception {
		GZIPInputStream in = null;
		OutputStream out = null;
		in = new GZIPInputStream(new FileInputStream(pathFile(date)));
		out = new FileOutputStream(newPahtFile(date));
		byte[] buf = new byte[1024 * 4];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		if (in != null)
			in.close();

		if (out != null)
			out.close();

	}

	/**
	 * permet de convertir un int to string (ex : pour convertir id d'une
	 * station en String est garder le 0 au début pour facilité la recherche )
	 * 
	 * @param id
	 *            : id station
	 * @return id converti en String
	 */
	public String parsIntToString(int id) {
		if (0 < id && id < 10 || 7004 < id && id < 7791) {
			return "0" + String.valueOf(id);
		}
		if (id == 00) {
			return "";
		}
		return String.valueOf(id);
	}

	/**
	 * renvoie une date de type String sous format yyyyMMdd
	 * 
	 * @param annee
	 *            : annee
	 * @param mois
	 *            : mois
	 * @param jour
	 *            : jour
	 * @return une date de type String sous format yyyyMMdd
	 */
	public String parsDateToString(int annee, int mois, int jour) {
		return parsIntToString(annee) + parsIntToString(mois) + parsIntToString(jour);
	}
}
