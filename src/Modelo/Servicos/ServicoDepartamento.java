package Modelo.Servicos;

import java.util.ArrayList;
import java.util.List;

import Modelo.Entidades.Departamento;

public class ServicoDepartamento {

	public static List<Departamento> pesquisarTodos(){
		List<Departamento> lista = new ArrayList<Departamento>();
		lista.add(new Departamento(1, "Livros"));
		lista.add(new Departamento(2, "Computadores"));
		lista.add(new Departamento(3, "Música"));
		lista.add(new Departamento(4, "Bibloteca"));
		lista.add(new Departamento(5, "Moda"));
		lista.add(new Departamento(6, "Software"));
		
		return lista;

	}
}

