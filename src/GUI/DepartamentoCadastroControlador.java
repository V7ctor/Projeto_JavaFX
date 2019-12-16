package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import BD.DBExcecao;
import GUI.Listeners.ObservadorEventos;
import GUI.util.Alertas;
import GUI.util.Restricoes;
import GUI.util.Utils;
import Modelo.Entidades.Departamento;
import Modelo.Excecoes.ValidacaoExcecao;
import Modelo.Servicos.ServicoDepartamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartamentoCadastroControlador implements Initializable{
	
	private Departamento entidade;
	
	private ServicoDepartamento servico;
	
	private List<ObservadorEventos> observadorEventos = new ArrayList<>();
	
	@FXML 
	private TextField campoId;
	
	@FXML 
	private TextField campoNome;

	@FXML
	private Label lblErroId;
	
	@FXML
	private Label lblErroNome;
	
	@FXML
	private Button botaoCadastrar;
	
	@FXML
	private Button botaoCancelar;
	
	public void setDepartamento(Departamento entidade) {
		this.entidade = entidade;
	}
	
	public void setServico(ServicoDepartamento servico) {
		this.servico = servico;
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
			
		}catch (ValidacaoExcecao e) {
			setErroMensagemLabel(e.getError());
		}
		catch (DBExcecao e) {
			Alertas.mostrarAlerta("Erro ao Salvar Objeto!", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notificarListeners() {
		for (ObservadorEventos listener : observadorEventos) {
			listener.onDadosAlterados();
		}
	}

	private Departamento getDadosFormulario() {
		Departamento obj = new Departamento();

		ValidacaoExcecao excecao = new ValidacaoExcecao("Erro de validação de Campos.");
		
		obj.setId(Utils.converToInt(campoId.getText()));
		
		if (campoNome.getText() == null || campoNome.getText().trim().equals("")) {
			excecao.addErro("Nome", "Campo não pode estar vazio");
		}
		
		obj.setNome(campoNome.getText());
		
		if (excecao.getError().size() != 0) {
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
		Restricoes.setCampoValorMax(20, campoNome);
	}
	
	public void updateDadosFormulario() {
		if (entidade == null) {
			throw new IllegalStateException("Dependência de Departamento deve ser Instanciado!");
		}
		campoId.setText(String.valueOf(entidade.getId()));
		campoNome.setText(entidade.getNome());
	}
	
	private void setErroMensagemLabel (Map<String, String> erro) {
		Set<String> campos = erro.keySet();
		
		if (campos.contains("Nome")) {
			lblErroNome.setText(erro.get("Nome"));
		}
	}

}
