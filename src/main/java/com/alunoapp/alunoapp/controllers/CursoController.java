package com.alunoapp.alunoapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alunoapp.alunoapp.models.Aluno;
import com.alunoapp.alunoapp.models.Curso;
import com.alunoapp.alunoapp.repository.CursoRepository;

@Controller
public class CursoController {
	
	@Autowired
	private CursoRepository cr;
	
	@RequestMapping(value= "/curso/cadastrar", method= RequestMethod.GET )
	public String cursoForm() {
		return "curso/cadastroCurso";
	}
	
	@RequestMapping(value="/curso/cadastrar", method = RequestMethod.POST)
	public String cursoForm(@Valid Curso curso, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) 	attributes.addFlashAttribute("mensagem", "Preencha os dados corretamente!");					
		else {
			attributes.addFlashAttribute("mensagem", "Cadastro realizado com êxito!");	
			cr.save(curso);
			}
		
		return "redirect:/curso/cadastrar";
		
	}
	
	@RequestMapping(value="/curso/lista", method = RequestMethod.GET)
	public ModelAndView listaCursos(Curso curso){
		
		Iterable<Curso> cursos = cr.findAll();		
		ModelAndView mv = new ModelAndView("curso/listaCurso");
		mv.addObject("cursos", cursos);
		return mv;		
	}
	
	@RequestMapping("/excluirCurso")
	public String excluirCurso(long codigo) {
		Curso curso = cr.findByCodigo(codigo);
		cr.delete(curso);		
		return "redirect:/curso/lista";
	} 
	
	@RequestMapping(value="/curso/editar/{codigo}", method = RequestMethod.GET)
	public ModelAndView updateCurso(@PathVariable ("codigo") long codigo) {
		
		ModelAndView mv = new ModelAndView("curso/editarCurso");	
		Curso curso = cr.findByCodigo(codigo);
		mv.addObject("curso", curso);
		return mv;
	}
	
	@RequestMapping(value = "/curso/editar/{codigo}", method=RequestMethod.POST)
	public String editarCurso(@PathVariable ("codigo") long codigo, Curso curso) {		
		
		cr.save(curso);

		return "redirect:/curso/lista";
	}
	
	
	@RequestMapping (value = "/curso/busca" , method = RequestMethod.GET )
	public String buscaCurso (@RequestParam(value = "nome_curso", name = "nome_curso", required = false ) String nome_curso, Model model) {
		//para busca algo, eu preciso de uma lista com o 
		//parametro que quero buscar, no caso quero String (nome do curso)
		
		
		//System.out.println("--->" + nome_curso);	
		if(nome_curso != null ) {
			List <Curso> curso = cr.findByNome(nome_curso);
			model.addAttribute("curso", curso);
		} else {
			List <Curso> curso = (List<Curso>) cr.findAll();
			model.addAttribute("curso", curso);			
		}
		
		return "/curso/buscaCurso";
	}
	
}
