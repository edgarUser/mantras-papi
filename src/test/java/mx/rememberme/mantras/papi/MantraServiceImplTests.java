package mx.rememberme.mantras.papi;

import static org.mockito.Mockito.doReturn;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;
import io.reactivex.subscribers.TestSubscriber;
import mx.rememberme.mantras.papi.adapters.MantraAdapter;
import mx.rememberme.mantras.papi.exceptions.InternalErrorException;
import mx.rememberme.mantras.papi.responses.MantraResponse;
import mx.rememberme.mantras.papi.services.MantraServiceImpl;

public class MantraServiceImplTests {

	@Mock
	MantraAdapter mockMantraAdapter;

	private MantraServiceImpl mantraService;
	private int id = 1;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mantraService = new MantraServiceImpl(mockMantraAdapter);
	}

	@After
	public void tearDown() {
		mantraService = null;
	}

	@Test
	public void testBasePath() {
		MantraResponse expectedResponse = new MantraResponse();
		expectedResponse.setDesc("Back to roots");
		expectedResponse.setWords("");
		doReturn(Single.just(expectedResponse)).when(mockMantraAdapter).getMantraById(id);
		TestSubscriber<MantraResponse> testSubscriber = new TestSubscriber<MantraResponse>();
		mantraService.getMantraById(id).toFlowable().subscribe(testSubscriber);
		testSubscriber.assertValue(expectedResponse);
	}

	@Test
	public void testErrorPath() {
		doReturn(Single.error(new InternalErrorException())).when(mockMantraAdapter).getMantraById(id);
		TestSubscriber<MantraResponse> testSubscriber = new TestSubscriber<MantraResponse>();
		mantraService.getMantraById(id).toFlowable().subscribe(testSubscriber);
		testSubscriber.assertError(InternalErrorException.class);
	}

	@Test
	public void testSubscription() {
		doReturn(Single.never()).when(mockMantraAdapter).getMantraById(id);
		TestSubscriber<MantraResponse> testSubscriber = new TestSubscriber<MantraResponse>();
		mantraService.getMantraById(id).toFlowable().subscribe(testSubscriber);
		testSubscriber.assertSubscribed();
	}

	@Test
	public void testComplete() {
		MantraResponse expectedResponse = new MantraResponse();
		expectedResponse.setDesc("Back to roots");
		expectedResponse.setWords("");
		doReturn(Single.just(expectedResponse)).when(mockMantraAdapter).getMantraById(id);
		TestSubscriber<MantraResponse> testSubscriber = new TestSubscriber<MantraResponse>();
		mantraService.getMantraById(id).toFlowable().subscribe(testSubscriber);
		testSubscriber.assertComplete();
	}

}
