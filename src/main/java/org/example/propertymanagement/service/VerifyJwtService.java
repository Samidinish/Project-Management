package org.example.propertymanagement.service;


import org.example.propertymanagement.entity.type.JwtInfo;

public interface VerifyJwtService {

    JwtInfo verifyToken(String jwtToken);
}
