package org.kiev.cinema.service;

import org.kiev.cinema.entity.Address;

import java.util.List;

public interface AddressService {
    void add(Address address);
    List<Address> listAll();
    List<Address> listByMovie(Long movieId);
    List<Address> listByIds(List<Integer> addressIdList);
    Address findById(Integer addressId);
}
