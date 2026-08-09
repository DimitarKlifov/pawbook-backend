package com.dimitar.booking.services;


import com.dimitar.booking.dto.WorkDayRequest;
import com.dimitar.booking.dto.WorkDayResponse;
import com.dimitar.booking.entities.WorkDay;
import com.dimitar.booking.repositories.WorkDayRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkDayService {
    private final WorkDayRepository workDayRepository;

    public WorkDayService(WorkDayRepository workDayRepository) {
        this.workDayRepository = workDayRepository;
    }

    public WorkDayResponse setWorkDay(WorkDayRequest request){
        if(request.isOpen() && (request.getStartTime() == null || request.getEndTime() == null)){
            throw new IllegalArgumentException("starTime and endTime are required when the day is open");
        }

        WorkDay workDay = workDayRepository.findByDate(request.getDate())
                .orElse(new WorkDay());

        workDay.setDate((request.getDate()));
        workDay.setOpen(request.isOpen());
        workDay.setStartTime(request.getStartTime());
        workDay.setEndTime(request.getEndTime());

        WorkDay saved = workDayRepository.save(workDay);
        return toResponse(saved);
    }


    public List<WorkDayResponse> getAllWorkDays(){
        return workDayRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public WorkDayResponse getWorkDayByDate(LocalDate date){
        WorkDay workDay = workDayRepository.findByDate(date)
                .orElseThrow(() -> new IllegalArgumentException("No WorkDay found for date: " + date));

        return toResponse(workDay);
    }


    private WorkDayResponse toResponse(WorkDay workDay) {
        return new WorkDayResponse(
                workDay.getId(),
                workDay.getDate(),
                workDay.isOpen(),
                workDay.getStartTime(),
                workDay.getEndTime()
        );
    }
}
