package com.fotopoint.fotopointapp.controllers;

import com.fotopoint.fotopointapp.models.Convidado;
import com.fotopoint.fotopointapp.models.Evento;
import com.fotopoint.fotopointapp.repository.ConvidadoRepository;
import com.fotopoint.fotopointapp.repository.EventoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository er;

    @Autowired
    private ConvidadoRepository cr;
    @RequestMapping(value="/cadastrarEvento", method = RequestMethod.GET)
    public String form(){
        return "evento/formEvento";
    }

    @RequestMapping(value="/cadastrarEvento", method = RequestMethod.POST)
    public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes){
       if(result.hasErrors()){
           attributes.addFlashAttribute("mensagem", "Verifique os campos!");
           return "redirect:/cadastrarEvento";
         }

       er.save(evento);
       attributes.addFlashAttribute("mensagem", "Evento adicionado com sucesso!");
       return "redirect:/cadastrarEvento";
    }

    @RequestMapping("/eventos")
    public ModelAndView listarEventos(){
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = er.findAll();
        mv.addObject("eventos", eventos);
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("id") long id){
        Evento evento = er.findById(id);
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento", evento);
        Iterable<Convidado> convidados = cr.findByEvento(evento);
        mv.addObject("convidados", convidados);
        return mv;
    }

    @RequestMapping(value="/deletarEvento")
    public String deletarEvento(long id){
        Evento evento = er.findById(id);
        er.delete(evento);
        return "redirect:/eventos";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String detalhesEventoConvidado(@PathVariable("id") long id, @Valid Convidado convidado,
                                          BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/{id}";
        }
        Evento evento = er.findById(id);
        convidado.setEvento(evento);
        cr.save(convidado);
        attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
        return "redirect:/{id}";
    }

    @RequestMapping(value="/deletarConvidado")
    public String deletarConvidado(String rg){
        Convidado convidado = cr.findByRg(rg);
        cr.delete(convidado);

        Evento evento = convidado.getEvento();
        long idLong = evento.getId();
        String id = "" + idLong;
        return "redirect:/"+id;
    }
}
