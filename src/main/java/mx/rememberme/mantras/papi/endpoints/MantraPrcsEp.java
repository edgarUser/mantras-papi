package mx.rememberme.mantras.papi.endpoints;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ManagedAsync;
import org.springframework.beans.factory.annotation.Autowired;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import mx.rememberme.mantras.papi.responses.MantraResponse;
import mx.rememberme.mantras.papi.services.MantraService;

@Path("/")
public class MantraPrcsEp {

	@Autowired
	private MantraService mantraService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("mantra/{id}")
	@ManagedAsync
	public void getMantraById(@Suspended final AsyncResponse async, @PathParam("id") final int id) {
		final MantraResponse response = new MantraResponse();

		mantraService.getMantraById(id).subscribe();
		final CountDownLatch outerLatch = new CountDownLatch(1);

		mantraService.getMantraById(id).subscribe(new SingleObserver<MantraResponse>() {
			public void onSubscribe(Disposable d) {

			}

			public void onSuccess(MantraResponse mantraResponse) {
				response.setDesc(mantraResponse.getDesc());
				response.setWords(mantraResponse.getWords());
				outerLatch.countDown();
			}

			public void onError(Throwable e) {
				outerLatch.countDown();
			}
		});

		try {
			if (outerLatch.await(10, TimeUnit.SECONDS)) {
				throw new Exception();
			}
		} catch (Exception e) {

		}

		async.resume(response);
	}
}
