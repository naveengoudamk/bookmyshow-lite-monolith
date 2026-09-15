package com.bookmyshow.controller;

import com.bookmyshow.dto.ScreenRequestDTO;
import com.bookmyshow.dto.ScreenResponseDTO;
import com.bookmyshow.service.ScreenService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
public class ScreenController {

    private final ScreenService screenService;

    public ScreenController(ScreenService screenService){
        this.screenService=screenService;
    }

    @PostMapping
    public ScreenResponseDTO createScreen(@Valid @RequestBody ScreenRequestDTO dto){

        return screenService.createScreen(dto);
    }

    @GetMapping
    public List<ScreenResponseDTO> getAllScreens(){

        return screenService.getAllScreens();
    }

    @GetMapping("/{id}")
    public ScreenResponseDTO getScreenById(@PathVariable Long id){

        return screenService.getScreenById(id);
    }

    @PutMapping("/{id}")
    public ScreenResponseDTO updateScreen(@PathVariable Long id,
                                          @Valid @RequestBody ScreenRequestDTO dto){

        return screenService.updateScreen(id,dto);
    }

    @DeleteMapping("/{id}")
    public void deleteScreen(@PathVariable Long id){

        screenService.deleteScreen(id);
    }
}