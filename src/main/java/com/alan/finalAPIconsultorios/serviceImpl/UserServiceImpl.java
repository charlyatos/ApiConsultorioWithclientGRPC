package com.alan.finalAPIconsultorios.serviceImpl;

import com.alan.finalAPIconsultorios.dtos.GenericMapperDTO;
import com.alan.finalAPIconsultorios.dtos.SimpleUserDTO;
import com.alan.finalAPIconsultorios.exception.ResourceNotFoundException;
import com.alan.finalAPIconsultorios.models.User;
import com.alan.finalAPIconsultorios.respository.GenericRepository;
import com.alan.finalAPIconsultorios.respository.UserRepository;
import com.alan.finalAPIconsultorios.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl extends GenericServiceImpl<User,Long> implements UserService {
    @Autowired
    private UserRepository userRepository;

    static GenericMapperDTO genericMapperDTO = GenericMapperDTO.singleInstance();

    public UserServiceImpl(GenericRepository<User, Long> genericRepository) {
        super(genericRepository);
    }

    @Async("asyncExecutor")
    @Transactional
    public CompletableFuture<List<SimpleUserDTO>> getAllDTO(){
        System.out.println(Thread.currentThread().getName());
        List<User> entities = userRepository.findAll();
        List<SimpleUserDTO> dtos = new ArrayList<>();
        for(User user: entities){
            SimpleUserDTO userSimpleDTO;
            userSimpleDTO = genericMapperDTO.mapToSimpleUserDTO(user);
            dtos.add(userSimpleDTO);
        }
        return CompletableFuture.completedFuture(dtos);
    }

    @Async("asyncExecutor")
    @Transactional
    public CompletableFuture<SimpleUserDTO> getOneUserDTO(Long id){
        User existingUser = userRepository.getById(id);
        ModelMapper modelMapper = new ModelMapper();
        SimpleUserDTO userDTO = modelMapper.map(existingUser,SimpleUserDTO.class);
       return CompletableFuture.completedFuture(userDTO);
    }

    @Async("asyncExecutor")
    @Transactional
    public CompletableFuture<User> updateUser(User user,Long id){
        User existingUser = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","Id",id));
        /*------------Not Modifiable------------------------*/
        existingUser.setModificationTime(new Date());
        existingUser.setCreationTime(new Date());

        /*-----------Modifiable--------------*/
        existingUser.setRole(user.getRole());
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setLastName(user.getLastName());
        existingUser.setSurname(user.getSurname());
        existingUser.setUserModifier(user.getUserModifier());

        return CompletableFuture.completedFuture(userRepository.save(existingUser));
    }


    @Async("asyncExecutor")
    @Transactional
    public CompletableFuture<Boolean> deleteUser(Long id){
        User existingUser = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","Id",id));
        existingUser.setStatus(2);
        return CompletableFuture.completedFuture(true);
    }

}
