package com.proyecto.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.servidor.model.Empresa;
import com.proyecto.servidor.model.Contrato;
import com.proyecto.servidor.service.EmpresaService;
import com.proyecto.servidor.service.ContratoService;

@Controller
@RequestMapping("/model")
public class ContratoController {

    @Autowired
    private EmpresaService makeService;

    @Autowired
    private ContratoService modelService;


    private String add_edit_template="/model/add-edit-model";
    private String list_template="/model/list-model";
    private String list_redirect="redirect:/model/list";

    @GetMapping("/add")
    public String addModel(Contrato model2, org.springframework.ui.Model model){
        model.addAttribute("model", model2);

        List<Empresa> listMake = makeService.findAll();
        model.addAttribute("listMake", listMake);

        return add_edit_template;
    }

    @GetMapping("/edit/{id}")
    public String editModel(@PathVariable("id") int id, org.springframework.ui.Model model){
        Contrato model2 = modelService.get(id);
        model.addAttribute("model", model2);

        List<Empresa> listMake = makeService.findAll();
        model.addAttribute("listMake", listMake);

        return add_edit_template;
    }

    @PostMapping("/save")
    public String saveModel(@Valid @ModelAttribute("model") Contrato model2, BindingResult result, org.springframework.ui.Model model){
        model.addAttribute("model", model2);

        List<Empresa> listMake = makeService.findAll();
        model.addAttribute("listMake", listMake);

        if(result.hasErrors()){
            return add_edit_template;
        }
        modelService.save(model2);

        return list_redirect+"?success";
    }

   
    @GetMapping("/list")
    public String listModel(org.springframework.ui.Model model) {
        List<Contrato> listModel = modelService.findAll();
        model.addAttribute("listModel", listModel);

        List<Empresa> listMake = makeService.findAll();
        model.addAttribute("listMake", listMake);

        return list_template;
    }
}