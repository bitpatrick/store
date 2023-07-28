package it.bitprojects.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.bitprojects.store.model.Balance;
import it.bitprojects.store.repository.BalanceRepositoryImplementation;
import it.bitprojects.store.repository.Warehouse;
import it.bitprojects.store.response.GeoLocationResponse;
import jakarta.inject.Provider;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

	@Autowired
	private Warehouse warehouse;
	
	@Autowired
	private BalanceRepositoryImplementation balanceRepositoryImplementation;
	
	@Autowired
	private Provider<Balance> balanceProvider;
	

	private final String GEO_LOCATION_API_URL = "http://ip-api.com/json/";
	private final String IP_DEFAULT = "24.48.0.1";

	private GeoLocationResponse getLocationByIp(String ipAddress) {

		if (ipAddress.equals("127.0.0.1")) {
			ipAddress = IP_DEFAULT;
		}

		RestTemplate restTemplate = new RestTemplate();

		GeoLocationResponse response = restTemplate
				.getForObject(GEO_LOCATION_API_URL + ipAddress + "?fields=country,currency", GeoLocationResponse.class);
		return response;
	}

	public void initializeUser(HttpServletRequest request, Authentication authentication) {

		GeoLocationResponse geoLocationResponse = getLocationByIp(request.getRemoteAddr());

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		String username = userDetails.getUsername();

		warehouse.updateUserLocalization(username, geoLocationResponse);
		
		balanceRepositoryImplementation.initializeBalance(balanceProvider.get(),username);

	}

}