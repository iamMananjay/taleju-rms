package com.taleju.rms.service;

import com.taleju.rms.dto.RoleRequest;
import com.taleju.rms.dto.RoleResponse;
import com.taleju.rms.entity.Role;
import com.taleju.rms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Create Role
    public RoleResponse createRole(RoleRequest request) {
        Role role = new Role();
        role.setName(request.getName());
        roleRepository.save(role);
        return new RoleResponse(role.getId(), role.getName());
    }

    // Get all Roles
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> new RoleResponse(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }

    // Update Role
    public RoleResponse updateRole(Long id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if(request.getName() != null) role.setName(request.getName());

        roleRepository.save(role);
        return new RoleResponse(role.getId(), role.getName());
    }

    // Delete Role
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
