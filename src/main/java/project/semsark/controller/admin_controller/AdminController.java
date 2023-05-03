package project.semsark.controller.admin_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.semsark.jwt.JwtUtil;
import project.semsark.model.entity.User;
import project.semsark.model.request_body.EmailRequest;
import project.semsark.model.response_body.UserResponse;
import project.semsark.service.admin_service.AdminService;

import java.util.List;

@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
public class AdminController {
    @Autowired
    AdminService adminService;


    @Autowired
    private JwtUtil jwtUtil;

    @DeleteMapping(value = "/deleteUser")
    public void deleteUser(@RequestBody EmailRequest request) {
        adminService.deleteUser(request.getEmail());
    }

    @DeleteMapping("/deleteAd/{id}")
    public void deleteAd(@PathVariable long id) {
        adminService.deleteAd(id);
    }

    @PostMapping(value = "/suspendUser")
    public void suspendUser(@RequestBody EmailRequest request) {
        adminService.suspendUser(request.getEmail());
    }

    @PostMapping(value = "/unSuspendUser")
    public void unSuspendUser(@RequestBody EmailRequest request) {
        adminService.unSuspendUser(request.getEmail());
    }

    @PostMapping(value = "/verifyUser")
    public void verifyUser(@RequestBody EmailRequest request) {
        adminService.verifyUser(request.getEmail());
    }

    @PostMapping(value = "/unVerifyUser")
    public void unVerifyUser(@RequestBody EmailRequest request) {
        adminService.unVerifyUser(request.getEmail());
    }

    ///////////////////////////////////////////////////////////
    @GetMapping("/getAllUsers")
    List<UserResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/getAllPendingUsers")
    List<UserResponse> getAllPendingUsers() {
        return adminService.getPendingUsers();
    }

    @GetMapping("/test")
    User getTokenParam() {
        return jwtUtil.getUserDataFromToken();
    }

}
