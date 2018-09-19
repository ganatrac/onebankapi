package com.infosys.onebank.service;

import com.infosys.onebank.dto.UserDTO;
import com.infosys.onebank.resource.User;

public interface UserService {
    UserDTO createUser(User user);
}
