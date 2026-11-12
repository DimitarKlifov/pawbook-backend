package com.dimitar.booking.config;

import com.dimitar.booking.entities.*;
import com.dimitar.booking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final GroomerRepository groomerRepository;
    private final PetRepository petRepository;
    private final GroomingServiceRepository groomingServiceRepository;
    private final ServicePricingRepository servicePricingRepository;
    private final WorkDayRepository workDayRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, GroomerRepository groomerRepository, PetRepository petRepository, GroomingServiceRepository groomingServiceRepository, ServicePricingRepository servicePricingRepository, WorkDayRepository workDayRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.groomerRepository = groomerRepository;
        this.petRepository = petRepository;
        this.groomingServiceRepository = groomingServiceRepository;
        this.servicePricingRepository = servicePricingRepository;
        this.workDayRepository = workDayRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            System.out.println("=== Seed data already present, skipping ===");
            return;
        }

        User anaUser = new User("Ana", "ana@example.com", passwordEncoder.encode("GroomerPass123!"), "070123456", User.Role.GROOMER);
        anaUser.setEmailVerified(true);
        anaUser = userRepository.save(anaUser);

        Groomer groomer = new Groomer(anaUser);
        groomer = groomerRepository.save(groomer);

        User client2User = new User("Second Client", "client2@test.com", passwordEncoder.encode("Test1234!"), "070222333", User.Role.CLIENT);
        client2User.setEmailVerified(true);
        client2User = userRepository.save(client2User);


        User markoUser = new User("Marko", "marko@example.com", passwordEncoder.encode("Test1234!"), "070999888", User.Role.CLIENT);
        markoUser.setEmailVerified(true);
        markoUser = userRepository.save(markoUser);

        Pet rex = new Pet("Rex", "Labrador", "Very friendly, loves treats", DogSize.LARGE, markoUser);
        rex = petRepository.save(rex);

        // Full Groom - all sizes
        GroomingService fullGroom = new GroomingService("Целосно чешлање", "Full Groom", groomer);
        fullGroom = groomingServiceRepository.save(fullGroom);
        servicePricingRepository.save(new ServicePricing(fullGroom, DogSize.SMALL, new BigDecimal("20.00"), 45));
        servicePricingRepository.save(new ServicePricing(fullGroom, DogSize.MEDIUM, new BigDecimal("28.00"), 60));
        servicePricingRepository.save(new ServicePricing(fullGroom, DogSize.LARGE, new BigDecimal("35.00"), 75));

        // Bath & Brush - all sizes
        GroomingService bathAndBrush = new GroomingService("Капење и чешлање", "Bath & Brush", groomer);
        bathAndBrush = groomingServiceRepository.save(bathAndBrush);
        servicePricingRepository.save(new ServicePricing(bathAndBrush, DogSize.SMALL, new BigDecimal("12.00"), 30));
        servicePricingRepository.save(new ServicePricing(bathAndBrush, DogSize.MEDIUM, new BigDecimal("16.00"), 40));
        servicePricingRepository.save(new ServicePricing(bathAndBrush, DogSize.LARGE, new BigDecimal("20.00"), 50));

        // Nail Trim - all sizes
        GroomingService nailTrim = new GroomingService("Стрижење нокти", "Nail Trim", groomer);
        nailTrim = groomingServiceRepository.save(nailTrim);
        servicePricingRepository.save(new ServicePricing(nailTrim, DogSize.SMALL, new BigDecimal("8.00"), 15));
        servicePricingRepository.save(new ServicePricing(nailTrim, DogSize.MEDIUM, new BigDecimal("8.00"), 15));
        servicePricingRepository.save(new ServicePricing(nailTrim, DogSize.LARGE, new BigDecimal("10.00"), 20));

        WorkDay today = new WorkDay(LocalDate.now(), true, LocalTime.of(9, 0), LocalTime.of(17, 0));
        workDayRepository.save(today);

        System.out.println("=== Seed data loaded ===");
        System.out.println("Groomer user id: " + anaUser.getId());
        System.out.println("Client 2 user id: " + client2User.getId() + " (email: client2@test.com, password: Test1234!)");
        System.out.println("Client user id: " + markoUser.getId());
        System.out.println("Pet id: " + rex.getId());
        System.out.println("Services: Full Groom, Bath & Brush, Nail Trim (all with SMALL/MEDIUM/LARGE pricing)");
        System.out.println("Today marked open, 9:00-17:00");
    }
}