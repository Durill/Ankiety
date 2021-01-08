package com.example.Ankiety;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private FormsRepository formsRepository;



    @GetMapping("/")
    public String showCreateForm(Model model){
        model.addAttribute("former", new Forms());
        Forms forms = new Forms();
        return "create_form";
    }

    @PostMapping("/definegroup")
    public String createForm(@ModelAttribute Forms forms, Model model, String formName, String email){
        model.addAttribute("former", forms);
        if(!formsRepository.checkIfExist(formName, email)){
            formsRepository.save(forms);
        }
        model.addAttribute("name", forms.getFormName());
        model.addAttribute("email", forms.getEmail());
        Integer findedForm = formsRepository.findFormByName(forms.getFormName(), forms.getEmail());
        model.addAttribute("choices", findedForm);
        String answerOne = formsRepository.findAnswers(forms.getFormName(), forms.getEmail());
        List<String> splitStr = Arrays.stream(answerOne.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        model.addAttribute("splitanswer", splitStr);
        String quantities = formsRepository.showQuantities(formName, email);
        List<String> splitQnt = Arrays.stream(quantities.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        model.addAttribute("splitQuantities", splitQnt);
        return "define_group";
    }


    @PostMapping("/showanswers")
    public String showAnswers(@ModelAttribute Forms forms, Model model
    , String formName, String email, Integer quantity1, Integer quantity2
            , Integer quantity3, Integer quantity4, Integer quantity5){
        model.addAttribute("former", forms);
        formsRepository.updateQuantity( formName, email, quantity1, quantity2, quantity3, quantity4, quantity5);
        String answerOne = formsRepository.findAnswers(forms.getFormName(), forms.getEmail());
        model.addAttribute("former", forms);
        model.addAttribute("name", forms.getFormName());
        model.addAttribute("email", forms.getEmail());
        String quantities = formsRepository.showQuantities(formName, email);
        List<String> splitQnt = Arrays.stream(quantities.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        model.addAttribute("splitQuantities", splitQnt);
        List<String> splitStr = Arrays.stream(answerOne.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        model.addAttribute("splitanswer", splitStr);
        return "show_answers";

    }




}
