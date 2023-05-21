package com.cydeo.entity.common;

import com.cydeo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    //How I can access to password field of the user object in DB?
    //we need to establish has a relationship

  private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }


    /*
hey spring I heard that you are working with certain user fields that I need to provide you.
Okay, can you please tell me the contract we need to do between us and then I can provide you.
Spring says, implement all these methods.
 */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorityList = new ArrayList<>();

        GrantedAuthority authority = new SimpleGrantedAuthority(this.user.getRole().getDescription());

        authorityList.add(authority);

        return authorityList;
    }
        /*
        whenever we give user, it will convert to Spring required user.
         */

    @Override
    public String getPassword() { //get userPassword from DB and then set it to Spring Security User field.
        return this.user.getPassWord();
    }

    @Override
    public String getUsername() { //get UserName from DB and then set it to Spring Security User field.
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
