package com.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService<UserRepository> {
    
    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;

    }

    public void createUser(String user_id, String password,String email){
        User user= new User();

        user.setUsername(user_id);
        user.setPassword(password);
        user.setEmail(email);
        
        userRepository.save(user);

    }
}
