package com.github.yunfeng.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.TimeUnit;
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

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
public class Deserialization {
    String string = "{\"inner\" : {\"id\" : \"test\"}}";
    ObjectMapper mapper = new ObjectMapper();

    @Benchmark
    public void withoutSetter() throws JsonProcessingException {
        ClassWithoutSetter withoutSetter = mapper.readValue(string, ClassWithoutSetter.class);
    }

    @Benchmark
    public void withSetter() throws JsonProcessingException {
        ClassWithSetter withSetter = mapper.readValue(string, ClassWithSetter.class);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(Deserialization.class.getSimpleName())
            .build();
        new Runner(opt).run();
    }
}
