package com.jd.edu.service;

import java.util.Map;

public interface MsmService {
    boolean send(Map<String,Object> param, String phone);
}
