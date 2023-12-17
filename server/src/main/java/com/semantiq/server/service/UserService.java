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
      public void saveUser(User user) {
          userRepo.save(user);
      }

      public boolean verifyUser(String email, int verificationCode) {
          User user = findUserByEmail(email);
          if (user.getVerificationCode() == verificationCode) {
              // Set the user's isVerified property to true and save it
              user.setVerified(true);
              userRepo.save(user);
              return true;
          } else {
              return false;
          }
      }
}
