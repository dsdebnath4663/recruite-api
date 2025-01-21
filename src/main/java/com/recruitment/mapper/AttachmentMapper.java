package com.recruitment.mapper;

import com.recruitment.dto.AttachmentDTO;
import com.recruitment.model.Attachment;
import org.springframework.stereotype.Component;

@Component
public class AttachmentMapper {

    public AttachmentDTO toDTO( Attachment attachment) {
        AttachmentDTO dto = new AttachmentDTO();
        dto.setId(attachment.getId());
        dto.setAttachmentType(attachment.getAttachmentType());
        dto.setFilePath(attachment.getFilePath());
        return dto;
    }

    public Attachment toEntity(AttachmentDTO dto) {
        Attachment attachment = new Attachment();
        attachment.setAttachmentType(dto.getAttachmentType());
        attachment.setFilePath(dto.getFilePath());
        return attachment;
    }
}
