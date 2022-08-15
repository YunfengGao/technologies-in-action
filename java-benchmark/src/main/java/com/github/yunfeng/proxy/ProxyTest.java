package com.github.yunfeng.proxy;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
public class ProxyTest {
    @Benchmark
    public String getName() {
        IFoo foo = new Foo();
        return foo.getName();
    }

    @Benchmark
    public String getDynamicName() {
        IFoo foo = new Foo();
        InvocationHandler handler = new ProxyHandler(foo);
        IFoo foo2 = (IFoo) Proxy.newProxyInstance(IFoo.class.getClassLoader(), new Class[]{IFoo.class}, handler);
        return foo2.getName();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ProxyTest.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }

    static class ProxyHandler implements InvocationHandler{
        private final IFoo object;
        public ProxyHandler(IFoo object){
            this.object = object;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            method.invoke(object, args);
            return null;
        }
    }

    interface IFoo {
        String getName();
    }

    static class Foo implements IFoo {
        private static final String name = "foo";

        @Override
        public String getName() {
            return name;
        }
    }
}