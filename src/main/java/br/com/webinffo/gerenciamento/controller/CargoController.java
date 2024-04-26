package br.com.webinffo.gerenciamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.webinffo.gerenciamento.entity.Cargo;
import br.com.webinffo.gerenciamento.entity.Departamento;
import br.com.webinffo.gerenciamento.projections.CargoProjection;
import br.com.webinffo.gerenciamento.service.CargoService;
import br.com.webinffo.gerenciamento.service.DepartamentoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cargos")
public class CargoController {
	
	@Autowired
	private CargoService cargoService;
	@Autowired
	private DepartamentoService departamentoService;

	@GetMapping("/cadastrar")
	public String cadastrar(Cargo cargo) {
		return "cargo/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model,
						 @RequestParam(value = "nome", defaultValue = "") String nome, 
						 @RequestParam(value = "departamentoId", defaultValue = "") Long departamentoId,
						 @RequestParam(value = "page", defaultValue = "0") Integer page,
						 @RequestParam(value = "size", defaultValue = "5") Integer size,
						 @RequestParam(value = "direction", defaultValue = "ASC") String direction,
						 @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		
		
		if(!nome.equals("") || departamentoId != null) {
			size = 100; 
		}
		
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);

		Page<CargoProjection> pageCargo = cargoService.findAllPaged(pageRequest, nome, departamentoId);
		
		model.addAttribute("pageCargo", pageCargo);
		return "cargo/lista"; 
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "cargo/cadastro";
		}
		
		try {
			cargoService.salvar(cargo);
			attr.addFlashAttribute("success", "Cargo inserido com sucesso.");
		}catch (Exception e) {
			attr.addFlashAttribute("fail", e.getMessage());
		}
		return "redirect:/cargos/cadastrar";

	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cargo", cargoService.findById(id));
		return "cargo/cadastro";
	}

	@PostMapping("/editar/{id}")
	public String editar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr, @PathVariable Long id) {
		
		if (result.hasErrors()) {
			return "cargo/cadastro";
		}	
		
		try {
			cargoService.edit(cargo, id);
			attr.addFlashAttribute("success", "Registro atualizado com sucesso.");
		}catch (Exception e) {
			attr.addFlashAttribute("fail", e.getMessage());
		}
		return "redirect:/cargos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		try {
			cargoService.delete(id);
			attr.addFlashAttribute("success", "Cargo excluido com sucesso.");
		}catch (Exception e) {
			attr.addFlashAttribute("fail", e.getMessage());
		}
		return "redirect:/cargos/listar";
	}
	
	@ModelAttribute("departamentos")
	public List<Departamento> listaDeDepartamentos() {
		return departamentoService.findAll();
	}
	
	@GetMapping("departamento/{id}")
	public ResponseEntity<List<CargoProjection>> listaCargoPorDepartamento(@PathVariable Long id){
			List<CargoProjection> proj = cargoService.findAllByDepartamentoId(id);
		return ResponseEntity.ok().body(proj);
	}
}
