package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.AuthRequest;
import com.mmglowbeauty.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
}
