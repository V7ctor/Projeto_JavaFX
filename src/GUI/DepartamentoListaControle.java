package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import Aplicacao.Main;
import BD.DBExcecaoIntegracao;
import GUI.Listeners.ObservadorEventos;
import GUI.util.Alertas;
import GUI.util.Utils;
import Modelo.Entidades.Departamento;
import Modelo.Servicos.ServicoDepartamento;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DepartamentoListaControle implements Initializable, ObservadorEventos {

	private ServicoDepartamento servico;

	@FXML
	private TableView<Departamento> tabelaDepartamento;

	@FXML
	private TableColumn<Departamento, Integer> colunaID;

	@FXML
	private TableColumn<Departamento, String> colunaNome;

	@FXML
	private TableColumn<Departamento, Departamento> colunaEditar;

	@FXML
	private TableColumn<Departamento, Departamento> colunaRemover;

	@FXML
	private Button btNovo;

	private ObservableList<Departamento> obsLista;

	@FXML
	public void onBtNovoAction(ActionEvent evento) {
		Stage stagePai = Utils.stageAtual(evento);
		Departamento obj = new Departamento();
		criarTelaFormulario(obj, "/GUI/DepartamentoCadastro.fxml", stagePai);
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
		if (servico == null) {
			throw new IllegalStateException("Servico deve ser instanciado!");
		}
		List<Departamento> lista = servico.pesquisarTodos();
		obsLista = FXCollections.observableArrayList(lista);
		tabelaDepartamento.setItems(obsLista);
		iniciarBotoesEditar();
		inicarBotoesRemocao();
	}

	private void criarTelaFormulario(Departamento obj, String caminho, Stage stageOriginario) {
		try {
			FXMLLoader carregar = new FXMLLoader(getClass().getResource(caminho));
			Pane painel = carregar.load();

			DepartamentoCadastroControlador controlador = carregar.getController();
			controlador.setDepartamento(obj);
			controlador.setServico(new ServicoDepartamento());
			controlador.inscricaoListener(this);
			controlador.updateDadosFormulario();

			Stage novaTela = new Stage();
			novaTela.setTitle("Entre com os Dados do Departamento");
			novaTela.setScene(new Scene(painel));
			novaTela.setResizable(false);
			novaTela.initOwner(stageOriginario);
			novaTela.initModality(Modality.WINDOW_MODAL);
			novaTela.showAndWait();

		} catch (IOException e) {
			Alertas.mostrarAlerta("IOException", "Erro ao Abrir Tela: ", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDadosAlterados() {
		updateTabela();
	}

	private void iniciarBotoesEditar() {
		colunaEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		colunaEditar.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
			private final Button botao = new Button("Editar");

			@Override
			protected void updateItem(Departamento obj, boolean vazio) {
				super.updateItem(obj, vazio);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(botao);
				botao.setOnAction(
						evento -> criarTelaFormulario(obj, "/GUI/DepartamentoCadastro.fxml", Utils.stageAtual(evento)));
			}
		});
	}

	private void inicarBotoesRemocao() {
		colunaRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		colunaRemover.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
			private final Button botao = new Button("Remover");
			
			@Override
				protected void updateItem(Departamento obj, boolean vazio) {
				super.updateItem(obj, vazio);
				if (obj == null) {
					setGraphic(null);
				return;
			}
			setGraphic(botao);
			botao.setOnAction(evento -> removerEntidade(obj));
		}
	 });
   }
	
	private void removerEntidade(Departamento obj) {
		Optional<ButtonType> resultado = Alertas.mostrarConfirmacao("Confirmação!!", 
				"Tem certeza que deseja Deletar o Departamento de "+obj.getNome()+" ?");
		
		if (resultado.get() == ButtonType.OK) {
			if (servico == null) {
				throw new IllegalStateException("servico deve ser instanciado.");
			}
			try {
				servico.excluirDepartamento(obj);
				updateTabela();
			} catch (DBExcecaoIntegracao e) {
				Alertas.mostrarAlerta("Erro ao Remover Objeto!", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
}