package br.com.webinffo.gerenciamento.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.webinffo.gerenciamento.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	@Query(nativeQuery = true, value = """
			SELECT func.* FROM tb_funcionario func INNER JOIN tb_cargo cargo ON cargo.id = func.cargo_id
			WHERE ( :dataEntrada IS NULL OR func.data_entrada = :dataEntrada ) AND
			( :dataSaida IS NULL OR func.data_saida = :dataSaida ) AND
			LOWER(func.nome) LIKE LOWER(CONCAT('%',:nome,'%')) AND (idCargo IS NULL OR func.cargo_id = :idCargo)
			""", countQuery = """
					SELECT COUNT(*) FROM (
						SELECT * FROM tb_funcionario func INNER JOIN tb_cargo cargo ON cargo.id = func.cargo_id
							WHERE ( :dataEntrada IS NULL OR func.data_entrada = :dataEntrada ) AND
							( :dataSaida IS NULL OR func.data_saida = :dataSaida ) AND
							LOWER(func.nome) LIKE LOWER(CONCAT('%',:nome,'%')) AND (idCargo IS NULL OR func.cargo_id = :idCargo)
					) AS tb_result
					""")
	Page<Funcionario> findAllPaged(Pageable pageable, String nome, Long idCargo, LocalDate dataEntrada,
			LocalDate dataSaida);
}
