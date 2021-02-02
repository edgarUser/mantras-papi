package mx.rememberme.mantras.papi.endpoints;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.springframework.beans.factory.annotation.Autowired;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import mx.rememberme.mantras.papi.exceptions.InternalErrorException;
import mx.rememberme.mantras.papi.responses.MantraResponse;
import mx.rememberme.mantras.papi.services.MantraService;

@Path("/")
public class MantraEndpoint {

	@Autowired
	private MantraService mantraService;

	private static Logger logger = LogManager.getLogger(MantraEndpoint.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("mantra")
	@ManagedAsync
	public void getMantra(@Suspended final AsyncResponse async) {
		final List<MantraResponse> response = new ArrayList<MantraResponse>();
		final CountDownLatch outerLatch = new CountDownLatch(1);

		mantraService.getMantra().subscribe(new SingleObserver<List<MantraResponse>>() {
			public void onSubscribe(Disposable d) {
			}

			public void onSuccess(List<MantraResponse> mantraResponse) {
				logger.debug("mantra response : {}", mantraResponse);
				response.addAll(mantraResponse);
				outerLatch.countDown();
			}

			public void onError(Throwable e) {
				logger.debug(e);
				async.resume(e);
				outerLatch.countDown();
			}
		});

		try {
			if (!outerLatch.await(10, TimeUnit.SECONDS)) {
				async.resume(new InternalErrorException());
			}
		} catch (Exception e) {
			async.resume(new InternalErrorException());
		}

		async.resume(response);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("mantra/{id}")
	@ManagedAsync
	public void getMantraById(@Suspended final AsyncResponse async, @PathParam("id") final int id) {

		final MantraResponse response = new MantraResponse();
		final CountDownLatch outerLatch = new CountDownLatch(1);

		mantraService.getMantraById(id).subscribe(new SingleObserver<MantraResponse>() {
			public void onSubscribe(Disposable d) {
			}

			public void onSuccess(MantraResponse mantraResponse) {
				logger.debug("mantra response : {}", mantraResponse);
				response.setDesc(mantraResponse.getDesc());
				response.setWords(mantraResponse.getWords());
				outerLatch.countDown();
			}

			public void onError(Throwable e) {
				logger.debug(e);
				async.resume(e);
				outerLatch.countDown();
			}
		});

		try {
			if (!outerLatch.await(10, TimeUnit.SECONDS)) {
				async.resume(new InternalErrorException());
			}
		} catch (Exception e) {
			async.resume(new InternalErrorException());
		}

		async.resume(response);
	}
}
