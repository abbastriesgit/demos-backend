package com.abbasali.demosbackend.revision_master.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Note {
    int id;
    int parentId;
    int userId;
    String title;
    String body;
}
