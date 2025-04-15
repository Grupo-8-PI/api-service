package school.sptech.hub.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.hub.service.ReservaService;

@RestController
public class ReservaController {

    @Autowired
    private ReservaService reservaService;



}
