package com.proyecto.cliente.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.servidor.model.Category;
import com.proyecto.servidor.model.Empresa;
import com.proyecto.servidor.model.Product;
import com.proyecto.servidor.service.CategoryService;
import com.proyecto.servidor.service.MakeService;
import com.proyecto.servidor.service.ModelService;
import com.proyecto.servidor.service.ProductService;
import com.proyecto.servidor.util.Utility;

@Controller
public class PartController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private MakeService makeService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private Utility utility;

    private final Integer PAGE_SIZE=2;

    @RequestMapping("/category")
    public String category(@RequestParam("id") Optional<Long> id
            , Model model
            , @RequestParam("page") Optional<Integer> page
            , @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(PAGE_SIZE);

        model.addAttribute("classActiveCategory", "home active ");

        //Get all categories
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categories", categoryList);

        //Get selected product & category
        Page<Product> productList;
        Category category;
        if (id.isPresent()) {
            productList = productService.searchResults("", id.get().toString(), "1", "1", "",  PageRequest.of(currentPage-1, pageSize));
            //productList = productService.findAllByCategoryId(id.get(), PageRequest.of(currentPage-1, pageSize));
            category = categoryService.get(id.get());
        } else {
            productList = productService.searchResults("", "", "1", "1", "",  PageRequest.of(currentPage-1, pageSize));
            //productList = productService.findPaginated("", PageRequest.of(currentPage-1, pageSize));
            category = new Category();
            category.setName("All Categories");
        }


        model.addAttribute("currentPage", currentPage);

        int totalPages = productList.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }



        model.addAttribute("productList", productList);
        model.addAttribute("category", category);

        return "/client/category";
    }

    @RequestMapping(value = "/part-search", method = RequestMethod.GET)
    public String partSearch(Model model
            , @RequestParam("name") Optional<String> name
            , @RequestParam("make") Optional<String> make
            , @RequestParam("model") Optional<String> model2
            , @RequestParam("year") Optional<String> year
            , @RequestParam("category") Optional<String> category
            , @RequestParam("page") Optional<Integer> page
            , @RequestParam("size") Optional<Integer> size) {

        String keyword="",makeId="1", modelId="1", categoryId="", yearId="";

        if(name.isPresent()){
            keyword = name.get();
        }
        if(make.isPresent()){
            makeId = make.get();
        }
        if(model2.isPresent()){
            modelId = model2.get();
        }
        if(year.isPresent()){
            yearId=year.get();
        }
        if(category.isPresent()){
            categoryId=category.get();
        }

        model.addAttribute("name", keyword);
        model.addAttribute("make", makeId);
        model.addAttribute("model", modelId);
        model.addAttribute("year", yearId);
        model.addAttribute("category", categoryId);




        int currentPage = page.orElse(1);
        int pageSize = size.orElse(PAGE_SIZE);


        model.addAttribute("classActivePartSearch", "home active ");

        //All categories
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categories", categoryList);

        List<Empresa> listMake = makeService.findAll();
        model.addAttribute("listMake", listMake);

        List<com.proyecto.servidor.model.Contrato> listModel = modelService.getModels(Long.parseLong(makeId));
        model.addAttribute("listModel", listModel);

        List<Integer> listYear = utility.getYears();
        model.addAttribute("listYear", listYear);


        //All parts
        Page<Product> productList = productService.searchResults(keyword, categoryId, makeId, modelId, yearId
                ,  PageRequest.of(currentPage-1, pageSize));
        model.addAttribute("productList", productList);


        model.addAttribute("currentPage", currentPage);

        int totalPages = productList.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }


        return "/client/inm-buscar";
    }

  

    @RequestMapping("/part-details")
    public String partDetails(@RequestParam("id") Long id, Model model) {
        model.addAttribute("classActivePartSearch", "home active ");
        model.addAttribute("search", new Product());

        //All categories
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categories", categoryList);

        List<Empresa> listMake = makeService.findAll();
        model.addAttribute("listMake", listMake);

        List<com.proyecto.servidor.model.Contrato> listModel = modelService.getModels(listMake.get(0).getId());
        model.addAttribute("listModel", listModel);

        List<Integer> listYear = utility.getYears();
        model.addAttribute("listYear", listYear);

        try {
            //Get product
            Product product = productService.get(id);
            model.addAttribute("product", product);
        }catch (Exception ex){
            model.addAttribute("error", ex.getMessage());
            return "/client/inm-detalles";
        }
        return "/client/inm-detalles";
    }


}
