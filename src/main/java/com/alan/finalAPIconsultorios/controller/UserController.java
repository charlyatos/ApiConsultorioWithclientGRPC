package com.alan.finalAPIconsultorios.controller;

import com.alan.finalAPIconsultorios.dtos.SimpleUserDTO;
import com.alan.finalAPIconsultorios.models.User;
import com.alan.finalAPIconsultorios.service.DoctorServiceImpl;
import com.alan.finalAPIconsultorios.serviceImpl.UserServiceImpl;
import com.doctorsrvc.grpc.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/users")
public class UserController extends GenericControllerImpl<User,UserServiceImpl> {

    @Autowired
    private DoctorServiceImpl docSrv;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/docs")
    public ResponseEntity getAllDocs(){
        List<Doctor> ld = docSrv.getAlldoctors();
        if(ld.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return  ResponseEntity.ok().build();

    }

    @Override
    @GetMapping("")
    public CompletableFuture<ResponseEntity> getAll(){
        return userService.getAllDTO().thenApply(ResponseEntity::ok);
    }

    @Override
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity> getOne(@PathVariable Long id){
        CompletableFuture<SimpleUserDTO> exists = userService.getOneUserDTO(id);
        return exists.thenApply(ResponseEntity::ok);
    }

    @PostMapping("")
    public CompletableFuture<ResponseEntity> save(@RequestBody User user){
        user.setModificationTime(new Date());
        user.setCreationTime(new Date());
        user.setRole(1);
        user.setStatus(1);
        return userService.save(user).thenApply(ResponseEntity::ok);
    }

    @Override
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity> update(@RequestBody User user,@PathVariable("id") Long id){
        return userService.updateUser(user,id).thenApply(ResponseEntity::ok);
    }


    @Override
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity> delete(@PathVariable("id")Long id){
        return userService.deleteUser(id).thenApply(ResponseEntity::ok);
    }

}
