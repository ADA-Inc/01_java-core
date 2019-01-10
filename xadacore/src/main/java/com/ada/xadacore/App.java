package com.ada.xadacore;

import com.ada.fics.accessdata.persistence.mapper.GestionadorEmpresasMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.ada")
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        GestionadorEmpresasMapper gestionadorEmpresasMapper = null;
        ConfigurableApplicationContext abc = SpringApplication.run(App.class, args);
        abc.getBean(gestionadorEmpresasMapper.getClass() );
        abc.close();
    }

}