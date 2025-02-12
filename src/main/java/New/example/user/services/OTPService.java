package New.example.user.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OTPService {

    public String generateOTP() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    public void sendOtpToPhone(String phoneNumber, String otp) {
    }
}
