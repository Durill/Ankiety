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
    private boolean formModified=false;
    private boolean redirectAfterVote=false;



    @GetMapping("/")
    public String showCreateForm(Model model){
        model.addAttribute("former", new Forms());
        isSaved=false;
        return "create_form";
    }

    @PostMapping("/yourform")
    public String saveForm(@ModelAttribute Forms forms, Model model, String formName, String email
    ,Integer numberOfChoices, String answer1, String answer2, String answer3, String answer4, String answer5
            , Integer quantity1, Integer quantity2, Integer quantity3, Integer quantity4, Integer quantity5){
        model.addAttribute("former", forms);

        try {
            if(!formsRepository.checkIfExist(formName, email) & isSaved==false){
                formsRepository.save(forms);
                isSaved=true;
                Integer choices = formsRepository.findFormByName(formName, email);
                model.addAttribute("choices", choices);
            }else if(formsRepository.checkIfExist(formName, email) & isSaved==true & formsRepository.findFormByName(formName,email)<=5){
                Integer choices = formsRepository.findFormByName(formName, email);
                model.addAttribute("choices", choices);
                model.addAttribute("splitanswer", convertAnswersToList(formName, email));
                if(formModified==true){
                    formsRepository.updateQuantity( formName, email, quantity1, quantity2, quantity3, quantity4, quantity5);
                    formsRepository.updateAnswers(formName,email,numberOfChoices,answer1,answer2,answer3,answer4,answer5);
                    formModified=false;
                }
                choicesOfForm(forms, model, formName, email);
            }
            model.addAttribute("name", forms.getFormName());
            model.addAttribute("email", forms.getEmail());
            choicesOfForm(forms, model, formName, email);

            return "your_form";

        }catch(Exception e){
            model.addAttribute("formnotadded", true);
        }
        return "create_form";
    }

    @PostMapping("/modifyform")
    public String modifyForm(@ModelAttribute Forms forms, Model model, String formName, String email){
        model.addAttribute("former", forms);

        try {
                Integer choices = formsRepository.findFormByName(formName, email);
                model.addAttribute("choices", choices);
                model.addAttribute("splitanswer", convertAnswersToList(formName, email));

            model.addAttribute("name", forms.getFormName());
            model.addAttribute("email", forms.getEmail());
            choicesOfForm(forms, model, formName, email);
            formModified=true;
            return "modify_form";

        }catch(Exception e){
            model.addAttribute("formnotadded", true);
        }
        return "your_form";
    }

    public List<String> convertAnswersToList(String formname, String email){
        String answerOne = formsRepository.findAnswers(formname, email);
        List<String> splitStr = Arrays.stream(answerOne.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        return splitStr;
    }



    public List<String> convertQuantitiesToList(String formname, String email){
        Long showId = formsRepository.findId(formname, email);

        String quantities = formsRepository.showQuantities(formname, email, showId);
        List<String> splitQnt = Arrays.stream(quantities.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        return splitQnt;
    }

    public void choicesOfForm(@ModelAttribute Forms forms, Model model, String formName, String email) {
        model.addAttribute("choices", formsRepository.findFormByName(formName, email));
        model.addAttribute("splitanswer", convertAnswersToList(formName, email));
        model.addAttribute("splitQuantities", convertQuantitiesToList(formName, email));
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

    @PostMapping("/answersaftervoting")
    public String showAfterVoting(@ModelAttribute Forms forms, Model model, String formName, String email
            , Integer quantity1, Integer quantity2, Integer quantity3, Integer quantity4, Integer quantity5){
        model.addAttribute("former", forms);
        formsRepository.updateQuantity( formName, email, quantity1, quantity2, quantity3, quantity4, quantity5);
        redirectAfterVote=true;

        model.addAttribute("name", forms.getFormName());
        model.addAttribute("email", forms.getEmail());
        model.addAttribute("splitQuantities", convertQuantitiesToList(formName, email));
        model.addAttribute("splitanswer", convertAnswersToList(formName, email));
        return "showaftervoting";
    }



}
