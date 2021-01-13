package com.example.Ankiety;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Forms {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min=3, max=50)
    private String FormName;

    @Email
    @NotEmpty
    private String Email;

    private Integer NumberOfChoices=0;

    private String Answer1;
    private Integer Quantity1=0;
    private String Answer2;
    private Integer Quantity2=0;
    private String Answer3;
    private Integer Quantity3=0;
    private String Answer4;
    private Integer Quantity4=0;
    private String Answer5;
    private Integer Quantity5=0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormName() {
        return FormName;
    }

    public void setFormName(String form_name) {
        this.FormName = form_name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Integer getNumberOfChoices() {
        return NumberOfChoices;
    }

    public void setNumberOfChoices(Integer number_of_choices) {
        this.NumberOfChoices = number_of_choices;
    }

    public String getAnswer1() {
        return Answer1;
    }

    public void setAnswer1(String answer1) {
        Answer1 = answer1;
    }

    public String getAnswer2() {
        return Answer2;
    }

    public void setAnswer2(String answer2) {
        Answer2 = answer2;
    }

    public String getAnswer3() {
        return Answer3;
    }

    public void setAnswer3(String answer3) {
        Answer3 = answer3;
    }

    public String getAnswer4() {
        return Answer4;
    }

    public void setAnswer4(String answer4) {
        Answer4 = answer4;
    }

    public String getAnswer5() {
        return Answer5;
    }

    public void setAnswer5(String answer5) {
        Answer5 = answer5;
    }

    public Integer getQuantity1() {
        return Quantity1;
    }

    public void setQuantity1(Integer quantity1) {
        Quantity1 = quantity1;
    }

    public Integer getQuantity2() {
        return Quantity2;
    }

    public void setQuantity2(Integer quantity2) {
        Quantity2 = quantity2;
    }

    public Integer getQuantity3() {
        return Quantity3;
    }

    public void setQuantity3(Integer quantity3) {
        Quantity3 = quantity3;
    }

    public Integer getQuantity4() {
        return Quantity4;
    }

    public void setQuantity4(Integer quantity4) {
        Quantity4 = quantity4;
    }

    public Integer getQuantity5() {
        return Quantity5;
    }

    public void setQuantity5(Integer quantity5) {
        Quantity5 = quantity5;
    }
}
