package com.dimitar.booking.services;

import com.dimitar.booking.config.CurrentUserProvider;
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
    private final CurrentUserProvider currentUserProvider;

    public PetService(PetRepository petRepository, UserRepository userRepository, CurrentUserProvider currentUserProvider) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public PetResponse createPet(PetRequest request){
        User owner = currentUserProvider.getCurrentUser();

        Pet pet = new Pet(request.getName(), request.getBreed(), request.getNotes(), request.getSize(), owner);
        Pet saved = petRepository.save(pet);

        return toResponse(saved);
    }

    public List<PetResponse> getAllPets(){
        User currentUser = currentUserProvider.getCurrentUser();

        List<Pet> pets = petRepository.findAll().stream()
                .filter(pet -> pet.getOwner().getId() == (currentUser.getId()))
                .collect(Collectors.toList());

        return pets.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PetResponse getPetById(Long id){
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found: " + id));

        checkOwnership(pet);

        return toResponse(pet);
    }

    public PetResponse updatePet(Long id, PetRequest request){
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found: " + id));

        checkOwnership(pet);

        pet.setName(request.getName());
        pet.setBreed(request.getBreed());
        pet.setNotes(request.getNotes());
        pet.setSize(request.getSize());

        Pet saved = petRepository.save(pet);

        return toResponse(saved);
    }

    public void deletePet(Long id){
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found: " + id));

        checkOwnership(pet);

        petRepository.deleteById(id);
    }

    private void checkOwnership(Pet pet) {
        User currentUser = currentUserProvider.getCurrentUser();

        boolean isOwner = pet.getOwner().getId() == (currentUser.getId());
        boolean isGroomer = currentUser.getRole() == User.Role.GROOMER;

        if (!isOwner && !isGroomer) {
            throw new IllegalArgumentException("You do not have permission to access this pet");
        }
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