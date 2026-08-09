package com.dimitar.booking.services;

import com.dimitar.booking.dto.PetRequest;
import com.dimitar.booking.dto.PetResponse;
import com.dimitar.booking.entities.Pet;
import com.dimitar.booking.entities.User;
import com.dimitar.booking.repositories.PetRepository;
import com.dimitar.booking.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetService(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public PetResponse createPet(PetRequest request){
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found: " + request.getOwnerId()));

        Pet pet = new Pet(request.getName(), request.getBreed(), request.getNotes(), request.getSize(), owner);
        Pet saved = petRepository.save(pet);

        return toResponse(saved);
    }

    public List<PetResponse> getAllPets(){
        return petRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PetResponse getPetById(Long id){
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found: " + id));
        return toResponse(pet);
    }

    public PetResponse updatePet(Long id, PetRequest request){
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found: " + id));
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found: " + request.getOwnerId()));

        pet.setName(request.getName());
        pet.setBreed(request.getBreed());
        pet.setNotes(request.getNotes());
        pet.setSize(request.getSize());
        pet.setOwner(owner);

        Pet saved = petRepository.save(pet);

        return toResponse(saved);
    }

    public void deletePet(Long id){
        if(!petRepository.existsById(id)){
            throw new IllegalArgumentException("Pet not found: " + id);
        }
        petRepository.deleteById(id);
    }








    private PetResponse toResponse(Pet pet) {
        return new PetResponse(
                pet.getId(),
                pet.getName(),
                pet.getBreed(),
                pet.getNotes(),
                pet.getSize(),
                pet.getOwner().getId()
        );
    }
}
