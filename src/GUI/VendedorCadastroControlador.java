package GUI;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import BD.DBExcecao;
import GUI.Listeners.ObservadorEventos;
import GUI.util.Alertas;
import GUI.util.Restricoes;
import GUI.util.Utils;
import Modelo.Entidades.Departamento;
import Modelo.Entidades.Vendedor;
import Modelo.Excecoes.ValidacaoExcecao;
import Modelo.Servicos.ServicoDepartamento;
import Modelo.Servicos.ServicoVendedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class VendedorCadastroControlador implements Initializable {

	private Vendedor entidade;

	private ServicoVendedor servico;

	private List<ObservadorEventos> observadorEventos = new ArrayList<>();

	private ServicoDepartamento servicoDepartamento;

	@FXML
	private TextField campoId;

	@FXML
	private TextField campoNome;

	@FXML
	private TextField campoEmail;

	@FXML
	private DatePicker dpNascimento;

	@FXML
	private TextField campoBaseSalarial;

	@FXML
	private ComboBox<Departamento> boxDepartamento;

	@FXML
	private Label lblErroId;

	@FXML
	private Label lblErroNome;

	@FXML
	private Label lblErroEmail;

	@FXML
	private Label lblErroNascimento;

	@FXML
	private Label lblErroBaseSalarial;

	@FXML
	private Button botaoCadastrar;

	@FXML
	private Button botaoCancelar;

	private ObservableList<Departamento> obsLista;

	public void setVendedor(Vendedor entidade) {
		this.entidade = entidade;
	}

	public void setServicos(ServicoVendedor servico, ServicoDepartamento servicoDepartamento) {
		this.servico = servico;
		this.servicoDepartamento = servicoDepartamento;
	}

	public void inscricaoListener(ObservadorEventos listener) {
		observadorEventos.add(listener);
	}

	@FXML
	public void onBotaoCadastrarAction(ActionEvent evento) {
		if (entidade == null) {
			throw new IllegalStateException("Entidade está nula!");
		}
		if (servico == null) {
			throw new IllegalStateException("Serviço está valendo nulo!");
		}
		try {
			entidade = getDadosFormulario();
			servico.salvarOuAtualizar(entidade);
			notificarListeners();
			Utils.stageAtual(evento).close();

		} catch (ValidacaoExcecao e) {
			setErroMensagemLabel(e.getError());
		} catch (DBExcecao e) {
			Alertas.mostrarAlerta("Erro ao Salvar Objeto!", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notificarListeners() {
		for (ObservadorEventos listener : observadorEventos) {
			listener.onDadosAlterados();
		}
	}

	private Vendedor getDadosFormulario() {
		Vendedor obj = new Vendedor();

		ValidacaoExcecao excecao = new ValidacaoExcecao("Erro de validação de Campos.");

		obj.setId(Utils.converToInt(campoId.getText()));

		if (campoNome.getText() == null || campoNome.getText().trim().equals("")) {
			excecao.addErro("Nome", "Campo não pode estar vazio");
		}

		obj.setNome(campoNome.getText());
		
		if (campoEmail.getText() == null || campoEmail.getText().trim().equals("")) {
			excecao.addErro("Email", "Campo não pode estar vazio");
		}

		obj.setEmail(campoEmail.getText());
		
		if (dpNascimento.getValue() == null) {
			excecao.addErro("DataNascimento", "Campo não pode estar vazio");
		} else {
			Instant instante = Instant.from(dpNascimento.getValue().atStartOfDay(ZoneId.systemDefault()));	
			obj.setDataNascimento(Date.from(instante));
		}

		if (campoBaseSalarial.getText() == null || campoBaseSalarial.getText().trim().equals("")) {
			excecao.addErro("BaseSalarial", "Campo não pode estar vazio");
		}

		obj.setSalarioBase(Utils.converToDouble(campoBaseSalarial.getText()));
		
		obj.setDepartamento(boxDepartamento.getValue());
		
		if (excecao.getError().size() > 0) {
			throw excecao;
		}
		
		return obj;
	}

	@FXML
	public void onBotaoCancelarAction(ActionEvent evento) {
		Utils.stageAtual(evento).close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		iniciarNodes();
	}

	private void iniciarNodes() {
		Restricoes.setCampoInteger(campoId);
		Restricoes.setCampoValorMax(60, campoNome);
		Restricoes.setCampoDouble(campoBaseSalarial);
		Restricoes.setCampoValorMax(60, campoEmail);
		Utils.formatarDatePicker(dpNascimento, "dd/MM/yyyy");
		iniciarComboBoxDepartmento();
	}

	public void updateDadosFormulario() {
		if (entidade == null) {
			throw new IllegalStateException("Dependência de Vendedor deve ser Instanciado!");
		}
		campoId.setText(String.valueOf(entidade.getId()));
		campoNome.setText(entidade.getNome());
		campoEmail.setText(entidade.getEmail());
		Locale.setDefault(Locale.US);
		campoBaseSalarial.setText(String.format("%.2f", entidade.getSalarioBase()));
		if (entidade.getDataNascimento() != null) {
			dpNascimento.setValue((LocalDate) (LocalDateTime.ofInstant(entidade.getDataNascimento().toInstant(),
					ZoneId.systemDefault())).toLocalDate());
		}
		if (entidade.getDepartamento() == null) {
			boxDepartamento.getSelectionModel().selectFirst();
		}else {
			boxDepartamento.setValue(entidade.getDepartamento());
		}

	}

	private void setErroMensagemLabel(Map<String, String> erro) {
		Set<String> campos = erro.keySet();
		
		lblErroNome.setText(campos.contains("Nome") ? erro.get("Nome") : "");
		
		lblErroEmail.setText(campos.contains("Email") ? erro.get("Email") : "");
		
		lblErroBaseSalarial.setText(campos.contains("BaseSalarial") ? erro.get("BaseSalarial") : "");
		
		lblErroNascimento.setText(campos.contains("DataNascimento") ? erro.get("DataNascimento") : "");
		
	}

	public void carregarObjetosAssociados() {
		if (servicoDepartamento == null) {
			throw new IllegalStateException("Serviço de Departamento não foi Instanciado.");
		}
		List<Departamento> lista = servicoDepartamento.pesquisarTodos();
		obsLista = FXCollections.observableArrayList(lista);
		boxDepartamento.setItems(obsLista);
	}

	private void iniciarComboBoxDepartmento() {
		
		Callback<ListView<Departamento>, ListCell<Departamento>> injetor = lv -> new ListCell<Departamento>() {
			@Override
			protected void updateItem(Departamento item, boolean Vazio) {
				super.updateItem(item, Vazio);
				setText(Vazio ? "" : item.getNome());
			}
		};
		boxDepartamento.setCellFactory(injetor);
		boxDepartamento.setButtonCell(injetor.call(null));
	}
}
