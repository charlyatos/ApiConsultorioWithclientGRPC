package com.alan.finalAPIconsultorios.service;

import com.alan.finalAPIconsultorios.models.SharedInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GenericService <T extends SharedInfo, ID extends Serializable>{
    CompletableFuture<T> save(T entityModel);
    CompletableFuture<List<T>> getAll();
    CompletableFuture<T> getById(ID id);
    CompletableFuture<T> update(T entityModel,ID id);
    CompletableFuture<Boolean> delete(ID id);

}
