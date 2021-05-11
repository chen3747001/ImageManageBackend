package com.chen.imagemanage.model.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private String bio;
    private String email;
    private String mobile;
    private String role;
}
