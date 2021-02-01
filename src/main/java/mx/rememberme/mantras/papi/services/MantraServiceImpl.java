package mx.rememberme.mantras.papi.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import mx.rememberme.mantras.papi.responses.MantraResponse;

public class MantraServiceImpl implements MantraService {

	private static Logger logger = LogManager.getLogger(MantraServiceImpl.class);
	private static final String MANTRA_SAPI_END_POINT = "http://localhost:8085/mantra/%d";

	@Override
	public Single<MantraResponse> getMantraById(int id) {
		return Single.create(new SingleOnSubscribe<MantraResponse>() {
			public void subscribe(SingleEmitter<MantraResponse> suscriber) {
				try {
					String endPoint = String.format(MANTRA_SAPI_END_POINT, id);
					logger.debug("endpoint : {}", endPoint);
					URL url = new URL(endPoint);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");

					BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					MantraResponse response = readResponseFromMantraResponse(bf);
					suscriber.onSuccess(response);

				} catch (Exception e) {
				}
			}
		});
	}

	private MantraResponse readResponseFromMantraResponse(BufferedReader in) throws Exception {
		String inputLine;
		StringBuffer response = new StringBuffer();
		try {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			String responseString = response.toString();
			Gson gson = new Gson();
			return gson.fromJson(responseString, MantraResponse.class);

		} catch (Exception e) {
			throw new Exception();
		} finally {
			in.close();
		}
	}

}
