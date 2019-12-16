package Modelo.Excecoes;

import java.util.HashMap;
import java.util.Map;

public class ValidacaoExcecao extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> error = new HashMap<String, String>();
	
	public ValidacaoExcecao(String mensagem) {
		super(mensagem);
	}

	public Map<String, String> getError() {
		return error;
	}
	
	public void addErro(String nomeCampo, String mensagemErro) {
		error.put(nomeCampo, mensagemErro);
	}

}