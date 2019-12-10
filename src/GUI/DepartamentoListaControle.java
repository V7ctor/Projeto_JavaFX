package GUI;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Aplicacao.Main;
import Modelo.Entidades.Departamento;
import Modelo.Servicos.ServicoDepartamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	public void onBtNovoAction() {
		System.out.println("Botão Novo Clickado!");
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
		List<Departamento> lista = ServicoDepartamento.pesquisarTodos();
		obsLista = FXCollections.observableArrayList(lista);
		tabelaDepartamento.setItems(obsLista);
		
	}
}
