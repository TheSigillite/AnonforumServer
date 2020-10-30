package com.tijo.anonforum.domain.mapper;

public interface TwoToOneMapper <To,From1,From2> {
    To convert(From1 from1, From2 from2);
}
