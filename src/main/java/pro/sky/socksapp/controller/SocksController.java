package pro.sky.socksapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pro.sky.socksapp.exception.SocksInitializeException;
import pro.sky.socksapp.exception.SocksQueryException;
import pro.sky.socksapp.model.Color;
import pro.sky.socksapp.model.Size;
import pro.sky.socksapp.model.Socks;
import pro.sky.socksapp.model.SocksDTO;
import pro.sky.socksapp.service.SocksService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/socks")
@Validated
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping
    public ResponseEntity<Integer> addSocks(@RequestBody SocksDTO socksDTO) {
        if (socksDTO.getQuantity() < 0) {
            throw new SocksInitializeException("Нельзя загрузить на склад отрицательное число носков");
        }
        Integer quantity = socksService.addSocks(socksDTO.getSocks(), socksDTO.getQuantity());
        return ResponseEntity.ok(quantity);
    }

    @PutMapping
    public ResponseEntity<SocksDTO> getSocks(@RequestBody SocksDTO socksDTO) {
        if (socksDTO.getQuantity() < 0) {
            throw new SocksQueryException("Нельзя получить со склада отрицательное число носков");
        }
        SocksDTO returnedValue = socksService.getSocks(socksDTO.getSocks(), socksDTO.getQuantity());
        if (returnedValue == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(returnedValue);
    }

    @GetMapping
    public ResponseEntity<Integer> findSocks(Color color, Size size, @RequestParam(defaultValue = "0") @Min(0) @Max(100) int minCotton,
                                             @RequestParam(defaultValue = "100") @Min(0) @Max(100) int maxCotton) {
        int count = socksService.findSocks(color, size, minCotton, maxCotton);
        if (count > 0) {
            return ResponseEntity.ok(count);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSocks(@RequestBody SocksDTO socksDTO) {
        if (socksDTO.getQuantity() < 0) {
            throw new SocksQueryException("Нельзя списать со склада отрицательное число носков");
        }
        SocksDTO returnedValue = socksService.deleteSocks(socksDTO.getSocks(), socksDTO.getQuantity());
        if (returnedValue == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Товар списан успешно");
    }

}
