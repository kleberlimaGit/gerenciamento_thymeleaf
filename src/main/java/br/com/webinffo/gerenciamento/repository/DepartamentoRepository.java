package br.com.webinffo.gerenciamento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.webinffo.gerenciamento.entity.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long>{

	Optional<Departamento> findByNome(String nome);
	
	Optional<Departamento> findByNomeAndId(String nome, Long id);
}
