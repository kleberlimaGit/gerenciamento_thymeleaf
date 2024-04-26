package br.com.webinffo.gerenciamento.validators;

import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.webinffo.gerenciamento.entity.Funcionario;

public class FuncionarioValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("supports: " + Funcionario.class.equals(clazz));
		return Funcionario.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		System.out.println("validate: true");
		
		Funcionario f = (Funcionario) object;
		
		LocalDate entrada = f.getDataEntrada();
		
		if (f.getDataEntrada() != null && f.getDataSaida() != null) {
			if (f.getDataSaida().isBefore(entrada)) {
				errors.rejectValue("dataSaida", "Data de saída deve ser posterior a data de entrada.");
			}
		}
	}
}