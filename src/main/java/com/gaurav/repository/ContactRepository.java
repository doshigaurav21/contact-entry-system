package com.gaurav.repository;

import com.gaurav.entity.ContactsEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ContactRepository extends CrudRepository<ContactsEntity, Long> {


    List<ContactsEntity> findAll();

    Optional<ContactsEntity> findById(Long id);

    List<ContactsEntity> findAllBy(Sort sort);
}
