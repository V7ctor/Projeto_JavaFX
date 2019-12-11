package BD;

public class DBExcecao extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DBExcecao(String mensagem) {
		super(mensagem);
	}
}
