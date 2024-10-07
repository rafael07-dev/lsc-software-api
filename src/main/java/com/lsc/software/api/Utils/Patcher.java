package com.lsc.software.api.Utils;

import com.lsc.software.api.model.UserEntity;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;

@Component
public class Patcher {

    public void userPatcher(UserEntity existingUser, UserEntity newUser) throws IllegalAccessException {
        Class<?> userClass = UserEntity.class;

        Field[] userFields = userClass.getDeclaredFields();

        for (Field userField : userFields) {
            userField.setAccessible(true);

            Object value = userField.get(newUser);

            if (value != null) {
                userField.set(existingUser, value);
            }

            userField.setAccessible(false);
        }
    }
}
