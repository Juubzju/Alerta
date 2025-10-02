package br.com.inalerta.inalerta.controller;




import br.com.inalerta.inalerta.entity.Alerta;
import br.com.inalerta.inalerta.service.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queimadas")
@RequiredArgsConstructor

public class AlertaController {

    private final AlertaService inpeService;



    @GetMapping("/recentes")
    public ResponseEntity<List<Alerta>> getFocosRecentes() {
        List<Alerta> focos = inpeService.getUltimosFocos();
        if (focos.isEmpty()) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(focos);
    }
}
