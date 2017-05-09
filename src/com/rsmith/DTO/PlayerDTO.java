package com.rsmith.DTO;

public class PlayerDTO {
    private Integer id;
    private String username;
    public PlayerDTO(){}
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String toString() {
	return "Id: " + id + " Username: " + username;
    }
}
