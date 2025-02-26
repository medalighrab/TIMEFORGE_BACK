package tn.esprit.tic.timeforge.security.services;


//import com.securityEcommerce.exception.TokenRefreshException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.timeforge.Entity.RefreshToken;
import tn.esprit.tic.timeforge.Reposotory.RefreshTokenReposotries;
import tn.esprit.tic.timeforge.Reposotory.UserRepo;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
  @Value("${app.jwtRefreshExpirationMs}")
  private Long refreshTokenDurationMs;

  @Autowired
  private RefreshTokenReposotries refreshTokenRepository;

  @Autowired
  private UserRepo userRepository;

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public RefreshToken createRefreshToken(Long userId) {
    RefreshToken refreshToken = new RefreshToken();

    refreshToken.setUser(userRepository.findById(userId).get());
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setToken(UUID.randomUUID().toString());

    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
     // throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
      throw new RuntimeException("failed");
    }

    return token;
  }

  @Transactional
  public int deleteByUserId(Long userId) {
    return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
  }
}
