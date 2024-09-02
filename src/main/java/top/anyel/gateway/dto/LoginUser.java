package top.anyel.gateway.dto;

import com.google.gson.Gson;

import lombok.*;

@Data
@NoArgsConstructor
public class LoginUser {

    private String username;
    private String password;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}