package com.prakritis.covid19tracker.controllers;

import com.prakritis.covid19tracker.models.LocationStats;
import com.prakritis.covid19tracker.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BaseController{

    @Autowired
    DataService dataService;

    @GetMapping("/")
    public String homePage(Model model)
    {
        List<LocationStats>  statsRecords = dataService.getStatsRecords();
        Integer totalCases = statsRecords.stream().mapToInt(stats -> stats.getLatestCaseCounts()).sum();
        Integer newCases = statsRecords.stream().mapToInt(stats -> stats.getDiffFromPrevDay()).sum();
        model.addAttribute("statsRecords",statsRecords);
        model.addAttribute("totalCases",totalCases);
        model.addAttribute("newCases",newCases);
        return "homePage";
    }
}
