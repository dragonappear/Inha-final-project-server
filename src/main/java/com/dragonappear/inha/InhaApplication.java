package com.dragonappear.inha;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class InhaApplication {

	public static void main(String[] args) {
		SpringApplication.run(InhaApplication.class, args);
	}

	// 임의의 세션정보-> 수정 필요
	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of(UUID.randomUUID().toString());
	}
	
	@Bean
	JPAQueryFactory jpaQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}

	//PutMapping,DeleteMapping을 사용하기 위해 Bean 주입
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}

	@Bean
	Hibernate5Module hibernate5Module() {
		return new Hibernate5Module();
	}
}
