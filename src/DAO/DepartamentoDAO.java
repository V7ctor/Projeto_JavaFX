package DAO;

import java.util.List;

import Modelo.Entidades.Departamento;

public interface DepartamentoDAO {

	void inserirDados(Departamento departamento);
	void atualizarDados(Departamento departamento);
	void excluirPerID(Integer Id);
	Departamento pesquisarPerID(Integer Id);
	List<Departamento> encontrarTodos();
}
