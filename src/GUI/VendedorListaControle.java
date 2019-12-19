package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import Aplicacao.Main;
import BD.DBExcecaoIntegracao;
import GUI.Listeners.ObservadorEventos;
import GUI.util.Alertas;
import GUI.util.Utils;
import Modelo.Entidades.Vendedor;
import Modelo.Servicos.ServicoVendedor;
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

public class VendedorListaControle implements Initializable, ObservadorEventos {

	private ServicoVendedor servico;

	@FXML
	private TableView<Vendedor> tabelaVendedor;

	@FXML
	private TableColumn<Vendedor, Integer> colunaID;

	@FXML
	private TableColumn<Vendedor, String> colunaNome;
	
	@FXML
	private TableColumn<Vendedor, String> colunaEmail;
	
	@FXML
	private TableColumn<Vendedor, Date> colunaDataNascimento;
	
	@FXML
	private TableColumn<Vendedor, Double> colunaSalarioBase;

	@FXML
	private TableColumn<Vendedor, Vendedor> colunaEditar;

	@FXML
	private TableColumn<Vendedor, Vendedor> colunaRemover;

	@FXML
	private Button btNovo;

	private ObservableList<Vendedor> obsLista;

	@FXML
	public void onBtNovoAction(ActionEvent evento) {
		Stage stagePai = Utils.stageAtual(evento);
		Vendedor obj = new Vendedor();
		criarTelaFormulario(obj, "/GUI/VendedorCadastro.fxml", stagePai);
	}

	public void setServico(ServicoVendedor servico) {
		this.servico = servico;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		iniciarNodes();

	}

	private void iniciarNodes() {
		colunaID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colunaDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
		Utils.formatarTabelaData(colunaDataNascimento, "dd/MM/yyyy");
		colunaSalarioBase.setCellValueFactory(new PropertyValueFactory<>("salarioBase"));
		Utils.formatarTabelaDouble(colunaSalarioBase, 2);

		Stage estagioAtual = (Stage) Main.getTelaPrincipal().getWindow();
		tabelaVendedor.prefHeightProperty().bind(estagioAtual.heightProperty());
	}

	public void updateTabela() {
		if (servico == null) {
			throw new IllegalStateException("Servico deve ser instanciado!");
		}
		List<Vendedor> lista = servico.pesquisarTodos();
		obsLista = FXCollections.observableArrayList(lista);
		tabelaVendedor.setItems(obsLista);
		iniciarBotoesEditar();
		inicarBotoesRemocao();
	}

	private void criarTelaFormulario(Vendedor obj, String caminho, Stage stageOriginario) {
		
		try {
			FXMLLoader carregar = new FXMLLoader(getClass().getResource(caminho));
			Pane painel = carregar.load();

			VendedorCadastroControlador controlador = carregar.getController();
			controlador.setVendedor(obj);
			controlador.setServico(new ServicoVendedor());
			controlador.inscricaoListener(this);
			controlador.updateDadosFormulario();

			Stage novaTela = new Stage();
			novaTela.setTitle("Entre com os Dados do Vendedor");
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
		colunaEditar.setCellFactory(param -> new TableCell<Vendedor, Vendedor>() {
			private final Button botao = new Button("Editar");

			@Override
			protected void updateItem(Vendedor obj, boolean vazio) {
				super.updateItem(obj, vazio);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(botao);
				botao.setOnAction(
						evento -> criarTelaFormulario(obj, "/GUI/VendedorCadastro.fxml", Utils.stageAtual(evento)));
			}
		});
	}

	private void inicarBotoesRemocao() {
		colunaRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		colunaRemover.setCellFactory(param -> new TableCell<Vendedor, Vendedor>() {
			private final Button botao = new Button("Remover");
			
			@Override
				protected void updateItem(Vendedor obj, boolean vazio) {
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
	
	private void removerEntidade(Vendedor obj) {
		Optional<ButtonType> resultado = Alertas.mostrarConfirmacao("Confirmação!!", 
				"Tem certeza que deseja Deletar o Vendedor de "+obj.getNome()+" ?");
		
		if (resultado.get() == ButtonType.OK) {
			if (servico == null) {
				throw new IllegalStateException("servico deve ser instanciado.");
			}
			try {
				servico.excluirVendedor(obj);
				updateTabela();
			} catch (DBExcecaoIntegracao e) {
				Alertas.mostrarAlerta("Erro ao Remover Objeto!", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
}