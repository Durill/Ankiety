package com.example.Ankiety;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FormsRepository extends CrudRepository<Forms, Long>, JpaRepository<Forms, Long> {

    @Query("SELECT f.NumberOfChoices FROM Forms f WHERE f.FormName= :name AND f.Email = :email")
    Integer findFormByName(@Param(value = "name") String name,@Param(value = "email") String email);

    @Query("SELECT f.Answer1, f.Answer2, f.Answer3, f.Answer4, f.Answer5 FROM Forms f WHERE f.FormName= :name AND f.Email = :email")
    String findAnswers(@Param(value = "name") String name, @Param(value = "email") String email);

    @Query("SELECT f.Quantity1, f.Quantity2, f.Quantity3, f.Quantity4, f.Quantity5 FROM Forms f WHERE f.FormName= :formName AND f.Email = :email AND f.id = :id")
    String showQuantities(@Param(value = "formName") String formName, @Param(value = "email") String email, @Param(value = "id") Long id);

    @Query("SELECT f.id FROM Forms f WHERE f.FormName= :formName AND f.Email = :email")
    Long findId(@Param(value = "formName") String formName, @Param(value = "email") String email);


    @Modifying
    @Transactional
    @Query("update Forms f set f.Quantity1 =:quantity1, f.Quantity2 =:quantity2, f.Quantity3 =:quantity3, f.Quantity4 =:quantity4, f.Quantity5 =:quantity5  WHERE f.FormName= :formName AND f.Email = :email")
    void updateQuantity(@Param(value = "formName") String formName,@Param(value = "email") String email
            ,@Param(value = "quantity1") Integer quantity1 ,@Param(value = "quantity2") Integer quantity2
            ,@Param(value = "quantity3") Integer quantity3 ,@Param(value = "quantity4") Integer quantity4
            ,@Param(value = "quantity5") Integer quantity5);

    @Modifying
    @Transactional
    @Query("update Forms f set f.NumberOfChoices=:choices, f.Answer1 =:answer1, f.Answer2 =:answer2, f.Answer3 =:answer3, f.Answer4 =:answer4, f.Answer5 =:answer5  WHERE f.FormName= :formName AND f.Email = :email")
    void updateAnswers(@Param(value = "formName") String formName,@Param(value = "email") String email,@Param(value = "choices") Integer choices
            ,@Param(value = "answer1") String answer1 ,@Param(value = "answer2") String answer2
            ,@Param(value = "answer3") String answer3 ,@Param(value = "answer4") String answer4
            ,@Param(value = "answer5") String answer5);

    @Query("select case when count(f)> 0 then true else false end from Forms f where lower(f.FormName) like lower(:name) AND lower(f.Email) like lower(:email)")
    Boolean checkIfExist(@Param( value = "name") String formName, @Param(value = "email") String email);

}
