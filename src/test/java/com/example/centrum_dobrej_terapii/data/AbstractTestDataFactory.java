package com.example.centrum_dobrej_terapii.data;

public interface AbstractTestDataFactory<T1, T2> {
    T1 create(T2 type);
}
