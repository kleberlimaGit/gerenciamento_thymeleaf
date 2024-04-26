package br.com.webinffo.gerenciamento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.webinffo.gerenciamento.entity.Funcionario;
import br.com.webinffo.gerenciamento.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
	
	@Autowired
	private FuncionarioRepository repository;
	
	@Transactional
	public Funcionario save(Funcionario funcionario) {
		return repository.save(funcionario);
	}
	
	public List<Funcionario> findAll(){
		return repository.findAll(Sort.by(Direction.ASC,"nome"));
	}
	
	
}
