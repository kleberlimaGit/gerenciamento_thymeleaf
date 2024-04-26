package br.com.webinffo.gerenciamento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.webinffo.gerenciamento.entity.Departamento;
import br.com.webinffo.gerenciamento.service.DepartamentoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {
	
	@Autowired
	private DepartamentoService service;

	@GetMapping("/cadastrar")
	public String cadastrar(Departamento departamento) {
		return "departamento/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("departamentos", service.findAll());
		return "departamento/lista"; 
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "departamento/cadastro";
		}
		
		try {
			service.save(departamento);
			attr.addFlashAttribute("success", "Departamento inserido com sucesso.");
			return "redirect:/departamentos/listar";
		}catch (Exception e) {
			attr.addFlashAttribute("fail", e.getMessage());
			return "redirect:/departamentos/cadastrar";
		}
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("departamento", service.findById(id));
		return "departamento/cadastro";
	}
	
	@PostMapping("/editar/{id}")
	public String editar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr, @PathVariable("id") Long id) {
		
		if (result.hasErrors()) {
			return "departamento/cadastro";
		}
		
		try {
			service.edit(departamento, id);
			attr.addFlashAttribute("success", "Departamento editado com sucesso.");
			return "redirect:/departamentos/listar";
		}catch (Exception e) {
			attr.addFlashAttribute("fail", e.getMessage());
			return "redirect:/departamentos/editar"+id;
		}
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model, RedirectAttributes attr) {
		
		try {
			service.delete(id);
			attr.addFlashAttribute("success", "Departamento excluído com sucesso.");
		}catch (Exception e) {
			attr.addFlashAttribute("fail", e.getMessage());
			return "redirect:/departamentos/listar";
		}
		
		return listar(model);
	}
	
	
}
