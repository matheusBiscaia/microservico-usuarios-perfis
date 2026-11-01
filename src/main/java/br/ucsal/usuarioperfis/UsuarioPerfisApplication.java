package br.ucsal.usuarioperfis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UsuarioPerfisApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsuarioPerfisApplication.class, args);
    }
}
