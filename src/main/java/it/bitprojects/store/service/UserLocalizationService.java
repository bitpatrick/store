package it.bitprojects.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.bitprojects.store.repository.Warehouse;
import it.bitprojects.store.response.GeoLocationResponse;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserLocalizationService {

	@Autowired
	private Warehouse warehouse;

	private final String GEO_LOCATION_API_URL = "http://ip-api.com/json/";
	private final String IP_DEFAULT = "2.32.163.10";

	private GeoLocationResponse getLocationByIp(String ipAddress) {

		if (ipAddress.equals("127.0.0.1")) {
			ipAddress = IP_DEFAULT;
		}

		RestTemplate restTemplate = new RestTemplate();

		GeoLocationResponse response = restTemplate
				.getForObject(GEO_LOCATION_API_URL + ipAddress + "?fields=country,currency", GeoLocationResponse.class);
		return response;
	}

	public void updateUserLocalization(HttpServletRequest request, Authentication authentication) {

		GeoLocationResponse geoLocationResponse = getLocationByIp(request.getRemoteAddr());

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		String username = userDetails.getUsername();

		warehouse.updateUserLocalization(username, geoLocationResponse);

	}

}