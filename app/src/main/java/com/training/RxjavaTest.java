package com.training;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chenqiuyi on 2018/1/22.
 */

public class RxjavaTest {
    public static void main(String args[]) {
//        Flowable.fromCallable(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                return "start";
//            }
//        })
//                .observeOn(Schedulers.computation())
//                .subscribeOn(Schedulers.single())
//                .subscribe(new FlowableSubscriberFlowableSubscriber<String>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        System.out.println(s);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


        final RxjavaTest test = new RxjavaTest();
        Observable
                .just(1, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        test.print(integer.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void print(String s) {
        System.out.println(s);
    }
}
