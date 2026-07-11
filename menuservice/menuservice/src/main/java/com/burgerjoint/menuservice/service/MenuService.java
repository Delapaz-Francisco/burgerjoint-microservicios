package com.burgerjoint.menuservice.service;

import com.burgerjoint.menuservice.dto.MenuDTO;
import com.burgerjoint.menuservice.model.Menu;
import com.burgerjoint.menuservice.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<MenuDTO> listarTodo() {
        return menuRepository.findAll().stream()
                .map(this::convertirAEntityADto)
                .collect(Collectors.toList());
    }

    public MenuDTO buscarPorId(Long id) {
        Menu menu = menuRepository.findById(id).orElse(null);
        return (menu != null) ? convertirAEntityADto(menu) : null;
    }

    public MenuDTO guardar(MenuDTO menuDTO) {
        Menu menu = convertirADtoAEntity(menuDTO);
        Menu guardado = menuRepository.save(menu);
        return convertirAEntityADto(guardado);
    }

    public void eliminar(Long id) {
        menuRepository.deleteById(id);
    }

    private MenuDTO convertirAEntityADto(Menu menu) {
        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getId());
        dto.setNombre(menu.getNombre());
        dto.setDescripcion(menu.getDescripcion());
        dto.setPrecio(menu.getPrecio());
        dto.setCategoria(menu.getCategoria());
        dto.setStock(menu.getStock());
        return dto;
    }

    private Menu convertirADtoAEntity(MenuDTO dto) {
        Menu entity = new Menu();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrecio());
        entity.setCategoria(dto.getCategoria());
        entity.setStock(dto.getStock());
        return entity;
    }

}
