package tn.esprit.tic.timeforge.Payload.Request;

import jakarta.validation.constraints.NotBlank;

public class TokenRefreshRequest {
    @NotBlank
    private String refreshtoken;

    public String getRefreshtoken() {
        return refreshtoken;
    }

    public void setRefreshtoken(String refreshtoken) {
        this.refreshtoken = refreshtoken;
    }

    public TokenRefreshRequest() {
    }

    public TokenRefreshRequest(String refreshtoken) {
        this.refreshtoken = refreshtoken;
    }
}
