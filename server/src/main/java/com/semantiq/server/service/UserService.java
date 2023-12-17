package com.semantiq.server.service;

import com.semantiq.server.entity.User;
import com.semantiq.server.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
      private final UserRepo userRepo;

      @Autowired
      public UserService(UserRepo userRepo) {
          this.userRepo = userRepo;
      }

      // Find User by email if it exists in database
      public User findUserByEmail (String email) {
          return userRepo.findByEmail(email);
      }

      // Save User to Database
      public User saveUser(User user) {
          return userRepo.save(user);
      }
}
