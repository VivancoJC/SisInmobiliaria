package com.proyecto.cliente.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.servidor.model.Dropdown;
import com.proyecto.servidor.model.Empresa;
import com.proyecto.servidor.model.Product;
import com.proyecto.servidor.service.MakeService;
import com.proyecto.servidor.service.ModelService;
import com.proyecto.servidor.service.ProductService;
import com.proyecto.servidor.util.Utility;

@Controller
public class HomeController {

    @Autowired
    private MakeService makeService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private Utility utility;


    @Autowired
    ProductService productService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("classActiveHome", "home active ");

        List<Empresa> listMake = makeService.findAll();
        model.addAttribute("listMake", listMake);

        List<com.proyecto.servidor.model.Contrato> listModel = modelService.getModels(listMake.get(0).getId());
        model.addAttribute("listModel", listModel);

        List<Integer> listYear = utility.getYears();
        model.addAttribute("listYear", listYear);

        List<Product> productList = productService.findAllByActive();
        model.addAttribute("productList", productList);

        //Getting top 4
        List<Product> productList2 = productService.topMostOrderedProducts(8);
        model.addAttribute("productList2", productList2);

        return "/client/home";
    }



   
  
    
   
   
  

    @RequestMapping(value = "/models")
    
    @ResponseBody
    public List<Dropdown> getModels(@RequestParam Long make) {
        List<com.proyecto.servidor.model.Contrato> modelList = modelService.getModels(make);
        List<Dropdown> dropdownList=new ArrayList<>();
        for (com.proyecto.servidor.model.Contrato model: modelList) {
            dropdownList.add(new Dropdown(model.getId(), model.getName()));
        }
        return dropdownList;
    }

    //  500 pag
    @RequestMapping(path = "/tigger-error", produces = MediaType.APPLICATION_JSON_VALUE)
    public void error500() throws Exception {
        throw new Exception();
    }

}
