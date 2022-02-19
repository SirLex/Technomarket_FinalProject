package com.technomarket.model.services;

import com.technomarket.exceptions.BadRequestException;
import com.technomarket.model.dtos.attribute.AttributeAddDTO;
import com.technomarket.model.dtos.attribute.AttributeResponseDTO;
import com.technomarket.model.pojos.Attributes;
import com.technomarket.model.repositories.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    public AttributeResponseDTO addAttribute(AttributeAddDTO attributeDTO) {
        if(attributeRepository.existsByName(attributeDTO.getName())){
            throw new BadRequestException("There is already attribute with that name");
        }
        Attributes attribute = new Attributes();
        attribute.setName(attributeDTO.getName());
        attributeRepository.save(attribute);
        return new AttributeResponseDTO(attribute);
    }

    public AttributeResponseDTO getById(int id) {
        if(!attributeRepository.existsById(id)){
            throw new BadRequestException("There is no attribute with this id");
        }
        return new AttributeResponseDTO(attributeRepository.getById(id));
    }

    public Attributes getWholeById(int id) {
        if(!attributeRepository.existsById(id)){
            throw new BadRequestException("There is no attribute with this id");
        }
        return attributeRepository.getById(id);
    }
}
