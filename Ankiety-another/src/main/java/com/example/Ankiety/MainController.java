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
        isSaved=false;
        return "create_form";
    }

    @PostMapping("/yourform")
    public String saveForm(@ModelAttribute Forms forms, Model model, String formName, String email){
        model.addAttribute("former", forms);

        if(!formsRepository.checkIfExist(formName, email) & isSaved==false){
            formsRepository.save(forms);
            isSaved=true;
        }else if(formsRepository.checkIfExist(formName, email) & isSaved==true & formsRepository.findFormByName(formName, email)<5){
            String answerOne = formsRepository.findAnswers(formName, email);
            List<String> splitStr = Arrays.stream(answerOne.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            Integer choices = formsRepository.findFormByName(formName, email);
            splitStr.set(choices, "");
            if(choices<5){
                choices++;
            }
            formsRepository.updateAnswers(formName,email,choices,splitStr.get(0),splitStr.get(1),splitStr.get(2),splitStr.get(3),splitStr.get(4));
            choicesOfForm(forms, model, formName, email);
        }

            model.addAttribute("name", forms.getFormName());
            model.addAttribute("email", forms.getEmail());
            choicesOfForm(forms, model, formName, email);

            return "your_form";
    }

    @PostMapping("/saving")
    public String saving(@ModelAttribute Forms forms, Model model, String formName, String email){
        model.addAttribute("former", forms);
        String answerOne = formsRepository.findAnswers(formName, email);
        List<String> splitStr = Arrays.stream(answerOne.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        Integer choices = formsRepository.findFormByName(formName, email);
        formsRepository.updateAnswers(formName,email,choices,splitStr.get(0),splitStr.get(1),splitStr.get(2),splitStr.get(3),splitStr.get(4));
        return "saving";
    }

    public void choicesOfForm(@ModelAttribute Forms forms, Model model, String formName, String email) {
        Integer choices = formsRepository.findFormByName(formName, email);
        model.addAttribute("choices", formsRepository.findFormByName(formName, email));


        String answerOne = formsRepository.findAnswers(formName, email);
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
    }

    @PostMapping("/definegroup")
    public String createForm(@ModelAttribute Forms forms, Model model, String formName, String email
    ,Integer quantity1, Integer quantity2, Integer quantity3, Integer quantity4, Integer quantity5){
        model.addAttribute("former", forms);
        model.addAttribute("name", forms.getFormName());
        model.addAttribute("email", forms.getEmail());

        choicesOfForm(forms, model, formName, email);

        if(formsRepository.checkIfExist(formName, email) & isSaved==true){
            formsRepository.updateQuantity( formName, email, quantity1, quantity2, quantity3, quantity4, quantity5);
        }
        return "define_group";
    }


    @PostMapping("/showanswers")
    public String showAnswers(@ModelAttribute Forms forms, Model model, String formName, String email
            , Integer quantity1, Integer quantity2, Integer quantity3, Integer quantity4, Integer quantity5){
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


}
