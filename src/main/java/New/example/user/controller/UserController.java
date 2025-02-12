package New.example.user.controller;

import New.example.user.model.User;
import New.example.user.services.EmailService;
import New.example.user.services.OTPService;
import New.example.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OTPService otpService;

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        User user = new User();
        model.addAttribute("user",user);
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            model.addAttribute("error", "Email cannot be empty");
            return "register";
        }

        if (!isValidEmail(user.getEmail())) {
            model.addAttribute("error", "Invalid email format");
            return "register";
        }

        userService.register(user);
        String otp = otpService.generateOTP();
        otpService.sendOtpToPhone(user.getEmail(), otp);

        emailService.sendConfirmationEmail(user.getEmail());

        return "login";
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}";
        return email.matches(emailRegex);
    }


    @GetMapping("/login")
    public String getLoginPage(){
        return "/login";
    }
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = userService.authenticate(email, password);
        if (isAuthenticated) {
            return "dashboard";
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String getDashboardPage() {
        return "dashboard";
    }
    @GetMapping("index")
    public String getIndexpage(){
        return "index";
    }
}
