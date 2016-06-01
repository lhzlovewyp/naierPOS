package com.joker.common.mapper;

import com.joker.common.model.Business;
import com.joker.core.annotation.DataSource;
import com.joker.core.dto.Page;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by crell on 2015/6/18.
 */
@Repository
public interface BusinessMapper {

    @DataSource("master")
    Boolean add(Business business);

    @DataSource("master")
    Boolean update(Business business);

    @DataSource("master")
    Boolean del(@Param("businessId") String businessId);

    @DataSource("slave")
    List<Business> getBusinessList(Page page);

    @DataSource("slave")
    Business getBusinessById(@Param("businessId") String businessId);
}
