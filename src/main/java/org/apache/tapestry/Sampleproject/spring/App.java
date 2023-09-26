package org.apache.tapestry.Sampleproject.spring;

import org.hibernate.cfg.Configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class App extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AppConfiguration.class);
    }

    public static void main(String[] args) throws Exception {
      //  SpringApplication application = new SpringApplication(App.class);
       // SpringApplication.run(App.class, args);
        
        Configuration con=new Configuration();
        con.configure("hibernate.cfg.xml");)
        SessionFactory factory=con.buildSessionFactory();
        System.out.println(factory);
        }
}
