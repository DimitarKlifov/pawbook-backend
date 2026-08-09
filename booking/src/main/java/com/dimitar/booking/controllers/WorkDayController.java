package com.dimitar.booking.controllers;

import com.dimitar.booking.dto.WorkDayRequest;
import com.dimitar.booking.dto.WorkDayResponse;
import com.dimitar.booking.services.WorkDayService;
import jakarta.validation.Valid;
import org.hibernate.jdbc.Work;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/workdays")
public class WorkDayController {
    private final WorkDayService workDayService;

    public WorkDayController(WorkDayService workDayService) {
        this.workDayService = workDayService;
    }

    @PutMapping
    public ResponseEntity<WorkDayResponse> setWorkDay(@Valid @RequestBody WorkDayRequest request){
        WorkDayResponse response = workDayService.setWorkDay(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<WorkDayResponse>> getAllWorkDays(){
        return ResponseEntity.ok(workDayService.getAllWorkDays());
    }

    @GetMapping("/{date}")
    public ResponseEntity<WorkDayResponse> getWorkDayByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date){
        return ResponseEntity.ok(workDayService.getWorkDayByDate(date));
    }
}
