package edu.wong.attendance_management_api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("edu.wong.attendance_management_api.mapper")
public class AttendanceManagementApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AttendanceManagementApiApplication.class, args);
    }
}
