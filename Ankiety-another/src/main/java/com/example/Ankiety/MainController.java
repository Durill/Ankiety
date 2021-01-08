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
    private boolean isSaved=false;



    @GetMapping("/")
    public String showCreateForm(Model model){
        model.addAttribute("former", new Forms());
        Forms forms = new Forms();
        return "create_form";
    }

    @PostMapping("/definegroup")
    public String createForm(@ModelAttribute Forms forms, Model model, String formName, String email
    ,Integer quantity1, Integer quantity2
            , Integer quantity3, Integer quantity4, Integer quantity5){
        model.addAttribute("former", forms);
        if(!formsRepository.checkIfExist(formName, email) & isSaved==false){
            formsRepository.save(forms);
            isSaved=true;
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
        Long showId = formsRepository.findId(formName, email);
        String quantities = formsRepository.showQuantities(formName, email, showId);
        List<String> splitQnt = Arrays.stream(quantities.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        model.addAttribute("splitQuantities", splitQnt);
        if(formsRepository.checkIfExist(formName, email) & isSaved==true){
            formsRepository.updateQuantity( formName, email, quantity1, quantity2, quantity3, quantity4, quantity5);
        }
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
        Long showId = formsRepository.findId(formName, email);
        String quantities = formsRepository.showQuantities(formName, email,showId);
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

    @GetMapping("/stopvoting")
    public String stopVoting(){
        return "stop_voting";
    }

    @RequestMapping("/formsaved")
    @ResponseBody
    public String queryStringMapping(
            @RequestParam String param1,
            @RequestParam String param2) {
        return String.format("Otrzymane wartości: param1=%s, param2=%s", param1, param2);
    }


}
