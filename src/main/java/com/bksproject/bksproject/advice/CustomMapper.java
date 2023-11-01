package com.bksproject.bksproject.advice;

public interface CustomMapper<S, D> {
    D map(S source);

}
