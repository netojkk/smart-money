package com.neto.smart_money.dto;

public record ChangePasswordDTO (String currentPassword, String newPassword, String confirmPassword){
}
