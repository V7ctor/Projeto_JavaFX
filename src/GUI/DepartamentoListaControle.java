package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Aplicacao.Main;
import GUI.util.Alertas;
import GUI.util.Utils;
import Modelo.Entidades.Departamento;
import Modelo.Servicos.ServicoDepartamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DepartamentoListaControle implements Initializable {

	private ServicoDepartamento servico;
	
	@FXML
	private TableView<Departamento> tabelaDepartamento;

	@FXML
	private TableColumn<Departamento, Integer> colunaID;

	@FXML
	private TableColumn<Departamento, String> colunaNome;

	@FXML
	private Button btNovo;
	
	private ObservableList<Departamento> obsLista;

	@FXML
	public void onBtNovoAction(ActionEvent evento) {
		Stage stagePai = Utils.stageAtual(evento);
		criarTelaFormulario("/GUI/DepartamentoCadastro.fxml", stagePai);
	}
	
	public void setServico(ServicoDepartamento servico) {
		this.servico = servico;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		iniciarNodes();
		
	}

	private void iniciarNodes() {
		colunaID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

		Stage estagioAtual = (Stage) Main.getTelaPrincipal().getWindow();
		tabelaDepartamento.prefHeightProperty().bind(estagioAtual.heightProperty());
	}

	public void updateTabela() {
		if (servico == null){
			throw new IllegalStateException("Servico deve ser instanciado!");
		}
		List<Departamento> lista = servico.pesquisarTodos();
		obsLista = FXCollections.observableArrayList(lista);
		tabelaDepartamento.setItems(obsLista);
	}
	
	private void criarTelaFormulario(String caminho, Stage stageOriginario) {
		try {
			FXMLLoader carregar = new FXMLLoader(getClass().getResource(caminho));
			Pane painel = carregar.load();
			
			Stage novaTela = new Stage();
			novaTela.setTitle("Entre com os Dados do Departamento");
			novaTela.setScene(new Scene(painel));
			novaTela.setResizable(false);
			novaTela.initOwner(stageOriginario);
			novaTela.initModality(Modality.WINDOW_MODAL);
			novaTela.showAndWait();

		}catch (IOException e) {
			Alertas.mostrarAlerta("IOException", "Erro ao Abrir Tela: ", e.getMessage(), AlertType.ERROR);
		}
	}
}
