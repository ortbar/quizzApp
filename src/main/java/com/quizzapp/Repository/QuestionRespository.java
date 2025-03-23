package com.quizzapp.Repository;

import com.quizzapp.Models.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRespository extends JpaRepository<QuestionEntity, Long> {


}
