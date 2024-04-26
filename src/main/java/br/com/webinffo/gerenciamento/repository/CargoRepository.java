package br.com.webinffo.gerenciamento.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.webinffo.gerenciamento.entity.Cargo;
import br.com.webinffo.gerenciamento.projections.CargoProjection;

public interface CargoRepository extends JpaRepository<Cargo, Long>{
	
	@Query(nativeQuery = true , value = "SELECT cargo.id AS id , cargo.nome AS nome, dp.nome AS departamento FROM tb_cargo cargo INNER JOIN tb_departamento dp on dp.id = cargo.departamento_id "
			+ "WHERE LOWER(cargo.nome) LIKE LOWER(CONCAT('%',:nomeCargo,'%')) AND :idDepartamento IS NULL OR dp.id = :idDepartamento", countQuery = """
			SELECT COUNT(*) FROM (
			SELECT cargo.id AS id , cargo.nome AS nome, dp.nome AS departamento FROM tb_cargo cargo INNER JOIN tb_departamento dp on dp.id = cargo.departamento_id
			WHERE LOWER(cargo.nome) LIKE LOWER(CONCAT('%',:nomeCargo,'%')) AND :idDepartamento IS NULL OR dp.id = :idDepartamento) AS tb_result
			""")
	Page<CargoProjection> findAllPaged(Pageable pageable, String nomeCargo, Long idDepartamento);
	
	
	@Query(nativeQuery = true, value = "SELECT cargo.id AS id , cargo.nome AS nome, dp.nome AS departamento  FROM tb_cargo cargo INNER JOIN tb_departamento dp on dp.id = cargo.departamento_id "
			+ "WHERE dp.id = :id")
	List<CargoProjection> findAllByDepartamentoId(Long id);

}
