package io.pivotal.literx;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Learn how to create Flux instances.
 *
 * @author Sebastien Deleuze
 * @see <a href="http://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html">Flux Javadoc</a>
 */
public class Part01FluxTest {

    Part01Flux workshop = new Part01Flux();

//========================================================================================

    @Test
    public void empty() {
        Flux<String> flux = workshop.emptyFlux();

        StepVerifier.create(flux)
                .verifyComplete();
    }

//========================================================================================

    @Test
    public void fromValues() {
        Flux<String> flux = workshop.fooBarFluxFromValues();
        StepVerifier.create(flux)
                .expectNext("foo", "bar")
                .verifyComplete();
    }

//========================================================================================

    @Test
    public void fromList() {
        Flux<String> flux = workshop.fooBarFluxFromList();
        StepVerifier.create(flux)
                .expectNext("foo", "bar")
                .verifyComplete();
    }

//========================================================================================

    @Test
    public void error() {
        Flux<String> flux = workshop.errorFlux();
        StepVerifier.create(flux)
                .verifyError(IllegalStateException.class);
    }

//========================================================================================

    @Test
    public void countEach100ms() {
        Flux<Long> flux = workshop.counter();
        StepVerifier.create(flux)
                .expectNext(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L)
                .verifyComplete();
    }

    @Test
    public void testFluxJust() {
        Flux<String> one = Flux.just("one");

        one.subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(0);
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    @Test
    public void testMerge() {
        Flux.merge(Flux.just("one"), Flux.just("two"))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subscription.request(2);
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
