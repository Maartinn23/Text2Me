package sintesis.text2me.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sintesis.text2me.models.AppUser;
import sintesis.text2me.repositories.AppUserRepository;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


/*
 *TODO: Añadir logica de la API REST y probar de hacer un GET sobre users con ID especifico para comprovar si se guarda todo bien
 */

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    @Autowired
    private AppUserRepository repo;

   /* @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppUser> getUserById(@PathVariable long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    */
}



