package com.example.projectmanagementsystem.mapper;

import com.example.projectmanagementsystem.model.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationMapper extends JpaRepository<Invitation, Integer> {
    Invitation findInvitationById(Integer id);

    List<Invitation> findAllByReceiverId(Integer ReceiverId);

    void deleteById(Integer id);

}
