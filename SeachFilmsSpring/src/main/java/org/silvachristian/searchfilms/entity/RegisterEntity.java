package org.silvachristian.searchfilms.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class RegisterEntity {
    @NonNull
    String email;
    @NonNull
    String username;
}
