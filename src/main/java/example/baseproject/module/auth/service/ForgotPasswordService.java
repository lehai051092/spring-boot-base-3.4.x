package example.baseproject.module.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ForgotPasswordService {

    private final JavaMailSender mailSender;

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public ForgotPasswordService(JavaMailSender mailSender, RedisTemplate<String, String> redisTemplate) {
        this.mailSender = mailSender;
        this.redisTemplate = redisTemplate;
    }

    public void sendForgotPasswordEmail(String toEmail) {
        // Xóa mã cũ nếu tồn tại
        String redisKey = toEmail + "_forgot_password_token";
        if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
            redisTemplate.delete(redisKey);
        }

        // Tạo mã token mới
        String token = UUID.randomUUID().toString();
        String resetLink = "http://localhost:8080/auth/reset-password?token=" + token + "&email=" + toEmail;

        // Lưu token vào Redis với thời hạn 1 giờ
        redisTemplate.opsForValue().set(redisKey, token, 1, TimeUnit.HOURS);

        // Gửi email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Password Reset Request");
        message.setText("Click on the following link to reset your password: " + resetLink);
        mailSender.send(message);
    }
}
