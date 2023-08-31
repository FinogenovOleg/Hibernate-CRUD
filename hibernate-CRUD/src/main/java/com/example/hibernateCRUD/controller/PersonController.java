package com.example.hibernateCRUD.controller;

import com.example.hibernateCRUD.dao.PersonDAO;
import com.example.hibernateCRUD.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PersonController {
    PersonDAO personDAO;
    @Autowired
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personDAO.read());
        return "index.html";
    }
    @GetMapping("/new")
    public String create(@ModelAttribute(name = "person")Person person) {
        return "new.html";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.getPersonByID(id));
        return "show.html";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                        Model model) {
        model.addAttribute("person", personDAO.getPersonByID(id));
        return "edit.html";
    }
    @PostMapping("/new")
    public String create(@ModelAttribute(name = "person") Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "new.html";
        personDAO.create(person);
        return "redirect:/people";
    }
    @PostMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
    @PostMapping("/{id}/edite")
    public String edite(Person person, @PathVariable("id") int id,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "redirect:/people/" + id + "/edite";
        personDAO.update(person);
        return "redirect:/people";
    }
}
