package com.echowire.core.model;

import java.util.List;


public record UserPreferences (List<String> categories, List<String> sources, String language) {
}
