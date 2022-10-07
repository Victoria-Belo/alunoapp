package com.alunoapp.alunoapp.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alunoapp.alunoapp.repository.AlunoPaginação;
import com.alunoapp.alunoapp.repository.AlunoRepository;
import com.alunoapp.alunoapp.repository.CursoRepository;
import com.alunoapp.alunoapp.models.Aluno;
import com.alunoapp.alunoapp.models.Curso;

@Controller
public class AlunoController {
	
	@Autowired
	private AlunoRepository ar; //instanciando classe aluno
	
	@Autowired
	private CursoRepository cr;
	
	@Autowired
	private AlunoPaginação ap;
	
	@RequestMapping(value= "/aluno/cadastrar", method = RequestMethod.GET)
	public String cadastrarAluno(Model model) {
		
		try {
			Iterable <Curso> curso = cr.findAll();		
			if (curso != null) {
				//System.out.println("-> " + curso);
				model.addAttribute("curso", curso);
			}
			
		} catch (NullPointerException e ) {
			Iterable <Curso> curso = null;		
			
		}
		
		return "aluno/cadastrarAluno";
	}		
	
	@RequestMapping(value = "/aluno/cadastrar", method=RequestMethod.POST)
	public String cadastrarAluno(@Valid Aluno aluno, @RequestParam (value="id_curso", name = "id_curso")  long  id_curso, 
			BindingResult result, RedirectAttributes attributes){
		
	    if(result.hasErrors()) 	{
			
			attributes.addFlashAttribute("mensagem", "Dados inválidos, por favor preencha corretamente!");
		}
		else {	
			
			//pegar o id do curso selecionado para settar no aluno			
			Curso selecionado = cr.findByCodigo(id_curso);
			aluno.setCurso(selecionado);
			
			System.out.println(" CODIGO DO CURSO SELECIONADO -> " + id_curso);					
			
			ar.save(aluno); 
			attributes.addFlashAttribute("mensagem", "Dados cadastros com êxito");		
		}
		
		return "redirect:/cadastrar/aluno";
	}	
	
	@RequestMapping(value="/aluno/lista", method = RequestMethod.GET)
	public String listaAluno(HttpServletRequest request, Model model) {		
		
		//Iterable <Aluno> alunos = ar.findAll();		
		
		int page = 0; //default page number is 0 (yes it is weird)
        int size = 3; //default page size is 10
        
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }        
        model.addAttribute("aluno", ap.findAll(PageRequest.of(page, size)));      
        //model.addAttribute("alunos", alunos );		
		
