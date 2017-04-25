package controller.Comparer;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
import utilitaireController.SubtractListe;

/**
 * cette class permet de comparer l'évolution des releve de deux années et les
 * afficher dans un graphe
 * 
 * @author REMILA Yanis
 * @version 1.4
 *
 */
public class ComparerAnneeController implements Initializable {

	private final CategoryAxis xAxis = new CategoryAxis();
	private final NumberAxis yAxis = new NumberAxis();
	private ObservableList<XYChart.Series<String, Number>> obsList = FXCollections.observableArrayList();
	@FXML
	private LineChart<String, Number> chartAnnee = new LineChart<>(xAxis, yAxis, obsList);

	@FXML
	private ChoiceBox<String> Station, operation, parametres, comparaison;
	@FXML
	private ChoiceBox<Integer> annee, annee2;
	@FXML
	private Label messageProgression;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private Button bntComparer;
	@FXML
	private CheckBox check;

	List<List<Releve>> listAnnee = new ArrayList<List<Releve>>();
	UtilitaireController initialiserVue = new UtilitaireController();
	BoiteModale boiteModale = new BoiteModale();
	LocalDate dateLocal = LocalDate.now();
	Imodel model = new Model();
	Thread monThread;

	/**
	 * cette methode permet lancé une tache dans un thread et cette tache a pour
	 * role de récupérer les listes de releve deux années et d'afficher leur
	 * series(courbe) on plus de celle de la difference et de l'ecart type dans
	 * un graph et suivre l'évolution du chargement des données sur un progresse
	 * bar
	 * 
	 * @param stationChercher
	 *            : station Chercher
	 * @param annee1
	 *            : annee1
	 * @param annee2
	 *            : annee2
	 * @param operation
	 *            : operation
	 * @param parametre
	 *            : parametre
	 * @param comparer
	 */
	private void tacheDonneeAnnee(int stationChercher, int annee1, int annee2, int operation, int parametre,
			int comparer) {
		chartAnnee.getData().clear();
		listAnnee.clear();
		Task<ObservableList<Releve>> tache = new Task<ObservableList<Releve>>() {
			@Override
			protected ObservableList<Releve> call() throws Exception {
				try {
					List<Releve> listDonneeAnnee1 = model.donneeAnnee(stationChercher, annee1, operation);
					List<Releve> listDonneeAnnee2 = model.donneeAnnee(stationChercher, annee2, operation);
					listAnnee.add(listDonneeAnnee1);
					listAnnee.add(listDonneeAnnee2);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							optionComparer(stationChercher, annee1, annee2, comparer, listDonneeAnnee1,
									listDonneeAnnee2);
							List<XYChart.Series<String, Number>> listeSeries = Graph.createListSerie(listAnnee,
									parametre, comparer, 1, "Année");
							for (XYChart.Series<String, Number> serie : listeSeries) {
								chartAnnee.getData().add(serie);
							}
							updateProgress(1, 1);
							updateMessage("chargement terminer");
							;
						}
					});
				} catch (Exception e) {
					updateMessage(e.getMessage());
					updateProgress(0, 0);
					boiteModale.information("erreur", e.getMessage());
				}
				return null;
			}
		};
		initialiserVue.lancerThread(monThread, tache, messageProgression, progressBar);
	}

	/**
	 * permet d'ajouter des liste a listAnnee selon l'option de comapraison
	 * 
	 * @param stationChercher
	 *            : station chercher
	 * @param annee1
	 *            : annee1
	 * @param annee2
	 *            : annee2
	 * @param comparer
	 *            : option de comparaison
	 * @param list1
	 *            : liste annee1
	 * @param list2
	 *            : liste annee2
	 * @throws Exception
	 *             s'il y a un probleme de connexion
	 */
	private void optionComparer(int stationChercher, int annee1, int annee2, int comparer, List<Releve> list1,
			List<Releve> list2) {
		switch (comparer) {
		case 1:
			try {
				List<Releve> ecartypeAnnee1 = model.donneeAnnee(stationChercher, annee1, 4);
				List<Releve> ecartypeAnnee2 = model.donneeAnnee(stationChercher, annee2, 4);
				listAnnee.add(ecartypeAnnee1);
				listAnnee.add(ecartypeAnnee2);
			} catch (Exception e) {
			}
			break;
		case 2:
			List<Releve> subList = SubtractListe.subtractList(list1, list2);
			listAnnee.add(subList);
			break;
		default:
			break;
		}
	}

	/**
	 * permet de récuperer les valeurs des champs sélectionner dans l'IHM et les
	 * passer en parametres a la methode tacheDonneeAnnee lors du clic sur le
	 * btnComparer
	 * 
	 * @param event
	 *            : event
	 */
	@FXML
	void visualiserAnnee(ActionEvent event) {
		int stationObtenue = model.getidMapStation(Station.getValue());
		int annee1Obtenue = annee.getValue();
		int annee2Obtenue = annee2.getValue();
		int operationObtenue = operation.getSelectionModel().getSelectedIndex() + 1;
		int parametreObtenue = parametres.getSelectionModel().getSelectedIndex() + 1;
		int comparaisonObtenue = comparaison.getSelectionModel().getSelectedIndex() + 1;
		tacheDonneeAnnee(stationObtenue, annee1Obtenue, annee2Obtenue, operationObtenue, parametreObtenue,
				comparaisonObtenue);
	}

	/**
	 * permet ce crée une relation maitre esclave entre le btnComparer et les
	 * autres champs de l'IHM tel que le bntComparer reste désactiver tant que
	 * tout les champs ne soit pas remplit et que le checkBox ne soit pas
	 * selectionner pour dire que les listener sont désactiver
	 */
	public void desableBntComparer() {
		BooleanBinding bindAfficheAnnee = new BooleanBinding() {
			{
				super.bind(Station.valueProperty(), annee.valueProperty(), annee2.valueProperty(),
						operation.valueProperty(), parametres.valueProperty(), comparaison.valueProperty(),
						check.selectedProperty());
			}

			@Override
			protected boolean computeValue() {
				if (Station.getValue() != null && annee.getValue() != null && annee2.getValue() != null
						&& operation.getValue() != null && parametres.getValue() != null
						&& comparaison.getValue() != null && check.isSelected()) {
					return false;
				}
				return true;
			}
		};
		bntComparer.disableProperty().bind(bindAfficheAnnee);
	}

	/**
	 * vérifie si tout les champs que l'IHM son non vide
	 * 
	 * @return false si tout les champs non vide sinon true
	 */
	private Boolean isChoiceBoxEmpty() {
		if (Station.getValue() != null && annee.getValue() != null && annee2.getValue() != null
				&& operation.getValue() != null && parametres.getValue() != null && comparaison.getValue() != null
				&& !check.isSelected()) {
			return false;
		}
		return true;
	}

	/**
	 * cette methode notifie le checkBox annee de sort que lorsque il change de
	 * valeur si tout les champs sont rempli la methode tacheDonneeAnnee sera
	 * appelé avec la nouvelle valeur du champ
	 */
	public void obsChoiceBoxAnnee1() {
		annee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeAnnee(model.getidMapStation(Station.getValue()), newValue, annee2.getValue(),
							operation.getSelectionModel().getSelectedIndex() + 1,
							parametres.getSelectionModel().getSelectedIndex() + 1,
							comparaison.getSelectionModel().getSelectedIndex() + 1);
				}

			}
		});
	}

	/**
	 * cette methode notifie le checkBox annee2 de sort que lorsque il change de
	 * valeur si tout les champs sont rempli la methode tacheDonneeAnnee sera
	 * appelé avec la nouvelle valeur du champ
	 */
	public void obsChoiceBoxAnnee2() {
		annee2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeAnnee(model.getidMapStation(Station.getValue()), annee.getValue(), newValue,
							operation.getSelectionModel().getSelectedIndex() + 1,
							parametres.getSelectionModel().getSelectedIndex() + 1,
							comparaison.getSelectionModel().getSelectedIndex() + 1);
				}

			}
		});
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
					tacheDonneeAnnee(model.getidMapStation(newValue), annee.getValue(), annee2.getValue(),
							operation.getSelectionModel().getSelectedIndex() + 1,
							parametres.getSelectionModel().getSelectedIndex() + 1,
							comparaison.getSelectionModel().getSelectedIndex() + 1);
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
					tacheDonneeAnnee(model.getidMapStation(Station.getValue()), annee.getValue(), annee2.getValue(),
							initialiserVue.SelectedIndexOperation(newValue),
							parametres.getSelectionModel().getSelectedIndex() + 1,
							comparaison.getSelectionModel().getSelectedIndex() + 1);
				}

			}
		});
	}

	/**
	 * cette methode notifie le checkBox parametre de sort que lorsque il change
	 * de valeur si tout les champs sont rempli la methode tacheDonneeAnnee sera
	 * appelé avec la nouvelle valeur du champ
	 */
	public void obsChoiceBoxParametre() {
		parametres.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeAnnee(model.getidMapStation(Station.getValue()), annee.getValue(), annee2.getValue(),
							operation.getSelectionModel().getSelectedIndex() + 1,
							initialiserVue.SelectedIndexParametre(newValue),
							comparaison.getSelectionModel().getSelectedIndex() + 1);

				}

			}
		});
	}

	/**
	 * cette methode notifie le checkBox comparaison de sort que lorsque il
	 * change de valeur si tout les champs sont rempli la methode
	 * tacheDonneeAnnee sera appelé avec la nouvelle valeur du champ
	 */
	public void obsChoiceBoxComparer() {
		comparaison.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!isChoiceBoxEmpty()) {
					tacheDonneeAnnee(model.getidMapStation(Station.getValue()), annee.getValue(), annee2.getValue(),
							operation.getSelectionModel().getSelectedIndex() + 1,
							parametres.getSelectionModel().getSelectedIndex() + 1,
							initialiserVue.SelectedIndexComparer(newValue));

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
	public void initialize(URL arg0, ResourceBundle arg1) {
		initialiserVue.initListeStation(Station);
		initialiserVue.initListeAnnee(annee);
		initialiserVue.initListeAnnee(annee2);
		initialiserVue.initListeParametre(parametres);
		initialiserVue.initListeOperation(operation);
		initialiserVue.initListeComparaison(comparaison, false);
		initialiserVue.checkBox(check);
		check.setText("Listener\nActiver");

		desableBntComparer();
		obsChoiceBoxAnnee1();
		obsChoiceBoxAnnee2();
		obsChoiceBoxStation();
		obsChoiceBoxOperation();
		obsChoiceBoxParametre();
		obsChoiceBoxComparer();
	}

}
