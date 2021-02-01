package mx.rememberme.mantras.papi.services;

import io.reactivex.Single;
import mx.rememberme.mantras.papi.responses.MantraResponse;

public interface MantraService {
	Single<MantraResponse> getMantraById(int id);
}
