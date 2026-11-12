package com.dimitar.booking.repositories;

import com.dimitar.booking.entities.SiteImage;
import com.dimitar.booking.entities.SiteImageSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteImageRepository extends JpaRepository<SiteImage, SiteImageSlot> {
}
