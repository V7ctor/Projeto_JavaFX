package BD;

public class DBExcecaoIntegracao extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DBExcecaoIntegracao (String mensagem) {
		super(mensagem);
	}

}
