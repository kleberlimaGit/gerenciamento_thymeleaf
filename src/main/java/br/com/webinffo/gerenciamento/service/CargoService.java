package br.com.webinffo.gerenciamento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.webinffo.gerenciamento.entity.Cargo;
import br.com.webinffo.gerenciamento.exceptions.DatabaseException;
import br.com.webinffo.gerenciamento.exceptions.ResourceEntityNotFoundException;
import br.com.webinffo.gerenciamento.projections.CargoProjection;
import br.com.webinffo.gerenciamento.repository.CargoRepository;

@Service
public class CargoService {

	@Autowired
	private CargoRepository repository;

	@Transactional(readOnly = true)
	public Page<CargoProjection> findAllPaged(PageRequest pageable, String nome, Long idDepartamento) {
		return repository.findAllPaged(pageable, nome, idDepartamento);
	}

	@Transactional
	public Cargo salvar(Cargo cargo) {
		try {
			return repository.save(cargo);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Esse cargo já foi cadastrado.");
		}
	}

	@Transactional
	public Cargo edit(Cargo cargo, Long id) {
		try {
			Cargo cargoBd = repository.findById(id)
					.orElseThrow(() -> new ResourceEntityNotFoundException("Cargo com id não encontrado: " + id));
			cargoBd.setDepartamento(cargo.getDepartamento());
			cargoBd.setNome(cargo.getNome());
			return repository.save(cargoBd);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Esse cargo já foi cadastrado.");
		}
	}

	@Transactional(readOnly = true)
	public Cargo findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceEntityNotFoundException("Cargo com id não encontrado: " + id));
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Cargo não pode ser excluído pois possui funcionarios vinculados.");
		}
	}
	
	@Transactional(readOnly = true)
	public List<CargoProjection> findAllByDepartamentoId(Long id) {
		return repository.findAllByDepartamentoId(id);
	}
}
