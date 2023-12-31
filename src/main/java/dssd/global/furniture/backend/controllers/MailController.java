package dssd.global.furniture.backend.controllers;

import dssd.global.furniture.backend.model.MailStructure;
import dssd.global.furniture.backend.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MailController {
    private final String baseUrl = "/api/mail";
    @Autowired
    private MailService mailService;
    @CrossOrigin(origins = "http://localhost:60000", allowCredentials = "true")
    @GetMapping(baseUrl + "/sendMailToIndicateStartOfManufacturing/{mail}")
    public ResponseEntity<String> sendMailToIndicateStartOfManufacturing(@PathVariable String mail){
        System.out.println("Se lanzara mail de inicio");
        MailStructure mailStructure = new MailStructure(
                "Indicativo de iniciación de la fabricación",
                "Se informa que la tarea de fabricación de la colección ha iniciado. Se informara cuando este proceso finalice."
        );
        mailService.sendMail(mail,mailStructure);
        return ResponseEntity.ok("El mail fue enviado con exito");
    }
    @CrossOrigin(origins = "http://localhost:60000", allowCredentials = "true")
    @GetMapping(baseUrl + "/sendMailToIndicateEndOfManufacturing/{mail}")
    public ResponseEntity<String> sendMailToIndicateEndOfManufacturing(@PathVariable String mail){
        System.out.println("Se lanzara mail de fin");
        MailStructure mailStructure = new MailStructure(
                "Indicativo de finalización en la fabricación",
                "Se informa que la tarea de fabricación de la colección ha sido completada satisfactoriamente"
        );
        mailService.sendMail(mail,mailStructure);
        return ResponseEntity.ok("El mail fue enviado con exito");
    }
}

