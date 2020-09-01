package com.example.portfolio.repositories;

import com.example.portfolio.entities.Photo;
import org.springframework.data.repository.PagingAndSortingRepository;


import java.util.List;

public interface PhotoRepository extends PagingAndSortingRepository<Photo,Integer> {
    Photo findByTitle(String title);

}
