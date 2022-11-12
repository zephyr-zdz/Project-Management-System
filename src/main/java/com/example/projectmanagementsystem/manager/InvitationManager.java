package com.example.projectmanagementsystem.manager;

import com.example.projectmanagementsystem.mapper.InvitationMapper;
import com.example.projectmanagementsystem.model.entity.Invitation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("InvitationManager")
public class InvitationManager {
    private final InvitationMapper invitationMapper;

    @Autowired
    InvitationManager(InvitationMapper invitationMapper) {
        this.invitationMapper = invitationMapper;
    }

    public List<Invitation> findAllByReceiverId(Integer receiverId) {
        return invitationMapper.findAllByReceiverId(receiverId);
    }

    public void save(Invitation invitation) {
        invitationMapper.save(invitation);
    }

    public Invitation findById(Integer invitationId) {
        return invitationMapper.findInvitationById(invitationId);
    }

    public void delete(Integer invitationId) {
        invitationMapper.deleteById(invitationId);
    }
}
