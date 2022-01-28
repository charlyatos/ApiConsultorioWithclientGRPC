package com.alan.finalAPIconsultorios.controller;

import com.alan.finalAPIconsultorios.dtos.SimpleUserDTO;
import com.alan.finalAPIconsultorios.exception.ResourceNotFoundException;
import com.alan.finalAPIconsultorios.models.User;
import com.alan.finalAPIconsultorios.serviceImpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl usrSrv;

    ModelMapper modelMapper = new ModelMapper();

    User mockUser  = new User(1, "String name", "String lastName", "String surname", "String email", 1, "String username");

    User mockUser2  = new User(1, "StringAcambiar", "StringAcambiar", "StringAcambiar", "StringAcambiar", 1, "String username");

    User mockUserResultUpdate  = new User(2, "String name", "String lastName", "String surname", "String email", 1, "String username");


    @Test
    @Description("Get One User Asynchronously")
    public void getUserTest() throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        SimpleUserDTO dto = modelMapper.map(mockUser,SimpleUserDTO.class);
        given(usrSrv.getOneUserDTO(Mockito.anyLong()))
                .willReturn(
                        CompletableFuture.completedFuture(dto)
                );

        ObjectMapper mapr = new ObjectMapper();

        String body = mapr.writeValueAsString(dto);
        MvcResult response =
                this.mockMvc.perform(get("http://localhost:8080/api/v1/users/46").accept(MediaType.APPLICATION_JSON) )
                        .andExpect(request().asyncStarted())
                        .andExpect(status().is2xxSuccessful())
                        .andReturn();

        this.mockMvc.perform(asyncDispatch(response))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(body));

    }

    @Test
    @Description("Post One User Asynchronously")
    public void shouldPostAUser()throws Exception{
        given(usrSrv.save(Mockito.any()))
                .willReturn(CompletableFuture.completedFuture(mockUser));

        ObjectMapper objectMapper = new ObjectMapper();

        String body = objectMapper.writeValueAsString(mockUser);

        MvcResult result = this.mockMvc.perform(post("http://localhost:8080/api/v1/users")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(request().asyncStarted())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        this.mockMvc.perform(asyncDispatch(result))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(body,false));
    }

    @Test
    @Description("Update One User Asynchronously")
    public void shouldUpdateUser()throws Exception{
        //SimpleUserDTO dto = modelMapper.map(mockUser,SimpleUserDTO.class);
        given(usrSrv.updateUser(Mockito.any(),Mockito.anyLong()))
                .willReturn(CompletableFuture.completedFuture(mockUserResultUpdate));

        ObjectMapper objectMapper = new ObjectMapper();

        String body = objectMapper.writeValueAsString(mockUserResultUpdate);


        MvcResult result = this.mockMvc.perform(put("http://localhost:8080/api/v1/users/44")
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(request().asyncStarted())
                .andExpect(status().is2xxSuccessful())
                .andReturn();


        this.mockMvc.perform(asyncDispatch(result))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.status").value(2))
                .andExpect(jsonPath("$.*",hasSize(13)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(body,false));
    }


    @Test
    @Description("Get all Users Asynchronously")
    public void shouldGetAll()throws Exception{
        SimpleUserDTO usedto1 = modelMapper.map(mockUser,SimpleUserDTO.class);
        SimpleUserDTO userdto2 = modelMapper.map(mockUser2,SimpleUserDTO.class);
        List<SimpleUserDTO> usersList = new ArrayList<>();
        usersList.add(usedto1);
        usersList.add(userdto2);

        given(usrSrv.getAllDTO()).willReturn(CompletableFuture.completedFuture(usersList));

        ObjectMapper objectMapper = new ObjectMapper();

        String body = objectMapper.writeValueAsString(usersList);

        MvcResult result = this.mockMvc.perform(get("http://localhost:8080/api/v1/users").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(request().asyncStarted())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        this.mockMvc.perform(asyncDispatch(result))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(body,false));
    }

    @Test
    @Description("Delete oneUserDTO Asynchronously")
    public void deleteUserDTOtest()throws Exception{
        given(usrSrv.deleteUser(Mockito.anyLong()))
                .willReturn(CompletableFuture.completedFuture(true));

        ObjectMapper objectMapper = new ObjectMapper();

        String body = objectMapper.writeValueAsString(true);


        MvcResult result = this.mockMvc.perform(delete("http://localhost:8080/api/v1/users/44")
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(request().asyncStarted())
                .andExpect(status().is2xxSuccessful())
                .andReturn();


        this.mockMvc.perform(asyncDispatch(result))
                .andDo(print())
                .andExpect(status().isOk());


    }








}