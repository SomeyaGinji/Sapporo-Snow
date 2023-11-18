package org.vaadin.example.service;

import org.springframework.stereotype.Service;
import org.vaadin.example.data.ShovelingPlace;
import org.vaadin.example.repository.SnowRepository;

import java.util.List;

@Service
public class SnowService {

    private final SnowRepository snowRepository;

    public SnowService(SnowRepository snowRepository){
        this.snowRepository = snowRepository;
    }

    public void insertShovelingPlace(ShovelingPlace shovelingPlace){
        snowRepository.insertShovelingPlace(shovelingPlace);
    }

    public List<ShovelingPlace> getShovelingPlaceList(){
        return snowRepository.getShovelingPlaceList();
    }

    public void updateAvailability(Long id){snowRepository.updateAvailability(id);}

}
