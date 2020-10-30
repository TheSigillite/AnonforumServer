package com.tijo.anonforum.domain.mapper;

public interface OneToOneMapper <To, From> {
    To convert(From from);
}
