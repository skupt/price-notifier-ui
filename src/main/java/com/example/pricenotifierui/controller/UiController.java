package com.example.pricenotifierui.controller;

import com.example.pricenotifierui.entity.Product;
import com.example.pricenotifierui.entity.Task;
import com.example.pricenotifierui.entity.restclient.SearchTaskDTO;
import com.example.pricenotifierui.repository.ProductRepository;
import com.example.pricenotifierui.repository.TaskRepository;
import com.example.pricenotifierui.service.UiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/")

public class UiController {

    @Autowired
    private UiService uiService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/tasks")
    public String getTasks(HttpSession session, HttpServletRequest request, Model model) {
        Page<Task> pageTasks = (Page<Task>) session.getAttribute("pageTasks");

        String cmd = request.getParameter("cmd");
        if (cmd != null) {
            switch (cmd) {
                case "prevPage":
                    if (pageTasks.hasPrevious()) {
                        Pageable pageable = pageTasks.previousOrFirstPageable();
                        pageTasks = taskRepository.findAll(pageable);
                    }
                    session.setAttribute("pageTasks", pageTasks);
                    model.addAttribute("taskList", pageTasks.getContent());
                    break;
                case "nextPage":
                    Pageable pageable = pageTasks.nextOrLastPageable();
                    pageTasks = taskRepository.findAll(pageable);
                    session.setAttribute("pageTasks", pageTasks);
                    model.addAttribute("taskList", pageTasks.getContent());
                    break;
            }
        } else {
            Pageable pageable = PageRequest.of(0, 10);
            pageTasks = taskRepository.findAll(pageable);
            session.setAttribute("pageTasks", pageTasks);
            model.addAttribute("taskList", pageTasks.getContent());
        }

        return "tasks";
    }

    @GetMapping("/tasks/search/new")
    public String getSearchTaskForm(Model model) {
        model.addAttribute("searchTaskDto", new SearchTaskDTO());
        return "task_search_new";
    }

    @PostMapping("/tasks/search/")
    public String createNewSearchTask(@ModelAttribute("searchTaskDto") @Valid SearchTaskDTO searchTaskDto,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "task_search_new";
        searchTaskDto.setUsername("testUser");
        long externalId = uiService.createNewSearchTask(searchTaskDto).getId();
        System.out.println("externalId: " + externalId);
        return "redirect:/tasks";
    }

    @GetMapping("/products")
    public String getProductsOfProduct(@RequestParam("taskId") long taskId, HttpSession session, HttpServletRequest request, Model model) {
        Page<Product> pageProducts = (Page<Product>) session.getAttribute("pageProducts");
        System.out.println("TaskId: " + taskId);

        String cmd = request.getParameter("cmd");
        if (pageProducts != null & cmd != null) {
            switch (cmd) {
                case "prevPage":
                    if (pageProducts.hasPrevious()) {
                        Pageable pageable = pageProducts.previousOrFirstPageable();
                        pageProducts = productRepository.findAllByTask_Id(taskId, pageable);
                    }
                    session.setAttribute("pageProducts", pageProducts);
                    model.addAttribute("productList", pageProducts.getContent());
                    model.addAttribute("taskId", taskId);

                    break;
                case "nextPage":
                    Pageable pageable = pageProducts.nextOrLastPageable();
                    pageProducts = productRepository.findAllByTask_Id(taskId, pageable);
                    session.setAttribute("pageProducts", pageProducts);
                    model.addAttribute("productList", pageProducts.getContent());
                    model.addAttribute("taskId", taskId);

                    break;
            }
        } else {
            Pageable pageable = PageRequest.of(0, 10);
            pageProducts = productRepository.findAllByTask_Id(taskId, pageable);
            session.setAttribute("pageProducts", pageProducts);
            model.addAttribute("productList", pageProducts.getContent());
            model.addAttribute("taskId", taskId);
        }

        return "products";
    }
}
