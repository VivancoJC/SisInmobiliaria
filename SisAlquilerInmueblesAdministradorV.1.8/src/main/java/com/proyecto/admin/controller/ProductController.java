package com.proyecto.admin.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.servidor.model.Category;
import com.proyecto.servidor.model.Dropdown;
import com.proyecto.servidor.model.Empresa;
import com.proyecto.servidor.model.Contrato;
import com.proyecto.servidor.model.Product;
import com.proyecto.servidor.service.CategoryService;
import com.proyecto.servidor.service.EmpresaService;
import com.proyecto.servidor.service.ContratoService;
import com.proyecto.servidor.service.ProductService;
import com.proyecto.servidor.util.Utility;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EmpresaService makeService;

    @Autowired
    private ContratoService modelService;

    @Autowired
    private Utility utility;


    private String add_edit_template="/product/add-edit-product";
    private String list_template="/product/list-product";
    private String list_redirect="redirect:/product/list";


    @GetMapping("/add")
    public String addProduct(Product product, org.springframework.ui.Model model){
        model.addAttribute("product", product);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        List<Empresa> listMake = makeService.findAll();
        model.addAttribute("listMake", listMake);

        List<Contrato> listModel = modelService.getModels(listMake.get(0).getId());
        model.addAttribute("listModel", listModel);

        List<Integer> listYear = utility.getYears();
        model.addAttribute("listYear", listYear);

        return add_edit_template;
    }


    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") long id, org.springframework.ui.Model model){
        Product product = productService.get(id);
        model.addAttribute("product", product);

        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        List<Empresa> listMake = makeService.findAll();
        model.addAttribute("listMake", listMake);

   
        List<Contrato> listModel = modelService.getModels(listMake.get(0).getId());
        if(product.getMake() !=null) {
            listModel = modelService.getModels(product.getMake().getId());
        }
        model.addAttribute("listModel", listModel);


        List<Integer> listYear = utility.getYears();
        model.addAttribute("listYear", listYear);

        return add_edit_template;
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, org.springframework.ui.Model model){
        model.addAttribute("product", product);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        List<Empresa> listMake = makeService.findAll();
        model.addAttribute("listMake", listMake);

        List<Integer> listYear = utility.getYears();
        model.addAttribute("listYear", listYear);

        if(result.hasErrors()){
            return add_edit_template;
        }
       

        productService.save(product);
        return list_redirect+"?success";
    }




    @GetMapping("/list")
    public String listProduct(org.springframework.ui.Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        List<Product> listProducts = productService.findAll();
        model.addAttribute("listProducts", listProducts);

        return list_template;
    }

    @RequestMapping(value = "/models")
    @ResponseBody
    public List<Dropdown> getModels(@RequestParam Long make) {
        List<Contrato> modelList = modelService.getModels(make);
        List<Dropdown> dropdownList=new ArrayList<>();
        for (Contrato model: modelList) {
            dropdownList.add(new Dropdown(model.getId(), model.getName()));
        }
        return dropdownList;
    }

}