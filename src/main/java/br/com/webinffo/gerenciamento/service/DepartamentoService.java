package br.com.webinffo.gerenciamento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.webinffo.gerenciamento.entity.Departamento;
import br.com.webinffo.gerenciamento.exceptions.DatabaseException;
import br.com.webinffo.gerenciamento.exceptions.ResourceEntityNotFoundException;
import br.com.webinffo.gerenciamento.repository.DepartamentoRepository;

@Service
public class DepartamentoService {

	@Autowired
	private DepartamentoRepository repository;
	
	@Transactional(readOnly = true)
	public List<Departamento> findAll(){
		return repository.findAll(Sort.by(Direction.ASC, "nome"));
	}
	
	@Transactional(readOnly = true)
	public Departamento findById(Long id){
		Departamento departamento = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Departamento não encontrado."));
		return departamento;
	}
	
	@Transactional
	public void save(Departamento departamento) {
		if(repository.findByNome(departamento.getNome()).isPresent()) {
			throw new DatabaseException("Departamento já cadastrado."); 
		}
		
		departamento = repository.save(departamento);
	}
	
	
	@Transactional
	public Departamento edit(Departamento departamento, Long id) {
		Optional<Departamento> departamentoBD = repository.findByNome(departamento.getNome());
		if(departamentoBD.isPresent() && departamentoBD.get().getId() != id) {
			throw new DatabaseException("O nome "+departamentoBD.get().getNome()+" já está cadastrado."); 
		}
		
		departamento = repository.save(departamento);
		return departamento;
	}
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Departamento não pode ser excluído verifique se há algum cargo vinculado a ele."); 
		}
	}
}
