package controller.ListeFichier;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * cette class renvoie la liste des sous dossiers et fichiers CSV contenue dans
 * le dossier principale
 * 
 * @author REMILA Yanis
 * @version 1.0
 *
 */
public class ListeFichierController implements Initializable {
	@FXML
	private TreeView<String> tree = new TreeView<>();
	/**
	 * le nom du dossier ou sont mis les fichiers telecharger
	 */
	private final String DOSSIERPRINCIPALE = "C:\\SmartClimate";

	/**
	 * afficher la liste des fichier existant lors du clic sur le btn actualiser
	 * 
	 * @param event
	 *            : event
	 */
	@FXML
	void actualiser(ActionEvent event) {
		tree.setRoot(getNodesForDirectory(new File(DOSSIERPRINCIPALE)));
		tree.setShowRoot(false);
	}

	/**
	 * renvoie la liste des sous dossier et fichier CSV contenue dans le dossier
	 * principale
	 * 
	 * @param directory
	 *            : le dossier qui contient les sous dossiers et les fichiers
	 *            CSV
	 * @return la liste des sous dossier et fichier CSV contenue dans le dossier
	 *         principale
	 */
	private TreeItem<String> getNodesForDirectory(File directory) {
		TreeItem<String> root = new TreeItem<String>(directory.getName());
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				root.getChildren().add(getNodesForDirectory(file));
			} else {
				root.getChildren().add(new TreeItem<String>(file.getName()));
			}
		}
		return root;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tree.setRoot(getNodesForDirectory(new File(DOSSIERPRINCIPALE)));
		tree.setShowRoot(false);
	}

}
