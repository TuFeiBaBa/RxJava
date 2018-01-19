/**
 * Copyright (c) 2016-present, RxJava Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package io.reactivex;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 提供了一个来接收推送式通知的机制
 * <p>
 * 在一个Observer调用一个{@link Observable}的{@link Observable#subscribe subscribe}方法，
 * 首先，这哥Observable会调用{@link #onSubscribe(Disposable)}方法，通过传递一个能够在任何时间里打断取消
 * 事件序列的{@link Disposable}实例作为参数，{@link #onComplete}方法
 * 然后，Observable可能会调用这个Observer的{@link #onNext}任意次数，来传递通知。这是规矩。
 * 最后，这个Observable将会只调用一次Observer的{@link #onComplete}方法，或者调用一次Observer的{@link #onError}
 * 方法(两者互斥)。
 * @see <a href="http://reactivex.io/documentation/observable.html">ReactiveX documentation: Observable</a>
 * @param <T>
 *          Observer期望观察的事件(事物)的具体类型
 *
 * Provides a mechanism for receiving push-based notifications.
 * <p>
 * After an Observer calls an {@link Observable}'s {@link Observable#subscribe subscribe} method,
 * first the Observable calls {@link #onSubscribe(Disposable)} with a {@link Disposable} that allows
 * cancelling the sequence at any time, then the
 * {@code Observable} may call the Observer's {@link #onNext} method any number of times
 * to provide notifications. A well-behaved
 * {@code Observable} will call an Observer's {@link #onComplete} method exactly once or the Observer's
 * {@link #onError} method exactly once.
 *
 * @see <a href="http://reactivex.io/documentation/observable.html">ReactiveX documentation: Observable</a>
 * @param <T>
 *          the type of item the Observer expects to observe
 */
public interface Observer<T> {

    /**
     * Provides the Observer with the means of cancelling (disposing) the
     * connection (channel) with the Observable in both
     * synchronous (from within {@link #onNext(Object)}) and asynchronous manner.
     * @param d the Disposable instance whose {@link Disposable#dispose()} can
     * be called anytime to cancel the connection
     * @since 2.0
     */
    void onSubscribe(@NonNull Disposable d);

    /**
     * Provides the Observer with a new item to observe.
     * <p>
     * The {@link Observable} may call this method 0 or more times.
     * <p>
     * The {@code Observable} will not call this method again after it calls either {@link #onComplete} or
     * {@link #onError}.
     *
     * @param t
     *          the item emitted by the Observable
     */
    void onNext(@NonNull T t);

    /**
     * Notifies the Observer that the {@link Observable} has experienced an error condition.
     * <p>
     * If the {@link Observable} calls this method, it will not thereafter call {@link #onNext} or
     * {@link #onComplete}.
     *
     * @param e
     *          the exception encountered by the Observable
     */
    void onError(@NonNull Throwable e);

    /**
     * Notifies the Observer that the {@link Observable} has finished sending push-based notifications.
     * <p>
     * The {@link Observable} will not call this method if it calls {@link #onError}.
     */
    void onComplete();

}
