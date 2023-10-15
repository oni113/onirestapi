import java.security.SecureRandom;
import java.util.Base64;

public class RandomStringGenerator {
    public static void main(String[] args) {
        // 32 바이트(256비트)의 랜덤 바이트 배열 생성
        byte[] randomBytes = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);

        // 바이트 배열을 Base64 인코딩하여 문자열로 변환
        String randomString = Base64.getEncoder().encodeToString(randomBytes);

        System.out.println("Random String: " + randomString);
    }
}