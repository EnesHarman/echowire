package com.echowire.userservice.dto;

import java.util.List;

public record RegisterRequest(String email, String password, List<String> preferredCategories, List<String> preferredSources, String language) {
}
