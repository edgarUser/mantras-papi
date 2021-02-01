package mx.rememberme.mantras.papi.services;

import org.springframework.beans.factory.annotation.Autowired;

import io.reactivex.Single;
import mx.rememberme.mantras.papi.adapters.MantraAdapter;
import mx.rememberme.mantras.papi.responses.MantraResponse;

public class MantraServiceImpl implements MantraService {

	private MantraAdapter mantraAdapter;

	@Autowired
	public MantraServiceImpl(MantraAdapter mantraAdapter) {
		this.mantraAdapter = mantraAdapter;
	}

	@Override
	public Single<MantraResponse> getMantraById(int id) {
		return this.mantraAdapter.getMantraById(id);
	}

}
