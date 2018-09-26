package com.apap.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add(@RequestParam(value="id", required = true) String id,
			@RequestParam(value="licenseNumber", required = true) String licenseNumber,
			@RequestParam(value="name", required = true) String name,
			@RequestParam(value="flyHour", required = true) int flyHour) {
				
		PilotModel pilot = new PilotModel(id,licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";
		
	}
		
	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot",archive);
		
		return "view-pilot";
		
	}
	
	@RequestMapping("/pilot/viewall")
	public String viewall (Model model) {
		List<PilotModel> archive = pilotService.getPilotList();
		model.addAttribute("ListPilot", archive);
		return "viewall-pilot";
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
	
	@RequestMapping(value= {"/pilot/update/license-number/{licenseNumber}/fly-hour","/pilot/update/license-number/{licenseNumber}/fly-hour/{flyHour}"})
	public String update(@PathVariable Optional <String> licenseNumber, @PathVariable Optional <Integer>flyHour, Model model) {
		
		if(licenseNumber.isPresent()) {
			model.addAttribute("licenseNumber",licenseNumber.get());
			PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber.get());
			if(archive != null) {
				archive.setFlyHour(flyHour.get());
				model.addAttribute("pilot",archive);
			}
			else {
				model.addAttribute("error", "licenseNumber kosong atau tidak ditemukan dan proses update dibatalkan");
				return "error";
			}
		}
		else {
			model.addAttribute("error", "licenseNumber kosong atau tidak ditemukan dan proses ​update dibatalkan");
			return "error";
		}
		model.addAttribute("error","Berhasil update");
		return "error";
	}
	
	@RequestMapping(value= {"/pilot/delete/id","/pilot/delete/id/{id}"})
	public String delete(@PathVariable Optional <String> id, Model model ) {
		List<PilotModel> archive = pilotService.getPilotList();
		if(id.isPresent()) {
			for(PilotModel pilot : archive) {
				if(pilot.getId().equalsIgnoreCase(id.get())) {
					pilotService.deletePilot(pilot.getId());
					model.addAttribute("error", "Delete Berhasil");
				}
				else {
					model.addAttribute("error", "id kosong atau tidak ditemukan dan proses delete dibatalkan");
				}
			}
			
		}
		else {
			model.addAttribute("error","id kosong atau tidak ditemukan dan proses delete dibatalkan");
			return "error";
		}
		
		return "error";
		
	}
		
	
	
	
	
	
	
	
	
}
