package com.dmdev.app;

import com.dmdev.app.entity.Author;
import com.dmdev.app.entity.Initials;
import com.dmdev.app.repositary.AuthorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        var context = SpringApplication.run(Application.class, args);

        var author = Author.builder()
                .initials(Initials.builder()
                        .firstName("Александр")
                        .middleName("Сергеевич")
                        .secondName("Пушкин")
                        .build())
                .build();
        var authorRepository = context.getBean(AuthorRepository.class);
        authorRepository.save(author);
        System.out.println(author);

    }

}
