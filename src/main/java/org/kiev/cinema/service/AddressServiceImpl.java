package org.kiev.cinema.service;

import org.kiev.cinema.entity.Address;
import org.kiev.cinema.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    @Transactional
    public void add(Address address) {
        addressRepository.save(address);
    }

    @Override
    public List<Address> listAll() {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> listByMovie(Long movieId) {
        return addressRepository.findAllByMovie(movieId);
    }

    @Override
    public List<Address> listByIds(List<Integer> addressIdList) {
        return addressRepository.findAllByIdIn(addressIdList);
    }

    @Override
    public Address findById(Integer addressId) {
        return addressRepository.findOne(addressId);
    }
}
