package mx.rememberme.mantras.papi.adapters;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import mx.rememberme.mantras.papi.exceptions.InternalErrorException;
import mx.rememberme.mantras.papi.exceptions.MantraNotFoundException;
import mx.rememberme.mantras.papi.responses.MantraResponse;

@Component
public class MantraAdapter {

	private static Logger logger = LogManager.getLogger(MantraAdapter.class);
	private static final String MANTRA_SAPI_END_POINT = "http://localhost:8085/mantra/%d";

	public Single<MantraResponse> getMantraById(int id) {
		return Single.create(new SingleOnSubscribe<MantraResponse>() {
			public void subscribe(SingleEmitter<MantraResponse> subscriber) {
				try {
					String endPoint = String.format(MANTRA_SAPI_END_POINT, id);
					logger.debug("endpoint : {}", endPoint);
					URL url = new URL(endPoint);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");

					BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					MantraResponse response = readResponseFromMantraResponse(bf);
					subscriber.onSuccess(response);
				} catch (MantraNotFoundException mnfe) {
					logger.debug(mnfe);
					subscriber.onError(mnfe);
				} catch (Exception e) {
					logger.debug(e);
					subscriber.onError(new InternalErrorException());
				}
			}
		});
	}

	private MantraResponse readResponseFromMantraResponse(BufferedReader in) throws Exception {
		String inputLine;
		StringBuffer response = new StringBuffer();
		MantraResponse mantraResponse;
		try {

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();
			String responseString = response.toString();
			Gson gson = new Gson();

			mantraResponse = gson.fromJson(responseString, MantraResponse.class);

			if (mantraResponse != null) {
				return gson.fromJson(responseString, MantraResponse.class);
			} else {
				throw new MantraNotFoundException();
			}

		} catch (Exception e) {
			logger.debug(e);
			throw new MantraNotFoundException();
		} finally {
			in.close();
		}
	}

}
