package com.apap.tutorial4.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value="/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("pilot",new PilotModel());
		return "addPilot";
	}
	
	@RequestMapping(value="/pilot/add", method= RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
			pilotService.addPilot(pilot);
			return "add";
	}
	
	@RequestMapping(value= {"/pilot/view/license-number","/pilot/view/license-number/{licenseNumber}"})
	public String viewPilot(@PathVariable Optional <String> licenseNumber, Model model) {
		String error = "nomor licenseNumber kosong atau tidak ditemukan";
		if(licenseNumber.isPresent()) {
			model.addAttribute("licenseNumber",licenseNumber.get());
			PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber.get());
			if(archive != null) {
				model.addAttribute("pilot",archive);
			}
			else {
				model.addAttribute("error", error);
				return "error";
			}
		}
		else {
			model.addAttribute("error", error);
			return "error";
		}
		
		return "view-pilot";
	}
}
