package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import GUI.util.Restricoes;
import Modelo.Entidades.Departamento;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartamentoCadastroControlador implements Initializable{
	
	private Departamento entidade;
	
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
	
	@FXML
	public void onBotaoCadastrarAction() {
		
		System.out.println("Cadastrar");
	}
	
	@FXML
	public void onBotaoCancelarAction() {
		System.out.println("Cancelar");
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

}
