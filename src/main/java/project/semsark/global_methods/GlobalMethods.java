package project.semsark.global_methods;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.Random;

public class GlobalMethods {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    public static String generateOtp() {
        String numbers = "0123456789";

        Random randomMethod = new Random();

        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            otp.append(numbers.charAt(randomMethod.nextInt(numbers.length())));
        }
        return otp.toString();
    }

    public static String getToken() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest()
                .getHeader(AUTHORIZATION_HEADER)
                .substring(7);
    }
}
