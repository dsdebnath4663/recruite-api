package com.recruitment.service;

import com.recruitment.model.UserGroup;
import com.recruitment.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    @Autowired
    private final GroupRepository groupRepository;

    public UserGroup createGroup ( UserGroup group ) {
        return groupRepository.save(group);
    }

    public List<UserGroup> getAllGroups () {
        return groupRepository.findAll();
    }

    public UserGroup updateGroup ( Long id , UserGroup updatedGroup ) {
        UserGroup group = groupRepository.findById(id)
                                         .orElseThrow(() -> new RuntimeException("Group not found with ID: " + id));
        group.setName(updatedGroup.getName());
        group.setDescription(updatedGroup.getDescription());
        group.setMembers(updatedGroup.getMembers());
        return groupRepository.save(group);
    }

    public void deleteGroup ( Long id ) {
        groupRepository.deleteById(id);
    }
}
