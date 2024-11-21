package com.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;

    }

    public void createUser(String user_id, String password,String email){
        User user= new User();

        String hashedPassword = passwordEncoder.encode(password);

        user.setUsername(user_id);
        user.setPassword(hashedPassword);
        user.setEmail(email);
        
        userRepository.save(user);

    }
}
