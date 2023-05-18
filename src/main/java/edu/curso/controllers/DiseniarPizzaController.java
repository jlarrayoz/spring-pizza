package edu.curso.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.curso.domain.Ingrediente;
import edu.curso.domain.OrdenPizza;
import edu.curso.domain.Pizza;
import edu.curso.domain.TipoIngrediente;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("ordenPizza") //Pone el atributo ordenPizza que se define en el modelo más abajo en la session
public class DiseniarPizzaController {
	
	@ModelAttribute	
	public void agregarIngredientesAlModelo(Model model) {
		
		List<Ingrediente>  ingredientes = Arrays.asList(
				new Ingrediente("MC", "Masa Común" , TipoIngrediente.MASA),
				new Ingrediente("MM", "Masa Madre" , TipoIngrediente.MASA),
				new Ingrediente("MIT", "Masa italiana" , TipoIngrediente.MASA),
				new Ingrediente("QM", "Queso muzzarella" , TipoIngrediente.QUESO),
				new Ingrediente("QD", "Queso dambo" , TipoIngrediente.QUESO)
				);
		
		TipoIngrediente[] tipos = TipoIngrediente.values();
		for (TipoIngrediente tipoIngrediente : tipos) {
			model.addAttribute(tipoIngrediente.toString().toLowerCase(), filterByType(ingredientes, tipoIngrediente));
		}
	}
	
	@ModelAttribute(name = "ordenPizza")
	public OrdenPizza orden() {
		return new OrdenPizza();
	}
	
	@ModelAttribute(name = "pizza")
	public Pizza pizza() {
		return new Pizza();
	}
	
	//Este método devuelve el nombre de la vista que antenderá el request en /design
	@GetMapping
	public String mostrarFormulario() {
		return "design";
	}
	
	private Iterable<Ingrediente> filterByType(List<Ingrediente> ingredientes, TipoIngrediente tipo) {
		return ingredientes.stream().filter(x -> x.getTipo().equals(tipo)).collect(Collectors.toList());
		
	}

}
