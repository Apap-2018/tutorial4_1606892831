package com.apap.tutorial3.service;

import java.util.ArrayList;
import java.util.List;

import com.apap.tutorial3.model.PilotModel;
import org.springframework.stereotype.Service;

@Service
public class PilotInMemoryService implements PilotService{
	private List<PilotModel> archivePilot;
	
	
	public PilotInMemoryService() {
		archivePilot = new ArrayList<>();
	}
	
	@Override
	public void addPilot(PilotModel pilot) {
		archivePilot.add(pilot);
	}

	@Override
	public List<PilotModel> getPilotList() {
		return archivePilot;
	}

	@Override
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		PilotModel pilot = null;
		for(PilotModel ln : archivePilot) {
			if(ln.getLicenseNumber().equals(licenseNumber)) {
				pilot = ln;
				break;
			}
		}
		return pilot;
	}

	@Override
	public void deletePilot(String pilot) {
		for (PilotModel id : archivePilot) {
			if(id.getId().equalsIgnoreCase(pilot)) {
				archivePilot.remove(id);
		}
		
		
	}

	
}
}
