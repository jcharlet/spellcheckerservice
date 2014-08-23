package org.jct.spellchecker.wordsrepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication
				.run(Application.class);
		// // ApplicationContext ctx = SpringApplication.run(Application.class,
		// // args);
		//
		// System.out.println("Let's inspect the beans provided by Spring Boot:");
		//
		// String[] beanNames = context.getBeanDefinitionNames();
		// Arrays.sort(beanNames);
		// for (String beanName : beanNames) {
		// System.out.println(beanName);
		// }
		//
		//
		// LanguageRepository repository = context
		// .getBean(LanguageRepository.class);
		//
		// // save a couple of customers
		// repository.save(new Language("EN"));
		// repository.save(new Language("FR"));
		//
		// // fetch all customers
		// Iterable<Language> languages = repository.findAll();
		// System.out.println("Language found with findAll():");
		// System.out.println("-------------------------------");
		// for (Language language : languages) {
		// System.out.println(language);
		// }
		// System.out.println();
		//
		// // fetch an individual customer by ID
		// Language language = repository.findOne(1L);
		// System.out.println("Language found with findOne(1L):");
		// System.out.println("--------------------------------");
		// System.out.println(language);
		// System.out.println();
		//
		// // fetch customers by last name
		// Language enLanguage = repository.findByShortCode("en");
		// System.out.println("Language found with findByShortCode('en'):");
		// System.out.println("--------------------------------------------");
		// System.out.println(enLanguage);
		//
		// context.close();
    }

}
