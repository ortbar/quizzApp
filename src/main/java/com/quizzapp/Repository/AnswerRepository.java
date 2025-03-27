package com.quizzapp.Repository;


import com.quizzapp.Models.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository <AnswerEntity, Long> {

}
