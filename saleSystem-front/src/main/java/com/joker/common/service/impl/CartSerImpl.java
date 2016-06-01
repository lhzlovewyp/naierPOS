package com.joker.common.service.impl;

import com.joker.common.model.Business;
import com.joker.common.model.Cart;
import com.joker.common.service.BusinessSer;
import com.joker.common.service.CartSer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by crell on 2015/6/18.
 */
@Service
public class CartSerImpl implements CartSer {

   // protected MongoTemplate mongoTemplate;

    @Autowired
    BusinessSer businessSer;

    public Boolean add(String userId,String businessId) {
        Business business = businessSer.getBusinessById(businessId);

        Query query = new Query();
        Criteria criteria1 = Criteria.where("userId").is(userId);
        Criteria criteria2 = Criteria.where("business").is(business);
        query.addCriteria(criteria1);
        query.addCriteria(criteria2);
        Update update = new Update().inc("num", 1);

       // mongoTemplate.upsert(query,update,Cart.class);
        return true;
    }

    public List<Cart> getCart(String userId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("userId").is(userId);
        query.addCriteria(criteria);
       // List<Cart> cartList = mongoTemplate.find(query, Cart.class);
        return null;
    }
}
