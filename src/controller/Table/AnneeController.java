package controller.Table;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import boiteModale.BoiteModale;
import interfaceModel.Imodel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Model;
import model.Releve;
import utilitaireController.UtilitaireController;

/**
 * cette class permet d'afficher la liste des releve sur une durée d'une année
 * dans un tableau
 * 
 * @author REMILA Yanis
 * @version 1.2
 *
 */
public class AnneeController implements Initializable {
	@FXML
	private TableColumn<Releve, Float> colTemperature, colHumidite, colNebulosite;
	@FXML
	private TableColumn<Releve, Integer> colHoraire;
	@FXML
	private TableView<Releve> tableView;

	@FXML
	private ChoiceBox<String> operation, Station;
	@FXML
	private Label messageProgression;
	@FXML
	private ChoiceBox<Integer> annee;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private Button afficherAnnee;
	@FXML
	private CheckBox check;

	ObservableList<Releve> listeAttendue = FXCollections.observableArrayList();
	UtilitaireController initialiserVue = new UtilitaireController();
	BoiteModale boiteModale = new BoiteModale();
	LocalDate dateLocal = LocalDate.now();
	Imodel model = new Model();
	Thread monThread;

	/**
	 * cette methode permet lancé une tache dans un thread et cette tache a pour
	 * role de récupérer la liste de releve d'une année et de les afficher dans
	 * un tableau et suivre l'évolution du chargement des données sur un
	 * progresse bar
	 * 
	 * @param stationChercher
	 *            : station charcher
	 * @param dateChercher
	 *            : date chercher
	 * @param operation
	 *            : operation
	 */
	private void tacheDonneeAnnee(int stationChercher, int dateChercher, int operation) {
		Task<ObservableList<Releve>> tache = new Task<ObservableList<Releve>>() {
			@Override
			protected ObservableList<Releve> call() throws Exception {
				listeAttendue.clear();
				try {
					List<Releve> listDonneeAnnee = model.donneeAnnee(stationChercher, dateChercher, operation);
					listeAttendue.addAll(listDonneeAnnee);
					tableView.setItems((ObservableList<Releve>) listeAttendue);
					updateProgress(1, 1);
					updateMessage("Chargement terminer");
				} catch (Exception e) {
					updateMessage(e.getMessage());
					updateProgress(0, 0);
					boiteModale.erreur("erreur", e.getMessage());
				}
				;
				return null;
			}
		};
		initialiserVue.lancerThread(monThread, tache, messageProgression, progressBar);
	}

	/**
	 * permet de récuperer les valeurs des champs sélectionner dans l'IHM et les
	 * passer en parametres a la methode tacheDonneeAnnee lors du clic sur le
	 * btn afficherAnnee
	 * 
	 * @param event
	 *            : event
	 */
	@FXML
	void afficherAnnee(ActionEvent event) {
		int station = model.getidMapStation(Station.getValue());
		int date = annee.getValue();
		int op = operation.getSelectionModel().getSelectedIndex() + 1;
		tacheDonneeAnnee(station, date, op);
	}

	/**
	 * vérifie si tout les champs de l'IHM sont non vide
	 * 
	 * @return false si tout les champs non vide sinon true
	 */
	private Boolean isChoiceBoxEmpty() {
		if (Station.getValue() != null && annee.getValue() != null && operation.getValue() != null
				&& !check.isSelected()) {
			return false;
		}
		return true;
	}

	/**
	 * permet ce crée une relation maitre esclave entre le btn afficherAnnee et
	 * les autres champs de l'IHM tel que le btn afficherAnnee reste désactiver
	 * tant que tout les champs ne soit pas remplit et que le checkBox ne soit
	 * pas selectionner pour dire que les listener sont désactiver
	 */
	public void desableBntAnnee() {
		BooleanBinding bindAfficheAnnee = new BooleanBinding() {
			{
				super.bind(Station.valueProperty(), annee.valueProperty(), operation.valueProperty(),
						check.selectedProperty());
			}

			@Override
			protected boolean computeValue() {
				if (Station.getValue() != null && annee.getValue() != null && operation.getValue() != null
						&& check.isSelected()) {
					return false;
				}
				return true;
			}
		};
		afficherAnnee.disableProperty().bind(bindAfficheAnnee);
	}

	/**
	 * cette methode notifie le checkBox station de sort que lorsque il change
	 * de valeur si tout les champs sont rempli la methode tacheDonneeAnnee sera
	 * appelé avec la nouvelle valeur du champ
	 */
	public void obsChoiceBoxStation() {
		Station.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeAnnee(model.getidMapStation(newValue), annee.getValue(),
							operation.getSelectionModel().getSelectedIndex() + 1);
				}

			}
		});
	}

	/**
	 * cette methode notifie le checkBox annee de sort que lorsque il change de
	 * valeur si tout les champs sont rempli la methode tacheDonneeAnnee sera
	 * appelé avec la nouvelle valeur du champ
	 */
	public void obsChoiceBoxAnnee() {
		annee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeAnnee(model.getidMapStation(Station.getValue()), newValue,
							operation.getSelectionModel().getSelectedIndex() + 1);
				}

			}
		});

	}

	/**
	 * cette methode notifie le checkBox operation de sort que lorsque il change
	 * de valeur si tout les champs sont rempli la methode tacheDonneeAnnee sera
	 * appelé avec la nouvelle valeur du champ
	 */
	public void obsChoiceBoxOperation() {
		operation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeAnnee(model.getidMapStation(Station.getValue()), annee.getValue(),
							initialiserVue.SelectedIndexOperation(newValue));
				}
			}

		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initialiserVue.initTableView(colHoraire, colTemperature, colHumidite, colNebulosite);
		initialiserVue.initListeStation(Station);
		initialiserVue.initListeAnnee(annee);
		initialiserVue.initListeOperation(operation);
		initialiserVue.checkBox(check);
		desableBntAnnee();
		obsChoiceBoxStation();
		obsChoiceBoxAnnee();
		obsChoiceBoxOperation();
		check.setText("Listener\nActiver");

	}

}
