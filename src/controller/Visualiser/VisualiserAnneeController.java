package controller.Visualiser;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import boiteModale.BoiteModale;
import interfaceModel.Imodel;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import model.Model;
import model.Releve;
import utilitaireController.Graph;
import utilitaireController.UtilitaireController;

/**
 * cette class permet de visualiser l'évolution des releve sur une durée d'une
 * année dans un graphe
 * 
 * @author REMILA Yanis
 * @version 1.2
 *
 */
public class VisualiserAnneeController implements Initializable {

	ObservableList<XYChart.Series<String, Number>> obsList = FXCollections.observableArrayList();
	final CategoryAxis xAxis = new CategoryAxis();
	final NumberAxis yAxis = new NumberAxis();
	@FXML
	private LineChart<String, Number> chartAnnee = new LineChart<>(xAxis, yAxis, obsList);

	@FXML
	private ChoiceBox<String> Station, operation, parametres;
	@FXML
	private ChoiceBox<Integer> annee;
	@FXML
	private Label messageProgression;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private Button bntVisualiser;
	@FXML
	private CheckBox check;

	XYChart.Series<String, Number> series = new XYChart.Series<>();
	UtilitaireController initialiserVue = new UtilitaireController();
	BoiteModale boiteModale = new BoiteModale();
	LocalDate dateLocal = LocalDate.now();
	Imodel model = new Model();
	Thread monThread;

	/**
	 * cette methode permet lancé une tache dans un thread et cette tache a pour
	 * role de récupérer la liste de releve d'une année et d'afficher une
	 * series(courbe) dans un graph et suivre l'évolution du chargement des
	 * données sur un progresse bar
	 * 
	 * @param stationChercher
	 *            : station chercher
	 * @param dateChercher
	 *            : date Chercher
	 * @param operation
	 *            : moyenne / max / min
	 * @param parametre
	 *            : 1 pour convertir en celsius ,sinon kelvin
	 */
	private void tacheDonneeAnnee(int stationChercher, int dateChercher, int operation, int parametre) {
		chartAnnee.getData().clear();
		Task<ObservableList<Releve>> tache = new Task<ObservableList<Releve>>() {
			@Override
			protected ObservableList<Releve> call() throws Exception {
				try {
					List<Releve> listDonneeAnnee = model.donneeAnnee(stationChercher, dateChercher, operation);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if (parametre == 1) {
								series = Graph.createSerie(Graph.converteToCel(listDonneeAnnee), parametre, 1, "Année");
							} else {
								series = Graph.createSerie(listDonneeAnnee, parametre, 1, "Année");
							}
							chartAnnee.getData().add(series);
						}
					});
					updateProgress(1, 1);
					updateMessage("chargement terminer");
				} catch (Exception e) {
					updateMessage(e.getMessage());
					updateProgress(0, 0);
					boiteModale.information("erreur", e.getMessage());
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
	 * btn visualiser
	 * 
	 * @param event
	 *            : event
	 */
	@FXML
	void visualiserAnnee(ActionEvent event) {
		int stationObtenue = model.getidMapStation(Station.getValue());
		int anneeObtenue = annee.getValue();
		int operationObtenue = operation.getSelectionModel().getSelectedIndex() + 1;
		int parametreObtenue = parametres.getSelectionModel().getSelectedIndex() + 1;
		tacheDonneeAnnee(stationObtenue, anneeObtenue, operationObtenue, parametreObtenue);
	}

	/**
	 * permet ce crée une relation maitre esclave entre le btn visualiser et les
	 * autres champs de l'IHM tel que le bntVisualiser reste désactiver tant que
	 * tout les champs ne soit pas remplit et que le checkBox ne soit pas
	 * selectionner pour dire que les listener sont désactiver
	 */
	public void desableBntVisualiser() {
		BooleanBinding bindAfficheAnnee = new BooleanBinding() {
			{
				super.bind(Station.valueProperty(), annee.valueProperty(), operation.valueProperty(),
						parametres.valueProperty(), check.selectedProperty());
			}

			@Override
			protected boolean computeValue() {
				if (Station.getValue() != null && annee.getValue() != null && operation.getValue() != null
						&& parametres.getValue() != null && check.isSelected()) {
					return false;
				}
				return true;
			}
		};
		bntVisualiser.disableProperty().bind(bindAfficheAnnee);
	}

	/**
	 * vérifie si tout les champs que l'IHM son non vide
	 * 
	 * @return false si tout les champs non vide sinon true
	 */
	private Boolean isChoiceBoxEmpty() {
		if (Station.getValue() != null && annee.getValue() != null && operation.getValue() != null
				&& parametres.getValue() != null && !check.isSelected()) {
			return false;
		}
		return true;
	}

	/**
	 * cette methode notifie le checkBox station de sort que lorsque il change
	 * de valeur si tout les champs sont rempli la methode tacheDonneeAnnee sera
	 * appelé avec la nouvelle valeur du champ
	 */
	private void obsChoiceBoxStation() {
		Station.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeAnnee(model.getidMapStation(newValue), annee.getValue(),
							operation.getSelectionModel().getSelectedIndex() + 1,
							parametres.getSelectionModel().getSelectedIndex() + 1);
				}

			}
		});
	}

	/**
	 * cette methode notifie le checkBox annee de sort que lorsque il change de
	 * valeur si tout les champs sont rempli la methode tacheDonneeAnnee sera
	 * appelé avec la nouvelle valeur du champ
	 */
	private void obsChoiceBoxAnnee() {
		annee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeAnnee(model.getidMapStation(Station.getValue()), newValue,
							operation.getSelectionModel().getSelectedIndex() + 1,
							parametres.getSelectionModel().getSelectedIndex() + 1);
				}

			}
		});
	}

	/**
	 * cette methode notifie le checkBox operation de sort que lorsque il change
	 * de valeur si tout les champs sont rempli la methode tacheDonneeAnnee sera
	 * appelé avec la nouvelle valeur du champ
	 */
	private void obsChoiceBoxOperation() {
		operation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeAnnee(model.getidMapStation(Station.getValue()), annee.getValue(),
							initialiserVue.SelectedIndexOperation(newValue),
							parametres.getSelectionModel().getSelectedIndex() + 1);
				}

			}
		});
	}

	/**
	 * cette methode notifie le checkBox parametre de sort que lorsque il change
	 * de valeur si tout les champs sont rempli la methode tacheDonneeAnnee sera
	 * appelé avec la nouvelle valeur du champ
	 */
	private void obsChoiceBoxParametre() {
		parametres.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeAnnee(model.getidMapStation(Station.getValue()), annee.getValue(),
							operation.getSelectionModel().getSelectedIndex() + 1,
							initialiserVue.SelectedIndexParametre(newValue));
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

		initialiserVue.initListeStation(Station);
		initialiserVue.initListeAnnee(annee);
		initialiserVue.initListeOperation(operation);
		initialiserVue.initListeParametre(parametres);
		initialiserVue.checkBox(check);
		desableBntVisualiser();

		obsChoiceBoxStation();
		obsChoiceBoxAnnee();
		obsChoiceBoxOperation();
		obsChoiceBoxParametre();
		check.setText("Listener\nActiver");
	}

}
