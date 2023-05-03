package project.semsark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.semsark.model.entity.OTP;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    public OTP findByOtp(String otp);

    public OTP findByEmailAndAndUsed(String email,String used);
}
