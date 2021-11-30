package com.proyecto.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.servidor.model.Category;
import com.proyecto.servidor.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private String add_edit_template="/category/add-edit-category";
    private String list_template="/category/list-category";
    private String list_redirect="redirect:/category/list";

    @GetMapping("/add")
    public String addCategory(Category category, Model model){
        model.addAttribute("category", category);
        return add_edit_template;
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") int id, Model model){
        Category category = categoryService.get(id);

        model.addAttribute("category", category);

        return add_edit_template;
    }

    @PostMapping("/save")
    public String saveCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model){
        model.addAttribute("category", category);

        if(result.hasErrors()){
            return add_edit_template;
        }
        categoryService.save(category);

        return list_redirect+"?success";
    }

   

    @GetMapping("/list")
    public String listCategory(Model model) {
        List<Category> listCategories = categoryService.findAll();
        model.addAttribute("listCategories", listCategories);

        return list_template;
    }
}