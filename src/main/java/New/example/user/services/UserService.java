package New.example.user.services;

import New.example.user.model.User;
import New.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String register(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Email id is already present, please try a new email.";
        }
        userRepository.save(user);
        return "registration Succesfull";
    }

    public boolean authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
}