		return "aluno/listaAluno";		
	}
	
	@RequestMapping("/excluirAluno")
	public String excluirAluno(long codigo) {
		Aluno aluno = ar.findByCodigo(codigo);
		ar.delete(aluno);		
		return "redirect:/listaAluno";
	} 
	
	@RequestMapping(value="/editarAluno/{codigo}", method=RequestMethod.GET)	
	public ModelAndView updateAluno(@PathVariable("codigo") long codigo, Model model) {
		
		Iterable <Curso> curso = cr.findAll(); //para que apareça a lista do select, preciso pegar a lista de tds os cursos e acrescentar no MODEL		
		model.addAttribute("curso", curso);
		
		ModelAndView mv = new ModelAndView("aluno/editarAluno");
		Aluno aluno = ar.findByCodigo(codigo);
		mv.addObject("aluno", aluno );
		return mv;		
	}	
	
	@RequestMapping(value="/editarAluno/{codigo}", method = RequestMethod.POST)
	public String editarAluno(@PathVariable("codigo") long codigo, Aluno aluno, @RequestParam(name="id_curso", value="id_curso") long id_curso) {			
		//CONSEGUI!!!
		Curso curso = cr.findByCodigo(id_curso);
		aluno.setCurso(curso);
		ar.save(aluno);			
		return "redirect:/listaAluno";
	}
	
	/*
	 * @RequestMapping(value="/aluno/busca", method = RequestMethod.GET)
	public String buscaAluno(Model model,@RequestParam(name="nome_aluno", value="nome_aluno", required = false) String nome_aluno, HttpServletRequest request){
		//tive problemas em fazer ese código com @RequestParam) 
		//O request não indentificava nome_aluno como uma string assim que carregava a página
		//Primeiro, testei a entrada por Default Value e o código funcionava, a query acontecia e a busca ocorria
		//Depois de checar as documentações, fiz tratamento de erro porque 
		//quando a pagina carrega, o input sempre está com valor null
		//Percebi que meu botão de submit era um problema. Ele alterava a URL e dava 404 
		//Para resolver, encontrei a solução colocando  form th:action no lugar de form action
		//Conclusão: Meu código back estava certo, o problema esta a na URL do HTML, que direcionava a uma página inexistente
		
		int page=0;
		int size=3;
		
		
		try {	
			
			if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
	            page = Integer.parseInt(request.getParameter("page")) - 1;
	        }

			if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
				size = Integer.parseInt(request.getParameter("size"));
			}    	
			
			int totalPages = ap.findAll(PageRequest.of(page, size)).getTotalPages();
			System.out.println("=====TOTAL PAGES:===== " + totalPages);
			model.addAttribute("totalPages", totalPages);
			
			if ( nome_aluno == null) {
				
				Iterable<Aluno> alunos = ar.findAll();
				model.addAttribute("aluno", alunos);	
				model.addAttribute("aluno", ap.findAll(PageRequest.of(page, size)));      

			}
			else {			
				
				List<Aluno> alunos = ap.findByAluno(nome_aluno);
				//List<? extends Aluno> encontrado = new ArrayList<>();
				//encontrado= alunos.stream().collect(Collectors.toList());
				
				
				System.out.println("ALUNOS DO AP -> " + alunos );
				model.addAttribute("aluno", alunos);	
				
				size = alunos.size();
				System.out.println(size);			
				
				model.addAttribute("aluno", ap.findAll(PageRequest.of(page, size)));     

			}				
			
			
		} catch (Exception e) {
			

			System.out.println(e);
			
		}						
		return "aluno/busca";
	}	*/
	
	
	
	@RequestMapping(value="/aluno/busca", method = RequestMethod.GET)
	public String buscaAluno(Model model,@RequestParam(name="nome_aluno", value="nome_aluno", required = false) String nome_aluno, HttpServletRequest request){
		//tive problemas em fazer ese código com @RequestParam) 
		//O request não indentificava nome_aluno como uma string assim que carregava a página
		//Primeiro, testei a entrada por Default Value e o código funcionava, a query acontecia e a busca ocorria
		//Depois de checar as documentações, fiz tratamento de erro porque 
		//quando a pagina carrega, o input sempre está com valor null
		//Percebi que meu botão de submit era um problema. Ele alterava a URL e dava 404 
		//Para resolver, encontrei a solução colocando  form th:action no lugar de form action
		//Conclusão: Meu código back estava certo, o problema esta a na URL do HTML, que direcionava a uma página inexistente
		
		try {	
			
			
			if ( nome_aluno == null) {
				
				Iterable<Aluno> alunos = ar.findAll();
				model.addAttribute("aluno", alunos);	

			}
			else {			
				
				List<Aluno> alunos = ap.findByAluno(nome_aluno);
				//List<? extends Aluno> encontrado = new ArrayList<>();
				//encontrado= alunos.stream().collect(Collectors.toList());
				
				
				System.out.println("ALUNOS DO AP -> " + alunos );
				model.addAttribute("aluno", alunos);					
				
				
			}				
			
			
		} catch (Exception e) {
			

			System.out.println(e);
			
		}						
		return "aluno/busca";
	}	
	
	
	
		
	@RequestMapping("/teste")
	public String testePagina() {
		return "aluno/teste";
	}
	
	
}
