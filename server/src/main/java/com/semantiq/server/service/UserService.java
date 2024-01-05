package com.semantiq.server.service;

import com.semantiq.server.entity.User;
import com.semantiq.server.repository.ChatBotRepo;
import com.semantiq.server.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
      private final UserRepo userRepo;
      private final PasswordEncoder passwordEncoder;
      private final ChatBotRepo chatBotRepo;

      @Autowired
      public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, ChatBotRepo chatBotRepo) {
          this.userRepo = userRepo;
          this.passwordEncoder = passwordEncoder;
          this.chatBotRepo = chatBotRepo;
      }

      // Find User by email if it exists in database
      public User findUserByEmail(String email) {
          return userRepo.findByEmail(email);
      }

      // Find User by id if it exists in database
      public User findUserById(int id) { return userRepo.findById(id); }

      // Save User to Database
      public void saveUser(User user) {
          userRepo.save(user);
      }

      public boolean verifyUser(String email, int verificationCode) {
          User user = findUserByEmail(email);
          if (user.getVerificationCode() == verificationCode) {
              // Set the user's isVerified property to true and save it
              user.setVerified(true);
              saveUser(user);
              return true;
          } else {
              return false;
          }
      }

      public String encodePassword(String password) {
          return passwordEncoder.encode(password);
      }

      public boolean checkPassword(String password, String email) {
          User user = findUserByEmail(email);
          return passwordEncoder.matches(password, user.getPassword());
      }

      public boolean deleteBot(String email) {
          User user = findUserByEmail(email);
          int botID = user.getBot().getId();

          // Check if user has chatBot
          if (user.getBot() == null) {
              return false;
          }

          // Set user's bot to null
          user.setBot(null);
          saveUser(user);

          chatBotRepo.deleteById(botID);

          return true;
      }
}
