package controller.Index;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

/**
 * cette class permet de gerer l'affichage des différente vue
 * 
 * @author REMILA Yanis
 * @version 1.4
 *
 */
public class IndexController implements Initializable {

	@FXML
	private TabPane tabPane;
	@FXML
	private Label label;

	/**
	 * vérifie si la vue demander existe deja dans la liste de Tab si elle
	 * existe alors elle renvoie la vue existant sinon elle crée une tab
	 * (englet) et renvoie la vue demander
	 * 
	 * @param root
	 *            : le chemain ver le fichier FXML
	 * @param titre
	 *            : le titre de la vue
	 * @return
	 */
	private Tab getOrCreateTab(String root, String titre) {
		label.setText(null);
		for (Tab tab : tabPane.getTabs()) {
			if (tab.getText() == titre) {
				return tab;
			}
		}
		try {
			Node node = (AnchorPane) FXMLLoader.load(getClass().getResource(root));
			Tab tab = new Tab(titre, node);
			tabPane.getTabs().add(tab);
			return tab;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * affiche la vue Tableau Année
	 * 
	 * @param event:
	 *            event
	 */
	@FXML
	void afficheTableauAnnee(ActionEvent event) {
		Tab tabAnnee = getOrCreateTab("/view/Table/TableAnnee.fxml", "Tableau Année");
		tabPane.getSelectionModel().select(tabAnnee);
	}

	/**
	 * affiche la vue Tableau Mois
	 * 
	 * @param event:
	 *            event
	 */
	@FXML
	void afficheTableauMois(ActionEvent event) {
		Tab tabMois = getOrCreateTab("/view/Table/TableMois.fxml", "Tableau Mois");
		tabPane.getSelectionModel().select(tabMois);
	}

	/**
	 * affiche la vue Tableau Jour
	 * 
	 * @param even:
	 *            event
	 */
	@FXML
	void afficheTableauJour(ActionEvent even) {
		Tab tabJour = getOrCreateTab("/view/Table/TableJour.fxml", "Tableau Jour");
		tabPane.getSelectionModel().select(tabJour);
	}

	/**
	 * affiche la vue Tableau Heur
	 * 
	 * @param event:
	 *            event
	 */
	@FXML
	void afficheTableauHeur(ActionEvent event) {
		Tab tabHeur = getOrCreateTab("/view/Table/TableHeur.fxml", "Tableau Heur");
		tabPane.getSelectionModel().select(tabHeur);
	}

	/**
	 * affiche la vue Visualiser Année
	 * 
	 * @param event:
	 *            event
	 */
	@FXML
	void VisualiserAnnee(ActionEvent event) {
		Tab tabVisuAnnee = getOrCreateTab("/view/Visualiser/VisualiserAnnee.fxml", "Visualiser Année");
		tabPane.getSelectionModel().select(tabVisuAnnee);
	}

	/**
	 * affiche la vue Visualiser Mois
	 * 
	 * @param event:
	 *            event
	 */
	@FXML
	void VisualiserMois(ActionEvent event) {
		Tab tabVisuMois = getOrCreateTab("/view/Visualiser/VisualiserMois.fxml", "Visualiser Mois");
		tabPane.getSelectionModel().select(tabVisuMois);
	}

	/**
	 * affiche la vue Visualiser Jour
	 * 
	 * @param event:
	 *            event
	 */
	@FXML
	void visualiserJour(ActionEvent event) {
		Tab tabVisuJour = getOrCreateTab("/view/Visualiser/VisualiserJour.fxml", "Visualiser Jour");
		tabPane.getSelectionModel().select(tabVisuJour);
	}

	/**
	 * affiche la vue Comparer Année
	 * 
	 * @param event:
	 *            event
	 */
	@FXML
	void comparerAnnee(ActionEvent event) {
		Tab tabCompAnnee = getOrCreateTab("/view/Comparer/ComparerAnnee.fxml", "Comparer Année");
		tabPane.getSelectionModel().select(tabCompAnnee);
	}

	/**
	 * affiche la vue Comparer Mois
	 * 
	 * @param event:
	 *            event
	 */
	@FXML
	void comparerMois(ActionEvent event) {
		Tab tabCompMois = getOrCreateTab("/view/Comparer/ComparerMois.fxml", "Comparer Mois");
		tabPane.getSelectionModel().select(tabCompMois);
	}

	/**
	 * affiche la vue Comparer Jour
	 * 
	 * @param event:
	 *            event
	 */
	@FXML
	void comparerJour(ActionEvent event) {
		Tab tabCompJour = getOrCreateTab("/view/Comparer/ComparerJour.fxml", "Comparer Jour");
		tabPane.getSelectionModel().select(tabCompJour);
	}

	/**
	 * affiche la vue Liste Fichier
	 * 
	 * @param event:
	 *            event
	 */
	@FXML
	void listFichier(ActionEvent event) {
		Tab tabCompJour = getOrCreateTab("/view/ListeFichier/ListeFichier.fxml", "Liste Fichier");
		tabPane.getSelectionModel().select(tabCompJour);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}
}
