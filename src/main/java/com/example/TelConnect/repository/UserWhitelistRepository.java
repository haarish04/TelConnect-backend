package com.example.TelConnect.repository;

import com.example.TelConnect.model.UserWhitelist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWhitelistRepository extends JpaRepository<UserWhitelist ,Long> {
}
