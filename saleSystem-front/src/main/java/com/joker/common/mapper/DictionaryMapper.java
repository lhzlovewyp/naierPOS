package com.joker.common.mapper;

import com.joker.common.model.Dictionary;
import com.joker.core.annotation.DataSource;

import org.springframework.stereotype.Repository;

/**
 * Created by crell on 2016/5/18.
 */
@Repository
public interface DictionaryMapper {

    @DataSource("master")
    Boolean add(Dictionary dictionary);

}
