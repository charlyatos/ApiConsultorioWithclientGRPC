package com.alan.finalAPIconsultorios.service;

import com.alan.finalAPIconsultorios.dtos.DoctorGrpcDTO;
import com.doctorsrvc.grpc.Doctor;
import com.doctorsrvc.grpc.DoctorsList;
import com.doctorsrvc.grpc.Empty;
import com.doctorsrvc.grpc.doctorServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl  {

    public List<Doctor> getAlldoctors() {
        ManagedChannel ch = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        doctorServiceGrpc.doctorServiceBlockingStub stub = doctorServiceGrpc.newBlockingStub(ch);

        DoctorsList listDoctorsFromGrpc = stub.getAllDoctors(Empty.newBuilder().build());

        ch.shutdown();

        List<Doctor> listDoctors =  listDoctorsFromGrpc.getDoctorsListList();

        listDoctors.forEach(doctor -> System.out.println(doctor));

        System.out.println( listDoctors.toString());
        return listDoctors;
    }



}
